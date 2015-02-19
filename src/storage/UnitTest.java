package storage;

import model.Boat;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by alex on 2/19/15.
 */
public class UnitTest
{
    Deque<Boat> boats;

    public UnitTest()
    {
        boats = new Deque<Boat>();
    }

    @Test
    public void test_insert()
    {
        String[] regNrs = {"A123", "A234", "A456",
                           "B123", "B234", "B456",
                           "C123", "C234", "C456"};

        for (int i = 0; i < regNrs.length; i++) {
            boats.addLast(new Boat.Builder(regNrs[i], "Sailboat").build());
        }

        for (int i = 0; i < regNrs.length; i++) {
            Boat pop = boats.removeFirst();
            assertTrue(pop.getRegnr().equals(regNrs[i]));
            assertTrue(pop.getType().equals("Sailboat"));
        }
    }
}
