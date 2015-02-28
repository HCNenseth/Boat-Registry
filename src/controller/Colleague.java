package controller;

import controller.dialog.Dialog;
import share.Command;
import share.SignalType;
import controller.widget.boat.Boat;
import controller.widget.member.Member;
import controller.window.Base;
import storage.Data;

/**
 * Created by alex on 2/22/15.
 *
 * This class is meant to act as a helper "colleague" to the busy
 * Mediator. This might not be 100% correct to the pattern.
 * As of now this class holds and manages all the controllers,
 * and will be first support line to the controller instances in
 * the future.
 */
public class Colleague
{
    // Singleton
    private static Colleague instance;

    private Mediator mediator;

    private Base baseController;
    private Member memberController;
    private Boat boatController;
    private Dialog dialogController;

    private Colleague(Mediator mediator)
    {
        this.mediator = mediator;
        setBaseController();
        setMemberController();
        setBoatController();
        setDialogController();
    }

    public static Colleague getInstance(Mediator mediator)
    {
        if (instance == null) { instance = new Colleague(mediator); }
        return instance;
    }

    /*
       BASE CONTROLLER
     */

    private void setBaseController()
    {
        baseController = new Base.Builder().observer(mediator).build();
    }

    public Base getBaseController()
    {
        return baseController;
    }

    /*
        GENERIC WINDOW METHODS
     */

    public void processWindowSignal(Command c)
    {
        if (c.getSignalType() == SignalType.QUIT) {
            mediator.endRoutine();
            return;
        }

        switch (c.getDataType()) {
            case BOAT:
                processBoatSignal(c);
                return;
            case MEMBER:
                processMemberSignal(c);
                return;
        }
    }

    /*
        GENERIC WIDGET METHODS
     */

    public void processWidgetSignal(Command c)
    {
        switch (c.getSignalType()) {
            case CLOSE:
                mediator.secondaryStage.close();
                break;
            case CREATE:
                reloadBoats(); reloadMembers();
                mediator.secondaryStage.close();
                break;
            case UPDATE:
                getBaseController().updateMembers();
                getBaseController().updateBoats();
                mediator.secondaryStage.close();
                break;
        }
    }

    /*
        MEMBER LOGIC
     */

    private void setMemberController()
    {
        memberController = new Member.Builder()
                .observer(mediator).build();
    }

    public Member getMemberController()
    {
        return memberController;
    }

    public void reloadMembers()
    {
        getBaseController().updateMembers();
        getBaseController().focusOnMembers();
    }

    /**
     * @param c
     */
    private void processMemberSignal(Command c)
    {
        switch (c.getSignalType()) {
            case EDIT:
                getMemberController().setMember((model.Member) c.getPayload());
                mediator.switchScene(Configuration.MEMBER_EDIT);
                break;
            case DELETE:
                try {
                    model.Member tmp = deleteMemberProcess(c);
                    showMessage(String.format("Deleted member: %s %s",
                                    tmp.getFirstname(), tmp.getLastname()),
                            Dialog.Icons.SUCCESS);
                } catch (IllegalStateException ise) {
                    showMessage(ise.getMessage(), Dialog.Icons.ERROR);
                }
                getBaseController().updateMembers();
                break;
            case NEW:
                mediator.switchScene(Configuration.MEMBER_NEW);
                break;
        }
    }

    /**
     * Delete member process. Makes sure not to delete
     * members with connected boats.
     * @param c
     * @return
     */
    private model.Member deleteMemberProcess(Command c)
    {
        model.Member member = (model.Member) c.getPayload();
        if (member.getBoats().size() > 0) {
            throw new IllegalStateException(
                    "Cannot delete a member with connected boats");
        } else {
            return Data.getInstance().getMembers().remove(member);
        }
    }

    /*
        BOAT LOGIC
     */

    private void setBoatController()
    {
        boatController = new Boat.Builder(Data.getInstance().getMembers())
                .observer(mediator).build();
    }

    public Boat getBoatController()
    {
        return boatController;
    }

    public void reloadBoats()
    {
        getBaseController().updateBoats();
        getBaseController().focusOnBoats();
    }

    /**
     * @param c
     */
    private void processBoatSignal(Command c)
    {
        switch (c.getSignalType()) {
            case EDIT:
                getBoatController().setBoat((model.Boat) c.getPayload());
                mediator.switchScene(Configuration.BOAT_EDIT);
                break;
            case DELETE:
                model.Boat removedBoat = deleteBoatProcess(c);
                showMessage(String.format("Deleted boat: %s", removedBoat.getRegnr()),
                        Dialog.Icons.SUCCESS);
                getBaseController().updateMembers();
                getBaseController().updateBoats();
                break;
            case NEW:
                mediator.switchScene(Configuration.BOAT_NEW);
                break;
        }
    }

    /**
     * Delete boat process method. Makes sure to disconnect any
     * member and boats connection that might be apparent.
     * @param c
     * @return
     */
    private model.Boat deleteBoatProcess(Command c)
    {
        model.Boat boat = (model.Boat) c.getPayload();
        if (boat.getOwner() != null)
            Data.getInstance().disconnectBoatAndMember(boat, boat.getOwner());
        return Data.getInstance().getBoats().remove(boat);
    }

    /*
        DIALOG LOGIC
     */

    private void setDialogController()
    {
        dialogController = new Dialog(mediator);
    }

    public Dialog getDialogController()
    {
        return dialogController;
    }

    /**
     * Simple generic show message method.
     * @param string
     * @param icon
     */
    public void showMessage(String string, Dialog.Icons icon)
    {
        mediator.switchScene(Configuration.DIALOG);
        getDialogController().setMessageLabel(string);
        getDialogController().setIcon(icon);
    }
}
