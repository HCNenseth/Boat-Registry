/**
 *
 * @filename UnitTest.java
 *
 * @date 2015-02-23
 *
 * Empty UnitTest for the controller package...
 */

package controller;

import org.junit.Rule;
import static org.junit.Assert.*;

import java.io.IOException;

public class UnitTest
{
    @Rule
    public void test_mediator() throws IOException
    {
        Mediator m = Mediator.getInstance();

        assertTrue(m.getActive().equals(Configuration.BASE));
    }
}
