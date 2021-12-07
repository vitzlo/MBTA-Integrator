import org.junit.Before;
import org.junit.Test;
import subway.model.Route;
import subway.model.Stop;
import subway.model.SubwayMap;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Test class for the SubwayMap class, its constructor, and its methods.
 */
public class MapMethodTests {
    Route r1_1, r1_2, r2_1, r2_2, r2_3, r2_4, r2_5, r3_1, r3_2;
    Stop s1_1, s1_2, s1_3, s1_4, s1_5, s2_1, s2_2, s2_3, s2_4, s2_5, s2_6, s3_1, s3_2, s3_3, s3_4;
    SubwayMap map1, map2, map3, map4;

    @Before
    public void initData() {
        r1_1 = new Route("Alfa Line");
        r1_2 = new Route("Bravo Line");
        s1_1 = new Stop("A-1", new ArrayList<>(List.of(r1_1)));
        s1_2 = new Stop("AB-2", new ArrayList<>(List.of(r1_1, r1_2)));           //    4
        s1_3 = new Stop("A-3", new ArrayList<>(List.of(r1_1)));                  //    |
        s1_4 = new Stop("B-1", new ArrayList<>(List.of(r1_2)));                  // 1--2--3  Alfa
        s1_5 = new Stop("B-3", new ArrayList<>(List.of(r1_2)));                  //    |
        map1 = new SubwayMap("A/B Metro", new ArrayList<>(List.of(r1_1, r1_2))); //    5 Bravo

        r2_1 = new Route("Charlie Line");
        r2_2 = new Route("Delta Line");
        r2_3 = new Route("Echo Line");
        r2_4 = new Route("Foxtrot Line");
        r2_5 = new Route("Golf Line");
        s2_1 = new Stop("Head", new ArrayList<>(List.of(r2_1)));
        s2_2 = new Stop("Neck", new ArrayList<>(List.of(r2_1, r2_2)));
        s2_3 = new Stop("Body", new ArrayList<>(List.of(r2_2, r2_3)));  //         2--3  6
        s2_4 = new Stop("Waist", new ArrayList<>(List.of(r2_3, r2_4))); // Charlie |  |  | Golf
        s2_5 = new Stop("Leg", new ArrayList<>(List.of(r2_4, r2_5))); //           1  4--5
        s2_6 = new Stop("Foot", new ArrayList<>(List.of(r2_5)));
        map2 = new SubwayMap("Linked Lines", new ArrayList<>(List.of(r2_1, r2_2, r2_3, r2_4, r2_5)));

        r3_1 = new Route("P Line");
        r3_2 = new Route("NP Line");
        s3_1 = new Stop("Linear", new ArrayList<>(List.of(r3_1)));
        s3_2 = new Stop("Quadratic", new ArrayList<>(List.of(r3_1))); //   1   3
        s3_3 = new Stop("Hard", new ArrayList<>(List.of(r3_2))); //      P |   | NP
        s3_4 = new Stop("Complete", new ArrayList<>(List.of(r3_2))); //    2   4
        map3 = new SubwayMap("Complex City", new ArrayList<>(List.of(r3_1, r3_2)));

        map4 = new SubwayMap("Zeroville", new ArrayList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
        new SubwayMap(null, new ArrayList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullRoutes() {
        new SubwayMap("Error", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullRouteInRoutes() {
        new SubwayMap("Error", new ArrayList<>(Arrays.asList(r2_1, null)));
    }

    @Test
    public void testPrintName() {
        assertEquals("Complex City", map3.printName());
    }

    @Test
    public void testPrintPaths() {
        assertEquals("P Line, NP Line", map3.printPaths());
    }

    @Test
    public void testHasStopTrue() {
        assertTrue(map3.hasStop(s3_3));
    }

    @Test
    public void testHasStopFalse() {
        assertFalse(map2.hasStop(s1_2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHasStopNull() {
        map1.hasStop(null);
    }

    @Test
    public void testGetStopByNameValid() {
        assertEquals(s2_6, map2.getStopByName("Foot"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetStopByNameNull() {
        map2.getStopByName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetStopByNameInvalid() {
        map2.getStopByName("Ankle");
    }

    @Test
    public void testMostRouteLongest() {
        assertEquals(r1_1, map1.mostRoute(true));
    }

    @Test
    public void testMostRouteShortest() {
        assertEquals(r1_1, map1.mostRoute(false));
    }


    @Test(expected = IllegalStateException.class)
    public void testMostRouteEmpty() {
        map4.mostRoute(true);
    }

    @Test
    public void testGetConnectorInfo() {
        assertEquals("""
                Neck: Charlie Line, Delta Line
                Body: Delta Line, Echo Line
                Waist: Echo Line, Foxtrot Line
                Leg: Foxtrot Line, Golf Line
                """, map2.getConnectorInfo());
    }

    @Test // check direct path using one line
    public void searchOneLine1() {
        assertEquals("Alfa Line", map1.findPath(s1_1, s1_3));
    }

    @Test // check direct path using another line
    public void searchOneLine2() {
        assertEquals("Bravo Line", map1.findPath(s1_4, s1_2));
    }

    @Test // check path using one connection stop and two lines
    public void searchTwoLines() {
        assertEquals("Alfa Line, Bravo Line", map1.findPath(s1_3, s1_5));
    }

    @Test // check path using five lines
    public void searchManyLines() {
        assertEquals("Charlie Line, Delta Line, Echo Line, Foxtrot Line, Golf Line", map2.findPath(s2_1, s2_6));
    }

    @Test // check that many-lines path is traversable backwards (same lines)
    public void searchManyLinesBackwards() {
        assertEquals("Golf Line, Foxtrot Line, Echo Line, Delta Line, Charlie Line", map2.findPath(s2_6, s2_1));
    }

    @Test // check if (malformed?) map's given two stations are disconnected
    public void searchDisjoint() {
        assertEquals("No path found.", map3.findPath(s3_1, s3_4));
    }

    @Test // case: start stop not present
    public void searchStartMissing() {
        assertEquals("A provided stop does not exist in this map.", map1.findPath(s3_3, s1_1));
    }

    @Test // case: destination stop not present
    public void searchDestMissing() {
        assertEquals("A provided stop does not exist in this map.", map3.findPath(s3_2, s2_5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullFindPathStart() {
        map3.findPath(null, s3_4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullFindPathDest() {
        map1.findPath(s1_5, null);
    }
}
