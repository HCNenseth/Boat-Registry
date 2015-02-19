/**
 * Created by alex on 2/17/15.
 */

package model;

import storage.Deque;

public class Member
{
    private String name;
    private int id;
    private static int memberCount = 0;
    private Deque<Boat> boats;

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
        b.setMember(this);

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
}
