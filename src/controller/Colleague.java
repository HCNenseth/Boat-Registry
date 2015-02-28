package controller;

import controller.dialog.Dialog;
import share.Command;
import share.SignalType;
import controller.widget.boat.Boat;
import controller.widget.member.Member;
import controller.window.Base;
import data.Data;

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

    private void processMemberSignal(Command c)
    {
        switch (c.getSignalType()) {
            case EDIT:
                getMemberController().setMember((model.Member) c.getPayload());
                mediator.switchScene(Configuration.MEMBER_EDIT);
                break;
            case DELETE:
                model.Member member = (model.Member) c.getPayload();
                mediator.switchScene(Configuration.DIALOG);
                if (member.getBoats().size() > 0) {
                    getDialogController().setMessageLabel(
                            "Cannot delete a member with connected boats!");
                } else {
                    model.Member deleted = Data.getInstance().getMembers().remove(member);
                    getDialogController().setMessageLabel(
                            String.format("%s %s successfully deleted",
                                    member.getFirstname(),
                                    member.getLastname()));
                    getDialogController().setIcon(Dialog.Icons.SUCCESS);
                    getBaseController().updateMembers();
                }
                break;
            case NEW:
                mediator.switchScene(Configuration.MEMBER_NEW);
                break;
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

    private void processBoatSignal(Command c)
    {
        switch (c.getSignalType()) {
            case EDIT:
                getBoatController().setBoat((model.Boat) c.getPayload());
                mediator.switchScene(Configuration.BOAT_EDIT);
                break;
            case DELETE:
                model.Boat boat = (model.Boat) c.getPayload();

                if (boat.getOwner() != null)
                    Data.getInstance().disconnectBoatAndMember(boat, boat.getOwner());

                Data.getInstance().getBoats().remove(boat);

                mediator.switchScene(Configuration.DIALOG);
                getDialogController().setMessageLabel(
                        String.format("%s successfully deleted",
                                boat.getRegnr()));
                getDialogController().setIcon(Dialog.Icons.SUCCESS);

                getBaseController().updateMembers();
                getBaseController().updateBoats();

                break;
            case NEW:
                mediator.switchScene(Configuration.BOAT_NEW);
                break;
        }
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
}
