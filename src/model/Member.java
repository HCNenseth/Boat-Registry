/**
 * Created by alex on 2/17/15.
 */

package model;

import com.sun.org.apache.xpath.internal.operations.Mod;
import storage.Deque;

import java.io.Serializable;

public final class Member implements Comparable<Member>, Serializable
{
    private static final long serialVersionUID = 2000L;

    private int id;
    private static int memberCount = 0;
    private String firstname, lastname;
    private Deque<Boat> boats;

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
        boats = new Deque<Boat>();
    }

    public void push(Boat b)
    {
        b.setOwner(this); // side effect...
        boats.addLast(b);
    }

    public Boat pop()
    {
        return boats.removeFirst();
    }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getId() { return id; }

    // OBS: this makes boats list mutable! (should return a copy)
    public Deque<Boat> getBoats() { return boats; }

    public String getBoatsString()
    {
        StringBuilder sb = new StringBuilder();

        for (Boat b : boats)
            sb.append(b.getRegnr()).append(", ");

        return sb.toString();
    }

    @Override
    public String toString()  { return firstname + " " +lastname; }

    public static void setMemberCount(int val) { memberCount = val; }

    public boolean hasBoats()
    {
        return getBoats() == null;
    }

    @Override
    public int compareTo(Member test)
    {
        final int NOT_EQUAL = 1;
        final int EQUAL = 0;

        if (id != test.getId()) { return NOT_EQUAL; }
        if (! firstname.equals(test.getFirstname())) { return NOT_EQUAL; }
        if (! lastname.equals(test.getLastname())) { return NOT_EQUAL; }

        if (boats.compareTo(test.getBoats()) < EQUAL) { return NOT_EQUAL; }

        return EQUAL;
    }
}
