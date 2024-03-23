package utility.csv;

import data.Address;
import data.Coordinates;
import data.Organization;
import data.OrganizationType;
import utility.CollectionManager;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods for loading and saving collections to CSV files.
 */
public class CSVProcess {
    private static String pathToFile;
    private static ArrayDeque<Organization> organizations;

    /**
     * Retrieves the path to the CSV file.
     *
     * @return The path to the CSV file.
     */
    public static String getPathToFile() {
        return pathToFile;
    }

    /**
     * Sets the path to the CSV file.
     *
     * @param pathToFile The path to the CSV file.
     */
    public static void setPathToFile(String pathToFile) {
        CSVProcess.pathToFile = pathToFile;
    }

    /**
     * Sets the collection of organizations.
     *
     * @param organizations The collection of organizations.
     */
    public static void setCollection(ArrayDeque<Organization> organizations) {
        CSVProcess.organizations = organizations;
    }

    /**
     * Loads the collection from the specified CSV file.
     *
     * @param fileName The name of the CSV file to load the collection from.
     * @throws IllegalArgumentException If there is an error in the CSV format.
     */
    public static ArrayDeque<Organization> loadCollection(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            System.out.println("File name has not been provided!");
        } else {
            try {
                CSVReader.setPathToFile(fileName);
                List<String> parsedCSVFile = CSVReader.readFromFile();
                CollectionManager.initializationCollection();
                ArrayDeque<Organization> organizations = CollectionManager.getCollection();
                boolean isFirstLine = true;
                for (String line : parsedCSVFile) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }
                    String[] elements = line.split(",");
                    int id = Integer.parseInt(elements[0]);
                    String name = elements[1];
                    Long x = Long.valueOf(elements[2]);
                    long y = Long.parseLong(elements[3]);
                    Coordinates coordinates = new Coordinates(x, y);
                    Float annualTurnover = Float.valueOf(elements[4]);
                    String fullName = elements[5];
                    Integer employeesCount = Integer.valueOf(elements[6]);
                    OrganizationType type = OrganizationType.valueOf(elements[7]);
                    Address address = new Address(elements[8]);
                    Organization organization = new Organization(id, name, coordinates, annualTurnover, fullName, employeesCount, type, address);
                    organizations.add(organization);
                }
                CSVProcess.organizations = organizations;
            } catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException("CSV Format Violation!: " + exception.getMessage());
            }
        }
        return organizations;
    }

    /**
     * Writes the collection to the CSV file.
     *
     * @throws IllegalArgumentException If there is an error in the CSV format.
     */
    public static void writeCollection() {
        String[] headers = {"id", "name", "x", "y", "annual turnover",
                "full name", "employees count", "type", "postal address"};
        List<String> records = new ArrayList<>();

        for (Organization organization : CollectionManager.getCollection()) {
            String[] fields = {
                    String.valueOf(organization.getId()),
                    organization.getName(),
                    String.valueOf(organization.getCoordinates().getX()),
                    String.valueOf(organization.getCoordinates().getY()),
                    String.valueOf(organization.getAnnualTurnover()),
                    organization.getFullName(),
                    String.valueOf(organization.getEmployeesCount()),
                    organization.getType().toString(),
                    organization.getPostalAddress().getStreet()
            };
            records.add(String.join(",", fields));
        }

        CSVWriter.writeToFile(pathToFile, headers, records);
    }
}
