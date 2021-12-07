import org.junit.Before;
import org.junit.Test;
import subway.model.Route;
import subway.model.Stop;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the Route class, its constructor, and its methods.
 */
public class RouteMethodTests {
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
        new Route(null);
    }

    @Test
    public void testPrintName() {
        assertEquals("Bravo Line", r1_2.printName());
    }

    @Test
    public void testGetStops() {
        assertEquals(List.of(s1_2, s1_4, s1_5, s1_6), r1_2.getStops());
    }

    @Test
    public void testHasStopTrue() {
        assertTrue(r1_3.hasStop(s1_7));
    }

    @Test
    public void testHasStopFalse() {
        assertFalse(r1_3.hasStop(s1_1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHasStopNull() {
        r1_2.hasStop(null);
    }

    @Test
    public void testGetStopByNameValid() {
        assertEquals(s1_2, r1_2.getStopByName("AB-2"));
    }

    @Test
    public void testGetStopByNameNull() {
        r1_2.getStopByName(null);
    }

    @Test
    public void testGetStopByNameInvalid() {
        r1_2.getStopByName("C-1");
    }

    @Test
    public void testAddStop() {
        assertEquals(3, r1_1.length());
        Stop s1_8 = new Stop("New Alfa", new ArrayList<>(List.of(r1_1)));
        assertEquals(4, r1_1.length());
        assertTrue(r1_1.hasStop(s1_8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddStopNull() {
        r1_1.addStop(null);
    }

    @Test
    public void testLength() {
        assertEquals(4, r1_2.length());
    }

    @Test
    public void testIsShorterTrue() {
        assertTrue(r1_3.isShorter(r1_2));
    }

    @Test
    public void testIsShorterFalse() {
        assertFalse(r1_1.isShorter(r1_3));
    }

    @Test
    public void testIsShorterEqual() {
        assertFalse(r1_1.isShorter(r1_1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsShorterNull() {
        r1_1.isShorter(null);
    }

    @Test
    public void testConnectorStops() {
        assertEquals(new ArrayList<>(List.of(s1_2, s1_6)), r1_2.connectorStops());
    }
}
