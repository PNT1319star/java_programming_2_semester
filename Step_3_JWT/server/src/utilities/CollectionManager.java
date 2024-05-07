package utilities;

import data.Organization;
import database.DatabaseCollectionManager;
import exceptions.HandlingDatabaseException;
import interaction.OrganizationRaw;
import utility.ConsolePrinter;
import utility.IDGenerator;

import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.stream.Collectors;

public class CollectionManager {
    private ArrayDeque<Organization> arrayDeque;
    private ZonedDateTime initializationDate;
    private final DatabaseCollectionManager databaseCollectionManager;

    public CollectionManager(DatabaseCollectionManager databaseCollectionManager) {
        this.databaseCollectionManager = databaseCollectionManager;
    }

    public boolean idExistence(int ID) {
        return arrayDeque.stream().anyMatch(organization -> organization.getId() == ID);
    }

    public ArrayDeque<Organization> getCollection() {
        return arrayDeque;
    }

    public String add(OrganizationRaw organization) {
        databaseCollectionManager.insertOrganization(organization,)
        return "Organization has been added to the collection!";
    }

    public String clear() {
        arrayDeque.clear();
        return "The collection is empty!";
    }

    public String addIfMax(Organization organization) {
        if (arrayDeque.isEmpty() || arrayDeque.stream().allMatch(org -> org.compareTo(organization) <= 0)) {
            add(organization);
            return "The organization has been added to the collection!";
        }
        return "The organization can not be added to the collection!";
    }

    public String info() {
        return "Type of collection: " + arrayDeque.getClass().getName() + ".\nInitialization Date: " + initializationDate
                + ".\nNumber of elements: " + arrayDeque.size() + ".";
    }

    public String filterStartsWithFullName(String fullName) {
        StringBuilder string = new StringBuilder();
        arrayDeque.stream()
                .filter(organization -> organization.getFullName().startsWith(fullName))
                .forEach(string::append);
        return string.toString();
    }

    public String head() {
        if (arrayDeque.isEmpty()) {
            return "The collection is empty!";
        }
        return arrayDeque.peekFirst().toString();
    }

    public String minByCreationDate() {
        return arrayDeque.stream()
                .min(Comparator.comparing(Organization::getCreationDate))
                .map(Organization::toString)
                .orElse("The collection is empty");
    }

    public String printUniquePostalAddress() {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        return arrayDeque.stream()
                .map(organization -> organization.getPostalAddress().getStreet())
                .distinct()
                .collect(Collectors.joining());
    }

    public String removeByID(String sID) {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        Integer ID = Integer.parseInt(sID);
        arrayDeque.removeIf(organization -> organization.getId().equals(ID));
        return "The organization with this ID has been removed!";
    }

    public String removeLower(Organization organization) {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        arrayDeque.removeIf(org -> org.compareTo(organization) < 0);
        return "All organizations that meet the conditions have been deleted!";
    }

    public String show() {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        return arrayDeque.stream()
                .map(Organization::toString)
                .collect(Collectors.joining());
    }

    public void updateElement(Organization newOrganization, Integer ID) {
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

    public void loadCollectionFromDatabase() {
        try {
            arrayDeque = databaseCollectionManager.loadCollection();
            initializationDate = ZonedDateTime.now();
            ConsolePrinter.printResult("Collection has been loaded!");
        } catch (HandlingDatabaseException exception) {
            arrayDeque = new ArrayDeque<>();
            initializationDate = ZonedDateTime.now();
            ConsolePrinter.printError("Collection has not been loaded!");
        }
    }
}

