package utility.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The CSVManager class implements the FileManager interface and provides methods for reading from and writing to CSV files.
 */
public class CSVManager implements FileManager {
    private static boolean flag = false;

    /**
     * Reads the contents of a CSV file and returns them as a list of lines.
     *
     * @param pathToFile The path to the CSV file.
     * @return A list of lines read from the CSV file.
     */
    @Override
    public ArrayList<String> readFromFile(String pathToFile) {
        ArrayList<String> lineList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(pathToFile))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                flag = true;
                StringBuilder stringBuilder = new StringBuilder();
                for (String element : nextLine) {
                    if (!stringBuilder.isEmpty()) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(element.trim());
                }
                lineList.add(stringBuilder.toString());
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException("CSV format violation: " + exception.getMessage());
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return lineList;
    }

    /**
     * Writes the specified header and records to a CSV file.
     *
     * @param pathToFile The path to the CSV file.
     * @param header     The header line to be written to the CSV file.
     * @param records    The list of records to be written to the CSV file.
     * @throws IllegalArgumentException If there is an error writing the CSV file.
     */
    @Override
    public void writeToFile(String pathToFile, String[] header, List<String> records) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(pathToFile))) {
            csvWriter.writeNext(header);
            for (String record : records) {
                String[] fields = record.split(",");
                csvWriter.writeNext(fields);
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException("Error writing CSV file: " + exception.getMessage());
        }
    }

    /**
     * Retrieves the flag indicating whether the CSV file was successfully read.
     *
     * @return The flag indicating whether the CSV file was successfully read.
     */
    public static boolean getFlag() {
        return flag;
    }
}
