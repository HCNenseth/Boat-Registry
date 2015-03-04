package share.validator;

/**
 * Created by alex on 3/1/15.
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class UnitTest
{
    @Test public void test_email()
    {
        assertTrue(StringMatcher.email("test@example.com"));
        assertTrue(StringMatcher.email("test.best@example.com"));
        assertTrue(StringMatcher.email("test.best@love.example.com"));

        assertFalse(StringMatcher.email("user@example"));
        assertFalse(StringMatcher.email("user.false@example"));
        assertFalse(StringMatcher.email("@example.com"));
        assertFalse(StringMatcher.email("@example"));
        assertFalse(StringMatcher.email("horse@"));
    }

    @Test public void test_firstname()
    {
        assertTrue(StringMatcher.firstname("John"));
        assertTrue(StringMatcher.firstname("John Carl"));
        assertTrue(StringMatcher.firstname("John-Carl"));

        // Some special chars
        assertTrue(StringMatcher.firstname("Jørn"));
    }

    @Test public void test_lastname()
    {
        assertTrue(StringMatcher.lastname("Doe"));
        assertTrue(StringMatcher.lastname("Doe Smith"));
        assertTrue(StringMatcher.lastname("Doe-Smith"));

        // Some special chars
        assertTrue(StringMatcher.lastname("Jørnson"));
    }

    @Test public void test_regnr()
    {
        assertTrue(StringMatcher.regnr("ABC123"));
    }

    @Test public void test_type()
    {
        assertTrue(StringMatcher.type("Sailboat"));
    }

    @Test public void test_color()
    {
        assertTrue(StringMatcher.color("Purple"));
        assertTrue(StringMatcher.color("Purplish yellow"));

        // Some special chars
        assertTrue(StringMatcher.color("Rød"));
        assertTrue(StringMatcher.color("Rød grønn"));
    }

    @Test public void test_year()
    {
        assertTrue(StringMatcher.year("1999"));

        assertFalse(StringMatcher.year("20O0"));
        assertFalse(StringMatcher.year("100"));
        assertFalse(StringMatcher.year("10000"));
        assertFalse(StringMatcher.year("Horse"));
    }

    @Test public void test_length()
    {
        assertTrue(StringMatcher.length("32.2"));
        assertTrue(StringMatcher.length("32"));

        assertFalse(StringMatcher.length("32.21.1"));
        assertFalse(StringMatcher.length("Horse"));
        assertFalse(StringMatcher.length("32 M"));
        assertFalse(StringMatcher.length("32 F"));
    }

    @Test public void test_power()
    {
        assertTrue(StringMatcher.power("144.5"));
        assertTrue(StringMatcher.power("144"));

        assertFalse(StringMatcher.power("133.2.3"));
        assertFalse(StringMatcher.power("Apple"));

        assertFalse(StringMatcher.power("150 HK"));
        assertFalse(StringMatcher.power("150 HP"));
    }
}
