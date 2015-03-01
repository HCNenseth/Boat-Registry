package storage;

import model.Member;
import model.Boat;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by alex on 2/19/15.
 */
public class UnitTest
{
    private Deque<Boat> boats;
    private Deque<Member> members;

    private String[] regNrs = {"A123", "A234", "A456",
                               "B123", "B234", "B456",
                               "C123", "C234", "C456",
                               "D123", "D234", "D456",
                               "E123", "E234", "E456",
                               "F123", "F234", "F456",
                               "G123", "G234", "G456",
                               "H123", "E234", "H456",
                               "I123", "I234", "I456",
                               "J123", "J234", "J456"
                              };

    private String[][] names = {
            {"John", "Doe" }, {"Pete", "Doe"},
            {"Steve", "Smith"}, {"Sara", "Smith"},
            {"Carry", "Wilson"}, {"Sue", "Wilson"}
    };

    public UnitTest()
    {
        boats = new Deque<Boat>();
        members = new Deque<Member>();

        // Populate data
        for (String r : regNrs)
            boats.addLast(new Boat.Builder(r, "Sailboat").build());

        for (String n[] : names)
            members.addLast(new Member.Builder(n[0], n[1]).build());
    }

    @Test
    public void test_inserted()
    {
        // Iterate
        for (String r : regNrs) {
            Boat pop = boats.removeFirst();
            assertTrue(pop.getRegnr().equals(r));
            assertTrue(pop.getType().equals("Sailboat"));
        }

        for (String n[] : names) {
            Member pop = members.removeFirst();
            assertTrue(pop.getFirstname().equals(n[0]));
            assertTrue(pop.getLastname().equals(n[1]));
        }
    }

    @Test
    public void test_iterator()
    {
        // Iterate
        int i = 0;
        for (Boat b : boats)
            assertTrue(b.getRegnr().equals(regNrs[i++]));

        i = 0;
        for (Member m : members) {
            assertTrue(m.getFirstname().equals(names[i][0]));
            assertTrue(m.getLastname().equals(names[i++][1]));
        }
    }

    @Test
    public void test_copy()
    {
        Deque<Member> tmp1 = members.copy();
        Deque<Member> tmp2 = members.copy();

        assertEquals(members.size(), tmp1.size());
        assertEquals(members.size(), tmp2.size());

        while(!tmp1.isEmpty()) {
            assertTrue(tmp1.removeFirst().equals(tmp2.removeFirst()));
        }
    }

    @Test
    public void test_remove()
    {
        Deque<Member> tmp = members.copy();

        // remove first from tmp.
        Member member = tmp.removeFirst();

        assertEquals(member, members.get(0));
        assertNotEquals(members.size(), tmp.size());

        // put member back to member.
        tmp.addFirst(member);
        assertEquals(tmp.size(), members.size());
        assertTrue(tmp.has(member));

        tmp = members.copy();

        /**
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < members.size(); j++) {

                // get random member from members
                int r = (int) (Math.random() * members.size());
                Member rand = members.get(r);

                assertNotNull(rand);
                assertTrue(tmp.has(rand));

                // remove element from tmp
                Member randomRemove = tmp.remove(rand);
                assertNotNull(randomRemove);

                //assertTrue(randomRemove.compareTo(rand) == 0);

                // put back in
                tmp.addLast(randomRemove);
                //assertTrue(tmp.has(randomRemove));

            }
            assertEquals(tmp.size(), members.size());
        }
        */
    }

    @Test
    public void test_relations()
    {
        int i = 0;
        // push two boats on each member
        for (Member m : members) {
            m.push(boats.get(i++));
            m.push(boats.get(i++));
        }

        relations_helper(members, boats);
    }

    /**
     * Somewhat less ugly unittest.
     */
    @Test
    public void test_data() throws IOException, ClassNotFoundException
    {
        String tmpfile = "testfile.dat";
        Data.getInstance().setFilename(tmpfile);

        /**
         * Create some relations.
         * REFACTOR
         * Assign random boats to random members, but not the
         * same boat twice to different or the same member.
         */
        int i = 0;
        for (Member m : members) {
            m.push(boats.get(i++));
            m.push(boats.get(i++));
            m.push(boats.get(i++));
        }

        // Test relations before saving to file
        relations_helper(members, boats);

        Data.getInstance().setMembers(members);
        Data.getInstance().setBoats(boats);

        Data.getInstance().writeData();

        Data.getInstance().loadData();

        Deque<Member> fileMembers = Data.getInstance().getMembers();
        Deque<Boat> fileBoats = Data.getInstance().getBoats();

        relations_helper(fileMembers, fileBoats);
    }

    private void relations_helper(Deque<Member> members, Deque<Boat> boats)
    {
        int j = 0;
        // test boat for each member
        for (Member m : members) {
            for (Boat b: m.getBoats()) {
                assertTrue(b.getRegnr().equals(regNrs[j++]));
                assertTrue(b.getOwner().equals(m));
            }
        }
        // test the orphan boats
        j = 0;
        for (Boat b : boats)
            assertTrue(b.getRegnr().equals(regNrs[j++]));
    }
}
