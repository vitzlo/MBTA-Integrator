package subway;

import subway.api.Link;
import subway.model.SubwayMap;

/** Task: Retrieve data from MBTA's API and print all subway routes
 *
 */
public class Question1 {
    protected static SubwayMap subway = Link.getBostonMap();
    public static void main(String[] args) {
        q1();
    }

    protected static void q1() {
        System.out.printf("The below data is for the %s.\n", subway.printName());
        System.out.printf("==========QUESTION 1==========\nLines: %s.\n", subway.printPaths());
    }
}
