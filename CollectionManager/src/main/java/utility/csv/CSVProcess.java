package utility.csv;

import data.Address;
import data.Coordinates;
import data.Organization;
import data.OrganizationType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import utility.CollectionManager;
import utility.creator.IDGenerator;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class CSVProcess {
    private static String pathToFile;
    private static ArrayDeque<Organization> organizations;
    public static ArrayDeque<Organization> getOrganizations() {
        return  organizations;
    }
    public static void setPathToFile(String pathToFile) {
        CSVProcess.pathToFile = pathToFile;
    }

    public static void setCollection(ArrayDeque<Organization> organizations) {
        CSVProcess.organizations = organizations;
    }
    public static void loadCollection(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            System.out.println("File name has not been provided!");
        } else {
            try {
                CSVManager csvManager = new CSVManager();
                ArrayList<String> parsedCSVFile = csvManager.readFromFile(fileName);
                ArrayDeque<Organization> organizations = new ArrayDeque<>();
                CSVParser csvParser = CSVParser.parse(String.join("\n", parsedCSVFile), CSVFormat.DEFAULT.withHeader("id", "name", "x", "y", "creationTime", "annual turnover",
                        "full name","employees count", "type", "postal address"));
                for (CSVRecord element : csvParser) {
                    int id = IDGenerator.generateID();
                    String name = element.get("name");
                    Long x = Long.valueOf(element.get("x"));
                    long y = Long.parseLong(element.get("y"));
                    Coordinates coordinates = new Coordinates(x,y);
                    Float annualTurnover = Float.valueOf(element.get("annual turnover"));
                    String fullName = element.get("full name");
                    Integer employeesCount = Integer.valueOf(element.get("employees count"));
                    OrganizationType type = OrganizationType.valueOf(element.get("type"));
                    Address address = new Address(element.get("postal address"));
                    Organization organization = new Organization(id,name,coordinates,annualTurnover,fullName,employeesCount,type,address);
                    organizations.add(organization);
                }
                CSVProcess.organizations = organizations;
            } catch (IOException | IllegalArgumentException exception) {
                throw new IllegalArgumentException("CSV Format Violation!: " + exception.getMessage());
            }
        }
    }

    public static void writeCollection() {
        try {
            String[] headers = {"id", "name", "x", "y", "annual turnover",
                    "full name","employees count", "type", "postal address"};
            List<String> records = new ArrayList<>();
            for (Organization organization: CollectionManager.getCollection()) {
                records.add(organization.getId() + "," + organization.getName() + "," + organization.getCoordinates().getX() + "," +
                        organization.getCoordinates().getY() + "," + organization.getAnnualTurnover() + "," +
                        organization.getFullName() + "," + organization.getEmployeesCount() + "," + organization.getType() + "," + organization.getPostalAddress().getStreet());
            }
            CSVManager csvManager = new CSVManager();
            csvManager.writeToFile(pathToFile, headers, records);
        } catch (IOException |IllegalArgumentException exception) {
            throw new IllegalArgumentException("CSV Format Violation: " + exception.getMessage());
        }
    }
}
