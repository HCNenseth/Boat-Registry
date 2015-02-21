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

    private String[] names = {"John", "Pete", "Steve",
                              "Sara", "Carry", "Sue"
                             };

    public UnitTest()
    {
        boats = new Deque<Boat>();
        members = new Deque<Member>();

        // Populate data
        for (String r : regNrs)
            boats.addLast(new Boat.Builder(r, "Sailboat").build());

        for (String n : names)
            members.addLast(new Member.Builder(n).build());
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

        for (String n : names) {
            Member pop = members.removeFirst();
            assertTrue(pop.getName().equals(n));
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
        for (Member m : members)
            assertTrue(m.getName().equals(names[i++]));
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

        // Save lists in wrapper class
        Deque<Deque> list = new Deque<Deque>(); // yo dawg!
        list.addLast(members);
        list.addLast(boats);

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

        // Write to tmp file!
        DequeStorage f1 = new DequeStorage(tmpfile);
        f1.write(list);

        // Read from tmp file
        DequeStorage f2 = new DequeStorage(tmpfile);
        Deque<?> fileList = f2.read();

        // Risky risky...
        Deque<Member> fileMembers = (Deque<Member>) fileList.removeFirst();
        Deque<Boat> fileBoats = (Deque<Boat>) fileList.removeFirst();

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
