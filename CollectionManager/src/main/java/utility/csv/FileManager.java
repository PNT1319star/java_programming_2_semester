package utility.csv;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface FileManager {
    ArrayList<String> readFromFile (String pathToFile) throws IOException;
    void writeToFile (String pathToFile, String[] header, List<String> records) throws IOException;
}
