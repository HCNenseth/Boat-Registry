package storage;

import model.Member;
import model.Boat;
import org.junit.Test;
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
                               "E123", "E234", "E456"
                              };

    private String[] names = {"John", "Pete", "Steve",
                              "Sara", "Carry", "Sue"
                             };

    public UnitTest()
    {
        boats = new Deque<Boat>();
        members = new Deque<Member>();
    }

    @Test
    public void test_insert()
    {
        // Populate data
        for (String r : regNrs)
            boats.addLast(new Boat.Builder(r, "Sailboat").build());

        for (String n : names)
            members.addLast(new Member.Builder(n).build());

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
        // Populate data
        for (String r : regNrs)
            boats.addLast(new Boat.Builder(r, "Sailboat").build());

        for (String n : names)
            members.addLast(new Member.Builder(n).build());

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
        // Populate data
        for (String r : regNrs)
            boats.addLast(new Boat.Builder(r, "Sailboat").build());

        for (String n : names)
            members.addLast(new Member.Builder(n).build());

        int i = 0;
        // push two boats on each member
        for (Member m : members) {
            m.push(boats.get(i++));
            m.push(boats.get(i++));
        }

        i = 0;
        // test boat for each member
        for (Member m : members) {
            for (Boat b: m.getBoats()) {
                assertTrue(b.getRegnr().equals(regNrs[i++]));
            }
        }

    }
}
