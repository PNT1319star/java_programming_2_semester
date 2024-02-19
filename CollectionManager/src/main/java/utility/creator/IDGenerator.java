package utility.creator;

import java.util.HashSet; //Sử dụng HashSet để đảm bảo là mỗi phần tử là duy nhất
import java.util.Random;

/**
 * Utility class for generating unique IDs.
 */
public class IDGenerator {
    private static HashSet<Integer> IDs = new HashSet<>();
    public static  int generateID() {
        Integer id = new Random().nextInt(Integer.MAX_VALUE) + 1;
        if(IDs.contains(id)) {
            while(IDs.contains(id)){
                id = new Random().nextInt(Integer.MAX_VALUE) + 1;
            }
        }
        IDs.add(id);
        return id;
    }
}
