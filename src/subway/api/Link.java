package subway.api;

import subway.model.Route;
import subway.model.Stop;
import subway.model.SubwayMap;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Link {
    /**
     * Retrieves information from the MBTA API, specifically the light and heavy rail routes, and returns the information
     * in the form of this program's data structure for subways. Requests that take longer than 15 seconds will time out.
     * @return the SubwayMap representing Boston's T subway system
     * @throws IllegalStateException if a URL or I/O issue causes an error
     */
    public static SubwayMap getBostonMap() {
        String apiKey = "c02ec40e74094c7a8130f0c5f0a45f1a";
        List<Route> routes = new ArrayList<>(); // used as parameter in final SubwayMap constructor
        HashMap<String, Route> routeIds = new HashMap<>();
        HashMap<String, List<Route>> stopRoutes = new HashMap<>();

        try {
            // filters the routes via the server API to light and heavy rail, and retrieves their long names and ids;
            // the decision to rely on the server API for the filtering was to alleviate some manual info parsing, as
            // a simple addition to the request eliminated the need for the work
            URI urlRoutes = new URI("https://api-v3.mbta.com/routes?filter[type]=0,1&fields[route]=long_name,id");
            HttpRequest req = HttpRequest.newBuilder().uri(urlRoutes).timeout(Duration.of(15, ChronoUnit.SECONDS))
                    .header("Authorization", apiKey).GET().build();
            HttpResponse<String> resp = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString());

            // while this isn't the best implementation for analyzing JSONs, the need for downloading external libraries
            // which come with JSON object classes is eliminating by passing through it manually; this implementation
            // thus is request-dependent in terms of work achieved
            String parseBody = resp.body();
            while (parseBody.contains("\"long_name\"")) {
                parseBody = parseBody.substring(parseBody.indexOf("\"long_name\"") + 13);
                routes.add(new Route(parseBody.substring(0, parseBody.indexOf("\""))));
                parseBody = parseBody.substring(parseBody.indexOf("\"id\"") + 6); // every id after the long_name matches to it
                routeIds.put(parseBody.substring(0, parseBody.indexOf("\"")), routes.get(routes.size() - 1)); // so we can map them
            }

            // after all the routes and their ids are retrieved, the stops are then added
            for (String id: routeIds.keySet()) {
                // filters the stops via the server API to only those on the given route (supplied via the route's id)
                URI urlStops = new URI("https://api-v3.mbta.com/stops?include=route&filter[route]=" + id + "&fields[stop]=name");
                req = HttpRequest.newBuilder().uri(urlStops).timeout(Duration.of(15, ChronoUnit.SECONDS))
                        .header("Authorization", apiKey).GET().build();
                resp = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString());

                // see above comment for JSON parsing notes
                parseBody = resp.body();
                while (parseBody.contains("\"name\"")) {
                    parseBody = parseBody.substring(parseBody.indexOf("\"name\"") + 8);
                    String currentName = parseBody.substring(0, parseBody.indexOf("\""));
                    if (stopRoutes.containsKey(currentName)) {
                        stopRoutes.get(currentName).add(routeIds.get(id));
                    }
                    else {
                        stopRoutes.put(currentName, new ArrayList<>(List.of(routeIds.get(id))));
                    }
                }
            }

            // create the stops, as each one has been assigned all of its routes
            for (String stopName: stopRoutes.keySet()) {
                new Stop(stopName, stopRoutes.get(stopName));
            }

            return new SubwayMap("Boston Subway", routes);

        } catch (URISyntaxException e) {
            throw new IllegalStateException("Malformed URL (should never happen).");
        } catch (IOException ioe) {
            throw new IllegalStateException("IO Error");
        } catch (InterruptedException ie) {
            throw new IllegalStateException("Thread interruption error");
        }
    }
}
