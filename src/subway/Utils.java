package subway;

import java.util.*;

public class Utils {
    /**
     * Formats the list of strings into one string. A list [x, y, z] would look like "x, y, z".
     * @param items the items to format
     * @return the formatted list string
     */
    public static String niceList(List<String> items) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            result.append(items.get(i)).append(i < items.size() - 1 ? ", " : "");
        }
        return result.toString();
    }
}
