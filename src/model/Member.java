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

        public Builder name(String val)
        {
            this.name = val;
            return this;
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
}
