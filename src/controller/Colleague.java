package controller;

import controller.dialog.boat.Boat;
import controller.dialog.member.Member;
import controller.window.Base;
import storage.Deque;

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
        if (instance == null) { instance = new Colleague(mediator); }
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

    public Deque<model.Member> getMembers() {
        return mediator.getMembers();
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

    public Deque<model.Boat> getBoats() { return mediator.getBoats(); }
}
