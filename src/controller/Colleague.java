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
    private Mediator mediator;

    private Base baseController;
    private Member memberController;
    private Boat boatController;

    public Colleague(Mediator mediator)
    {
        this.mediator = mediator;
        setBaseController();
        setMemberController();
        setBoatController();
    }

    private void setBaseController()
    {
        baseController = new Base.Builder(mediator.getMembers(), mediator.getBoats())
                .observer(mediator).build();
    }

    private void setMemberController()
    {
        memberController = new Member.Builder()
                .observer(mediator).build();
    }

    private void setBoatController()
    {
        boatController = new Boat.Builder(mediator.getMembers())
                .observer(mediator).build();
    }

    public Base getBaseController() { return baseController; }

    public Member getMemberController() { return memberController; }

    public Boat getBoatController() { return boatController; }
}