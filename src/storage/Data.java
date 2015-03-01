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
    private String filename;

    private Deque<Member> members;
    private Deque<Boat> boats;

    private Data()
    {
        emptyLists();
    }

    public void emptyLists()
    {
        filename = "";
        members = new Deque<>();
        boats = new Deque<>();
        Member.setMemberCount(Member.START_NR);
    }

    public static Data getInstance()
    {
        return instance;
    }

    public void loadData() throws IOException, ClassNotFoundException
    {
        if (filename != "") {
            Deque<?> fileList = DequeStorage.getInstance().read(filename);
            members = (Deque<Member>) fileList.removeFirst();
            boats = (Deque<Boat>) fileList.removeFirst();
            Member.setMemberCount(members.size() + 1);
        }
    }

    public void writeData() throws IOException
    {
        if (filename != "") {
            Deque<Deque> godList = new Deque<Deque>();
            godList.addLast(members);
            godList.addLast(boats);
            DequeStorage.getInstance().write(godList, filename);
        }
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

    public Data setFilename(String filename)
    {
        instance.filename = filename;
        return instance;
    }
}
