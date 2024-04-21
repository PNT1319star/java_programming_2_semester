package utilities;

import data.Organization;
import file.CSVProcess;
import utility.IDGenerator;

import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.stream.Collectors;

public class CollectionManager {
    private static ArrayDeque<Organization> arrayDeque;
    private static ZonedDateTime initializationDate;

    public static void initializationCollection() {
        arrayDeque = new ArrayDeque<>();
        initializationDate = ZonedDateTime.now();
    }

    public static boolean idExistence(int ID) {
        return arrayDeque.stream().anyMatch(organization -> organization.getId() == ID);
    }

    public static ArrayDeque<Organization> getCollection() {
        return arrayDeque;
    }

    public static String add(Organization organization) {
        organization.setId(IDGenerator.generateID());
        arrayDeque.add(organization);
        return "Organization has been added to the collection!";
    }

    public static String clear() {
        arrayDeque.clear();
        return "The collection is empty!";
    }

    public static String addIfMax(Organization organization) {
        if (arrayDeque.isEmpty() || arrayDeque.stream().allMatch(org -> org.compareTo(organization) <= 0)) {
            add(organization);
            return "The organization has been added to the collection!";
        }
        return "The organization can not be added to the collection!";
    }

    public static String info() {
        return "Type of collection: " + arrayDeque.getClass().getName() + ".\nInitialization Date: " + initializationDate
                + ".\nNumber of elements: " + arrayDeque.size() + ".";
    }

    public static String filterStartsWithFullName(String fullName) {
        StringBuilder string = new StringBuilder();
        arrayDeque.stream()
                .filter(organization -> organization.getFullName().startsWith(fullName))
                .forEach(string::append);
        return string.toString();
    }

    public static String head() {
        if (arrayDeque.isEmpty()) {
            return "The collection is empty!";
        }
        return arrayDeque.peekFirst().toString();
    }

    public static String minByCreationDate() {
        return arrayDeque.stream()
                .min(Comparator.comparing(Organization::getCreationDate))
                .map(Organization::toString)
                .orElse("The collection is empty");
    }

    public static String printUniquePostalAddress() {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        return arrayDeque.stream()
                .map(organization -> organization.getPostalAddress().getStreet())
                .distinct()
                .collect(Collectors.joining());
    }

    public static String removeByID(String sID) {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        Integer ID = Integer.parseInt(sID);
        arrayDeque.removeIf(organization -> organization.getId().equals(ID));
        return "The organization with this ID has been removed!";
    }

    public static String removeLower(Organization organization) {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        arrayDeque.removeIf(org -> org.compareTo(organization) < 0);
        return "All organizations that meet the conditions have been deleted!";
    }

    public static String show() {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        return arrayDeque.stream()
                .map(Organization::toString)
                .collect(Collectors.joining());
    }

    public static void updateElement(Organization newOrganization, Integer ID) {
        arrayDeque.forEach(organization -> {
            if (organization.getId().equals(ID)) {
                organization.setName(newOrganization.getName());
                organization.setCoordinates(newOrganization.getCoordinates());
                organization.setAnnualTurnover(newOrganization.getAnnualTurnover());
                organization.setType(newOrganization.getType());
                organization.setEmployeesCount(newOrganization.getEmployeesCount());
                organization.setFullName(newOrganization.getFullName());
                organization.setPostalAddress(newOrganization.getPostalAddress());
            }
        });
    }
    public static void getCollectionFromFile(String fileName) {
        arrayDeque = CSVProcess.loadCollection(fileName);
    }
}

