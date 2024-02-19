package utility.csv;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVManager implements FileManager {
    @Override
    public ArrayList<String> readFromFile(String pathToFile) {
        ArrayList<String> lineList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(pathToFile);
             CSVParser csvParser = new CSVParser(fileReader,CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreSurroundingSpaces())) {

            for (CSVRecord record : csvParser) {
                StringBuilder stringBuilder = new StringBuilder();
                for(String element : record) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(element.trim());
                }
                lineList.add(stringBuilder.toString());
            }
        } catch (IOException | IllegalArgumentException exception) {
            throw new IllegalArgumentException("CSV format violation: " + exception.getMessage());
        }

        return lineList;
    }

    @Override
    public void writeToFile(String pathToFile, String[] header, List<String> records) throws IOException {
        try {
            PrintWriter fileWriter = new PrintWriter(new FileWriter(pathToFile));
            CSVPrinter csvPrinter = new CSVPrinter(fileWriter,CSVFormat.DEFAULT.withHeader("id", "name", "x", "y", "annual turnover",
                    "full name","employees count", "type", "postal address"));
            for (String record : records) {
                csvPrinter.printRecord(record.split(","));
            }

            csvPrinter.flush();
            csvPrinter.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
