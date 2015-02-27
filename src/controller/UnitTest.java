package controller;

import org.junit.Rule;
import static org.junit.Assert.*;

import java.io.IOException;

/**
 * Created by alex on 2/23/15.
 */
public class UnitTest
{
    @Rule
    public void test_mediator() throws IOException
    {
        Mediator m = Mediator.getInstance();

        assertTrue(m.getActive().equals(Configuration.BASE));
    }
}
