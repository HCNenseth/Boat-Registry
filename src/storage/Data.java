package storage;

import model.Boat;
import model.Member;

import java.io.IOException;
import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * Created by alex on 2/19/15.
 *
 * Test class for wrapping data and pushing it to file.
 *
 * EXPERIMENTAL!
 *
 */
public class Data implements Serializable
{
    private Deque<Boat> boats;
    private Deque<Member> members;

    public Data() {}

    public void setBoats(Deque<Boat> boats)
    {
        this.boats = boats;
    }

    public void setMembers(Deque<Member> members)
    {
        this.members = members;
    }


    public Deque<Boat> getBoats()
    {
        return boats;
    }

    public Deque<Member> getMembers()
    {
        return members;
    }
}
