package subway.model;

import subway.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a subway rail system. Subways are a set of stops organized into routes which connect with each
 * other at certain stops, or connectors.
 */
public class SubwayMap {
    private final String name;
    private final List<Route> routes;

    /**
     * Constructs a subway.
     * @param name the name of this system
     * @param routes the set of routes that comprise this subway, stops are already assumed to be in the routes
     * @throws IllegalArgumentException if any parameter is null or contains a null value
     */
    public SubwayMap(String name, List<Route> routes) {
        if (name == null || routes == null || routes.contains(null)) {
            throw new IllegalArgumentException("Null parameter.");
        }
        this.name = name;
        this.routes = routes;
    }

    /**
     * Prints the name of this subway.
     * @return this subway's name
     */
    public String printName() { return name; }

    /**
     * Prints the routes of this subway in a formatted list.
     * @return this subway's routes
     */
    public String printPaths() {
        return Utils.niceList(routes.stream().map(Route::printName).toList());
    }

    /**
     * Checks whether the given stop is in this subway.
     * @param s the stop
     * @return true if the stop is in this subway
     * @throws IllegalArgumentException if the stop is null
     */
    public boolean hasStop(Stop s) {
        if (s == null) {
            throw new IllegalArgumentException("Null stop.");
        }
        return routes.stream().anyMatch(route -> route.hasStop(s));
    }

    /**
     * Finds a stop in this subway that matches the given name, if it exists.
     * @param name the name of the stop
     * @return the stop
     * @throws IllegalArgumentException if the name is null or does not correspond to a stop in this subway
     */
    public Stop getStopByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Null name.");
        }
        for (Route r: routes) {
            try {
                return r.getStopByName(name);
            } catch (IllegalArgumentException ignored) {}
        }
        throw new IllegalArgumentException("Stop does not exist.");
    }

    /**
     * Determines the route in this subway with the longest or shortest length, depending on the given boolean. Route
     * length is measured in number of stops.
     * @param longest true if we are checking for the longest route, false if we are checking for the shortest route
     * @return the longest or shortest route in this subway
     * @throws IllegalStateException if there are no routes in this subway
     */
    public Route mostRoute(boolean longest) {
        if (routes.isEmpty()) {
            throw new IllegalStateException("No routes in this subway.");
        }

        Route result = routes.get(0);
        for (Route r: routes) {
            if (longest ? result.isShorter(r) : r.isShorter(result)) {
                result = r;
            }
        }
        return result;
    }

    /**
     * Finds all stops in this subway which service more than one route, and formats them into a printed list along with
     * their relevant routes.
     * @return all intersection stops in this subway and their routes
     */
    public String getConnectorInfo() {
        List<Stop> connectors = new ArrayList<>();
        for (Route r: routes) {
            connectors.addAll(r.connectorStops());
        }
        connectors = connectors.stream().distinct().toList();

        StringBuilder result = new StringBuilder();
        for (Stop conn: connectors) {
            result.append(String.format("%s: %s\n", conn.printName(),
                    Utils.niceList(conn.getRoutes().stream().map(Route::printName).toList())));
        }
        return result.toString();
    }

    /**
     * Prints a list of routes to travel across in order to get from one stop to another stop in this system.
     * If the stops are the same, indicates that no lines are used. If the stops are disjoint, indicates that no path
     * exists in the subway.
     * @param start the starting stop to search from
     * @param dest the goal stop to end at
     * @return the path from the start to destination stop
     * @throws IllegalArgumentException if either given stop is null
     */
    public String findPath(Stop start, Stop dest) {
        if (start == null || dest == null) {
            throw new IllegalArgumentException("Null parameter.");
        }
        else if (!hasStop(start) || !hasStop(dest)) {
            return "A provided stop does not exist in this map.";
        }
        else if (start.equals(dest)) {
            return "No lines needed!";
        }

        return findSubpath(start, dest, new ArrayList<>());
    }

    /**
     * Recurs on the routes and connectors in order to either find the destination stop via a DFS-like search or indicate
     * that no such path exists.
     * @param start the current stop to search from
     * @param dest the goal stop to end at
     * @param used accumulator that tracks the routes already used
     * @return the path from the start to destination stop
     */
    private String findSubpath(Stop start, Stop dest, List<Route> used) {
        if (start.equals(dest)) {
            return "";
        }

        for (Route r: start.getRoutes()) {
            if (!used.contains(r)) {
                if (r.hasStop(dest)) return r.printName();

                ArrayList<Route> copy = new ArrayList<>(used);
                copy.add(r);
                for (Stop conn: r.connectorStops()) {
                    if (!conn.equals(start) && !findSubpath(conn, dest, copy).contains("No path found.")) {
                        return r.printName() + ", " + findSubpath(conn, dest, copy);
                    }
                }
            }
        }
        return "No path found.";
    }
}
