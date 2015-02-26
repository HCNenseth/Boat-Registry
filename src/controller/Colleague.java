package controller;

import common.Command;
import common.DataType;
import common.SignalType;
import controller.dialog.boat.Boat;
import controller.dialog.member.Member;
import controller.window.Base;
import data.Data;
import storage.Deque;

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

    private Colleague(Mediator mediator)
    {
        this.mediator = mediator;
        setBaseController();
        setMemberController();
        setBoatController();
    }

    public static Colleague getInstance(Mediator mediator)
    {
        if (instance == null) {
            instance = new Colleague(mediator);
        }
        return instance;
    }

    public static Colleague getInstance()
    {
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
        GENERIC DIALOG METHODS
     */
    public void processDialogSignal(Command c)
    {
        switch (c.getSignalType()) {
            case CLOSE:
                mediator.secondaryStage.close();
                break;
            case CREATE:
                reloadBoats(); reloadMembers();
                mediator.secondaryStage.close();
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
                System.out.println("edit member");
                break;
            case DELETE:
                System.out.println("delete member");
                break;
            case NEW:
                mediator.switchScene(Windows.MEMBER_NEW);
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
                /**
                 * Open Boat edit mode
                 * send boat on payload to controller
                 */
                System.out.println("edit boat");
                break;
            case DELETE:
                /**
                 * Push a query dialog to verify
                 * - Fetch data on payload.
                 * - Check if connected owners
                 * - Remove from boat list.
                 * - Remove from member list.
                 */

                System.out.println("delete boat");
                break;
            case NEW:
                mediator.switchScene(Windows.BOAT_NEW);
                break;
        }
    }

}
