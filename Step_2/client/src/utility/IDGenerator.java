package utility.creator;

import java.util.HashSet;
import java.util.Random;

/**
 * Utility class for generating unique IDs.
 */
public class IDGenerator {
    private static HashSet<Integer> IDs = new HashSet<>();

    /**
     * Generates a unique ID.
     *
     * @return the generated unique ID.
     */
    public static int generateID() {
        int id = new Random().nextInt(30000) + 1;
        while (IDs.contains(id)) {
            id = new Random().nextInt(30000) + 1;
        }
        IDs.add(id);
        return id;
    }
}