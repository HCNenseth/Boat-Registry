/**
 * Created by alex on 2/17/15.
 */

package model;

import storage.Deque;

import java.io.Serializable;

public class Member implements Serializable
{
    private String name;
    private int id;
    private static int memberCount = 0;
    private Deque<Boat> boats;
    private String boatsString;

    public static class Builder
    {
        // Required parameters
        private String name;

        public Builder(String val)
        {
            this.name = val;
        }

        public Member build()
        {
            return new Member(this);
        }
    }

    private Member(Builder b)
    {
        this.name = b.name;
        id = ++memberCount;
        boats = new Deque<Boat>();
    }

    public void push(Boat b)
    {
        // Side effect ...
        b.setOwner(this);

        boats.addLast(b);
    }

    public Boat pop()
    {
        return boats.removeFirst();
    }

    public String getName() { return name; }
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
    public String toString()
    {
        return name;
    }
}
