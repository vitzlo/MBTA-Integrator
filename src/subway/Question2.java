package subway;

import subway.model.Route;

/** Task: Print longest subway route and its number of stops.
 *  Task: Print shortest subway route and its number of stops.
 *  Task: Print all stops that connect multiple lines and their respective lines.
 */
public class Question2 extends Question1 {
    public static void main(String[] args) {
        q2();
    }

    protected static void q2() {
        q1();
        Route longest = subway.mostRoute(true);
        Route shortest = subway.mostRoute(false);
        String connectors = subway.getConnectorInfo();
        System.out.printf("==========QUESTION 2==========\nThe route with the most stops is %s, with %d stops.\n",
                longest.printName(), longest.length());
        System.out.printf("The route with the least stops is %s, with %d stops.\n", shortest.printName(), shortest.length());
        System.out.printf("Stops that contain two or more routes and their routes:\n%s", connectors);
    }
}
