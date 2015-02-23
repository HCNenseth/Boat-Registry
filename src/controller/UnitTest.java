package controller;

import org.junit.Rule;
import static org.junit.Assert.*;

import java.io.IOException;

/**
 * Created by alex on 2/23/15.
 */
public class UnitTest
{
    public final String dataFile = "testfile.dat";

    @Rule
    public void test_mediator() throws IOException
    {
        Mediator m = new Mediator(dataFile);

        assertTrue(m.getActive().equals(Windows.BASE));
    }
}
