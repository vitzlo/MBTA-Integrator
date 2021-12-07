package subway;

import subway.model.Stop;

/** Task: Allow user to give the names of two stops, and in return print the lines needed to travel from the first stop
 * to the second stop.
 */
public class Question3 extends Question2 {
    private final static String start = "Ruggles";
    private final static String dest = "Wood Island";

    public static void main(String[] args) {
        q3();
    }

    protected static void q3() {
        q2();
        System.out.println("==========QUESTION 3==========");
        Stop s, t;
        try {
            s = subway.getStopByName(start);
            t = subway.getStopByName(dest);
            System.out.printf("%s to %s: %s.\n", start, dest, subway.findPath(s, t));
        } catch (IllegalArgumentException iae) {
            System.out.printf("One of the given stops does not exist in the %s.\n", subway.printName());
        }
    }
}
