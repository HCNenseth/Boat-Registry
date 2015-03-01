package storage;

import model.boat.BoatSkeleton;
import model.member.Member;

import java.io.IOException;

/**
 * Created by alex on 2/26/15.
 */
public class Data
{
    private static final Data instance = new Data();
    private String filename = "";

    private Deque<Member> members;
    private Deque<BoatSkeleton> boats;

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

    public Data setFilename(String filename)
    {
        instance.filename = filename;
        return instance;
    }

    public String getFilename() { return filename; }

    /*
        READ AND WRITE DATA METHODS
     */

    public void loadData() throws IOException, ClassNotFoundException
    {
        if (filename.equals("")) return; // guard

        Deque<?> fileList = DequeStorage.getInstance().read(filename);
        members = (Deque<Member>) fileList.removeFirst();
        boats = (Deque<BoatSkeleton>) fileList.removeFirst();
        Member.setMemberCount(members.size() + 1);
    }

    public void writeData() throws IOException
    {
        if (filename.equals("")) return; // guard

        Deque<Deque> godList = new Deque<Deque>();
        godList.addLast(members);
        godList.addLast(boats);
        DequeStorage.getInstance().write(godList, filename);
    }

    /*
        BOATS METHODS
     */

    public Deque<BoatSkeleton> getBoats() { return boats; }

    public void addBoat(BoatSkeleton boat) { boats.addLast(boat); }

    public BoatSkeleton removeBoat(BoatSkeleton boat) { return boats.remove(boat); }

    /*
        MEMBERS METHODS
     */

    public Deque<Member> getMembers() { return members; }

    public void addMember(Member member) { members.addLast(member); }

    public Member removeMember(Member member) { return members.remove(member); }

    /*
        SHARED METHODS
     */

    public void connectBoatAndMember(BoatSkeleton boat, Member member)
    {
        boat.setOwner(member);
        member.push(boat);
    }

    public void disconnectBoatAndMember(BoatSkeleton boat, Member member)
    {
        member.getBoats().remove(boat);
        boat.setOwner(null);
    }

    /**
     * Methods used for the unit testers.
     */
    protected void setMembers(Deque<Member> members) { this.members = members; }
    protected void setBoats(Deque<BoatSkeleton> boats) { this.boats = boats; }
}
