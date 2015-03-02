/**
 * Created by alex on 2/17/15.
 */

package model.member;

import model.boat.BoatSkeleton;
import storage.Deque;

import java.io.Serializable;

public final class Member implements Comparable<Member>, Serializable
{
    private static final long serialVersionUID = 2000L;

    public static final short START_NR = 1;

    private int id;
    private static int memberCount = START_NR;
    private String firstname, lastname;
    private Deque<BoatSkeleton> boats;

    public static class Builder implements share.Builder
    {
        // Required parameters
        private String firstname, lastname;

        public Builder(String firstname, String lastname)
        {
            this.firstname = firstname;
            this.lastname = lastname;
        }

        public Member build()
        {
            return new Member(this);
        }
    }

    private Member(Builder b)
    {
        this.firstname = b.firstname;
        this.lastname = b.lastname;
        id = memberCount++;
        boats = new Deque<BoatSkeleton>();
    }

    public void push(BoatSkeleton b)
    {
        b.setOwner(this); // side effect...
        boats.addLast(b);
    }

    public BoatSkeleton pop()
    {
        return boats.removeFirst();
    }

    /*
        GETTERS
     */
    public int getId() { return id; }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    /*
        SETTERS
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Makes boats list mutable...
     */
    public Deque<BoatSkeleton> getBoats() { return boats; }

    public boolean hasBoats()
    {
        return getBoats() == null;
    }

    public String getBoatsString()
    {
        StringBuilder sb = new StringBuilder();

        for (BoatSkeleton b : boats)
            sb.append(b.getRegnr()).append(" ");

        return sb.toString();
    }

    /**
     * Set the static member counter. This helps the
     * import (load from file) method.
     * @param val
     */
    public static void setMemberCount(int val) { memberCount = val; }

    @Override
    public String toString()  { return firstname + " " +lastname; }

    @Override
    public int compareTo(Member test)
    {
        if (test == null)
            throw new NullPointerException("Cannot test against null object!");

        final int NOT_EQUAL = -1;
        final int EQUAL = 0;

        if (id != test.getId()) { return NOT_EQUAL; }

        if (! firstname.equals(test.getFirstname())) { return NOT_EQUAL; }
        if (! lastname.equals(test.getLastname())) { return NOT_EQUAL; }

        if (boats.compareTo(test.getBoats()) < EQUAL) { return NOT_EQUAL; }

        return EQUAL;
    }
}
