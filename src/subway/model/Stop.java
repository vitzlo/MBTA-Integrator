package subway.model;

import java.util.*;

/**
 * Represents a subway station. Stations are situated in at least one route in the subway.
 */
public class Stop {
    private final String name;
    private final List<Route> routes;

    /**
     * Constructs a stop.
     * @param name the name of this stop
     * @param routes the routes on which this stop lies
     * @throws IllegalArgumentException if any parameter is null or contains a null value
     */
    public Stop(String name, List<Route> routes) {
        if (name == null || routes == null || routes.contains(null)) {
            throw new IllegalArgumentException("Null parameter.");
        }
        this.name = name;
        this.routes = routes;
        for (Route r: routes) {
            r.addStop(this);
        }
    }

    /**
     * Prints the name of this stop.
     * @return this stop's name
     */
    public String printName() { return name; }

    /**
     * Gets the list of routes in this stop.
     * @return this stop's routes
     */
    public List<Route> getRoutes() { return List.copyOf(routes); }

    /**
     * Checks if this stop has more than one route.
     * @return if this stop is an intersection
     */
    public boolean isConnector() { return routes.size() > 1; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Stop s)) {
            return false;
        }
        /*
        else if (this.getRoutes().size() != s.getRoutes().size()) return false;
        for (int i = 0; i < getRoutes().size(); i++) {
            if (this.getRoutes().get(i) != s.getRoutes().get(i)) return false;
        } */
        return printName().equals(s.printName());
    }

    @Override
    // Including the reference to stops in Route and the reference to routes in Stop creates a hashing loop. The design
    // choice was made to define equality between stops only by name, conducive to Question 3's task of user input of
    // stop names. In order to keep hashCode() and equals() consistent, equals() also does not check for routes equality.
    public int hashCode() { return Objects.hash(name); }
}
