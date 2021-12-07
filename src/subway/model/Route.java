package subway.model;

import java.util.*;

/**
 * Represents a subway line. Routes are comprised of stops in a linear order.
 */
public class Route {
    private final String name;
    private final List<Stop> stops;

    /**
     * Constructs a route.
     * @param name the name of this route
     * @throws IllegalArgumentException if the name is null
     */
    public Route(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Null parameter.");
        }
        this.name = name;
        this.stops = new ArrayList<>();
    }

    /**
     * Prints the name of this route.
     * @return this route's name
     */
    public String printName() { return name; }

    /**
     * Gets the list of stops in this route.
     * @return this route's stops
     */
    public List<Stop> getStops() { return List.copyOf(stops); }

    /**
     * Checks if this route has the given stop in its line.
     * @param s the stop to check
     * @return if the stop is in this route
     * @throws IllegalArgumentException if the stop is null
     */
    public boolean hasStop(Stop s) {
        if (s == null) {
            throw new IllegalArgumentException("Null stop.");
        }
        return stops.contains(s);
    }

    /**
     * Finds a stop in this route that matches the given name, if it exists.
     * @param name the name of the stop
     * @return the stop
     * @throws IllegalArgumentException if the name is null or does not correspond to a stop in this route
     */
    public Stop getStopByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Null name.");
        }
        for (Stop s: stops) {
            if (s.printName().equals(name)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Stop does not exist.");
    }

    /**
     * Adds the given stop to the end of this route.
     * @param s the new stop
     * @throws IllegalArgumentException if the stop is null or is already in this route
     */
    public void addStop(Stop s) {
        if (s == null) {
            throw new IllegalArgumentException("Null stop.");
        }
        else if (hasStop(s)) {
            throw new IllegalArgumentException("Stop already in route.");
        }
        stops.add(s);
    }

    /**
     * Gets the size of this route, in stops.
     * @return this route's number of stops
     */
    public int length() { return stops.size(); }

    /**
     * Checks if this route has fewer stops than the given route.
     * @param that the route to compare to
     * @return if the other route has a greater number of stops
     * @throws IllegalArgumentException if the given route is null
     */
    public boolean isShorter(Route that) {
        if (that == null) {
            throw new IllegalArgumentException("Null stop.");
        }
        return this.length() < that.length();
    }

    /**
     * Returns a list of stops in this route which are located in more than one route.
     * @return a list of intersections in this route
     */
    public List<Stop> connectorStops() {
        return stops.stream().filter(Stop::isConnector).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Route r) || this.length() != r.length()) {
            return false;
        }
        for (int i = 0; i < getStops().size(); i++) {
            if (this.getStops().get(i) != r.getStops().get(i)) {
                return false;
            }
        }
        return printName().equals(r.printName());
    }

    @Override
    public int hashCode() { return Objects.hash(name, stops); }
}
