package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by alex on 2/21/15.
 */
public class UnitTest
{
    @Test
    public void test_member()
    {
        String name = "John";

        Member m = new Member.Builder(name).build();

        assertTrue(m.getName().equals(name));
    }

    @Test
    public void test_boat()
    {
        String regNr = "A123";
        String type = "SpeedBoat";
        String color = "Purple";
        int year = 1998;
        double length = 10.3;
        double power = 200.3;

        Boat b = new Boat.Builder(regNr, type)
                            .color(color)
                            .year(year)
                            .power(power)
                            .length(length)
                            .build();

        assertTrue(b.getRegnr().equals(regNr));
        assertTrue(b.getType().equals(type));
        assertTrue(b.getColor().equals(color));
        assertTrue(b.getYear() == year);
        assertTrue(b.getLength() == length);
        assertTrue(b.getPower() == power);
    }
}
