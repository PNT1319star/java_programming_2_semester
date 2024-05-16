package utilities;

import data.Organization;
import database.DatabaseCollectionManager;
import exceptions.HandlingDatabaseException;
import interaction.OrganizationRaw;
import utility.ConsolePrinter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class CollectionManager {
    private static ArrayDeque<Organization> arrayDeque;
    private static ZonedDateTime initializationDate;
    private static DatabaseCollectionManager databaseCollectionManager;
    private static String username;
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    public static boolean idExistence(int ID) {
        return arrayDeque.stream().anyMatch(organization -> organization.getId() == ID);
    }

    public static ArrayDeque<Organization> getCollection() {
        return arrayDeque;
    }

    public static String add(OrganizationRaw organization) throws IOException {
        try {
            reentrantLock.lock();
            if (databaseCollectionManager.insertOrganization(organization, username)) {
                databaseCollectionManager.loadCollection();
                return "Organization has been added to the collection!";
            } else {
                return "Organization existed in the collection!";
            }
        } catch (HandlingDatabaseException exception) {
            throw new IOException();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static String clear() throws IOException {
        try {
            reentrantLock.lock();
            if (databaseCollectionManager.deleteOrganizationByUsername(username)) {
                databaseCollectionManager.loadCollection();
                return "All your organizations have been deleted!";
            } else {
                throw new HandlingDatabaseException();
            }
        } catch (HandlingDatabaseException exception) {
            throw new IOException();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static String addIfMax(OrganizationRaw organization) throws IOException {
        if (arrayDeque.isEmpty() || arrayDeque.stream().allMatch(org -> org.compareTo(organization.getAnnualTurnover()) <= 0)) {
            try {
                reentrantLock.lock();
                if (databaseCollectionManager.insertOrganization(organization, username)) {
                    databaseCollectionManager.loadCollection();
                    return "Organization has been added to the collection!";
                } else {
                    throw new HandlingDatabaseException();
                }
            } catch (HandlingDatabaseException exception) {
                throw new IOException();
            } finally {
                reentrantLock.unlock();
            }
        }
        return "The organization can not be added to the collection!";
    }

    public static String info() {
        return "Type of collection: " + arrayDeque.getClass().getName() + ".\nInitialization Date: " + initializationDate
                + ".\nNumber of elements: " + arrayDeque.size() + ".";
    }

    public static String filterStartsWithFullName(String fullName) {
        try {
            reentrantLock.lock();
            StringBuilder string = new StringBuilder();
            arrayDeque.stream()
                    .filter(organization -> organization.getFullName().startsWith(fullName))
                    .forEach(string::append);
            return string.toString();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static String head() {
        try {
            reentrantLock.lock();
            if (arrayDeque.isEmpty()) {
                return "The collection is empty!";
            }
            return arrayDeque.peekFirst().toString();
        } finally {
            reentrantLock.unlock();
        }
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

    public static String removeByID(String sID) throws IOException {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        try {
            reentrantLock.lock();
            int Id = Integer.parseInt(sID);
            if (databaseCollectionManager.deleteOrganizationById(Id, username)) {
                databaseCollectionManager.loadCollection();
                return "The organization with this ID has been removed!";
            } else {
                throw new HandlingDatabaseException();
            }
        } catch (HandlingDatabaseException exception) {
            throw new IOException();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static String removeLower(OrganizationRaw organization) {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        arrayDeque.removeIf(org -> org.compareTo(organization.getAnnualTurnover()) < 0);
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

    public static void loadCollectionFromDatabase() {
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

    public static void setUsername(String username) {
        CollectionManager.username = username;
    }

    public static void setDatabaseCollectionManager(DatabaseCollectionManager databaseCollectionManager) {
        CollectionManager.databaseCollectionManager = databaseCollectionManager;
    }
}

