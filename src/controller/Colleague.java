package controller;

import controller.dialog.Boat;
import controller.dialog.Member;
import controller.window.Base;

/**
 * Created by alex on 2/22/15.
 *
 * This class is meant to act as a helper "colleauge" to the busy
 * Mediator. This might not be 100% correct to the pattern.
 * As of now this class holds and manages all the controllers,
 * and will be first support line to the controller instances in
 * the future.
 */
public class Colleague
{
    // Singleton
    private static Colleague INSTANCE;

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
        if (INSTANCE == null) { INSTANCE = new Colleague(mediator); }
        return INSTANCE;
    }

    /*
        BASE CONTROLLER
     */

    private void setBaseController()
    {
        baseController = new Base.Builder(mediator.getMembers(), mediator.getBoats())
                .observer(mediator).build();
    }

    public Base getBaseController() { return baseController; }

    /*
        MEMBER CONTROLLER
     */

    private void setMemberController()
    {
        memberController = new Member.Builder()
                .observer(mediator).build();
    }

    public Member getMemberController() { return memberController; }

    public void reloadMembers()
    {
        getBaseController().updateMembers();
        getBaseController().focusOnMembers();
    }

    /*
        BOAT CONTROLLER
     */

    private void setBoatController()
    {
        boatController = new Boat.Builder(mediator.getMembers())
                .observer(mediator).build();
    }

    public Boat getBoatController() { return boatController; }

    public void reloadBoats()
    {
        getBaseController().updateBoats();
        getBaseController().focusOnBoats();
    }
}
