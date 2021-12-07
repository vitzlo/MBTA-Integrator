import org.junit.Before;
import org.junit.Test;
import subway.model.Route;
import subway.model.Stop;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Test class for the Stop class, its constructor, and its methods.
 */
public class StopMethodTests {
    Route r1_1, r1_2, r1_3;
    Stop s1_1, s1_2, s1_3, s1_4, s1_5, s1_6, s1_7;

    @Before
    public void initData() {
        r1_1 = new Route("Alfa Line");
        r1_2 = new Route("Bravo Line");
        r1_3 = new Route("Bravo II");
        s1_1 = new Stop("A-1", new ArrayList<>(List.of(r1_1)));                        //    4
        s1_2 = new Stop("AB-2", new ArrayList<>(List.of(r1_1, r1_2)));                 //    |
        s1_3 = new Stop("A-3", new ArrayList<>(List.of(r1_1)));                        // 1--2--3  Alfa
        s1_4 = new Stop("B-1", new ArrayList<>(List.of(r1_2)));                        //    |
        s1_5 = new Stop("B-3", new ArrayList<>(List.of(r1_2)));                        //    5 Bravo
        s1_6 = new Stop("B-4", new ArrayList<>(List.of(r1_2, r1_3)));                  //    |
        s1_7 = new Stop("C-1", new ArrayList<>(List.of(r1_3)));                        // 7--6 Bravo II
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
        new Stop(null, new ArrayList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullRoutes() {
        new Stop("Error", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullRouteInRoutes() {
        new Stop("Error", new ArrayList<>(Arrays.asList(r1_3, null)));
    }

    @Test
    public void testPrintName() {
        assertEquals("A-1", s1_1.printName());
    }

    @Test
    public void testGetRoutes() {
        assertEquals(new ArrayList<>(List.of(r1_2, r1_3)), s1_6.getRoutes());
    }

    @Test
    public void testConnectorTrue() {
        assertTrue(s1_6.isConnector());
    }

    @Test
    public void testConnectorFalse() {
        assertFalse(s1_5.isConnector());
    }
}
