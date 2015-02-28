package storage;

import model.Boat;
import model.Member;

import java.io.IOException;

/**
 * Created by alex on 2/26/15.
 */
public class Data
{
    private static final Data instance = new Data();

    private Deque<Member> members;
    private Deque<Boat> boats;

    private Data() { }

    public static Data getInstance()
    {
        return instance;
    }

    public void loadData() throws IOException, ClassNotFoundException
    {
        Deque<?> fileList = DequeStorage.getInstance().read();
        members = (Deque<Member>) fileList.removeFirst();
        boats = (Deque<Boat>) fileList.removeFirst();

        model.Member.setMemberCount(members.size() + 1);
    }

    public void writeData() throws IOException
    {
        Deque<Deque> godList = new Deque<Deque>();
        godList.addLast(members);
        godList.addLast(boats);
        DequeStorage.getInstance().write(godList);
    }

    public void connectBoatAndMember(Boat boat, Member member)
    {
        boat.setOwner(member);
        member.push(boat);
    }

    public void disconnectBoatAndMember(Boat boat, Member member)
    {
        member.getBoats().remove(boat);
        boat.setOwner(null);
    }

    public Deque<Boat> getBoats() { return boats;}

    public Deque<Member> getMembers() { return members; }

    public void setBoats(Deque<Boat> boats) { this.boats = boats; }

    public void setMembers(Deque<Member> members) { this.members = members; }
}
