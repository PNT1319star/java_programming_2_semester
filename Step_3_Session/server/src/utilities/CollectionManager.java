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
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class CollectionManager {
    private static ArrayDeque<Organization> arrayDeque;
    private static ZonedDateTime initializationDate;
    private static DatabaseCollectionManager databaseCollectionManager;
    private static int userId;
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
            if (databaseCollectionManager.insertOrganization(organization, userId)) {
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
            if (databaseCollectionManager.deleteOrganizationByUsername(userId)) {
                databaseCollectionManager.loadCollection();
                return "All your organizations have been deleted!";
            } else {
                databaseCollectionManager.loadCollection();
                return "You didn't add any organizations!";
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
                if (databaseCollectionManager.insertOrganization(organization, userId)) {
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
            if (string.toString().isEmpty()) return "There is no organizations with this full name!";
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
            if (databaseCollectionManager.deleteOrganizationById(Id, userId)) {
                databaseCollectionManager.loadCollection();
                return "The organization with this ID has been removed!";
            } else {
                databaseCollectionManager.loadCollection();
                return "You can not remove this organization!";
            }
        } catch (HandlingDatabaseException exception) {
            throw new IOException();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static String removeLower(OrganizationRaw organization) throws IOException {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        try {
            reentrantLock.lock();
            for (Organization organization1 : arrayDeque) {
                if (organization1.getAnnualTurnover() < organization.getAnnualTurnover() && databaseCollectionManager.getUserIdByUsername(organization1.getUsername()) == userId) {
                    if (databaseCollectionManager.deleteOrganizationById(organization1.getId(), userId)) {
                        databaseCollectionManager.loadCollection();
                    } else {
                        return "There is no organizations that meet the conditions have been deleted!";
                    }
                }
            }
        } catch (HandlingDatabaseException exception) {
            throw new IOException();
        } finally {
            reentrantLock.unlock();
        }
        return "All organizations that meet the conditions have been deleted!";
    }

    public static String show() {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        try {
            reentrantLock.lock();
            return arrayDeque.stream()
                    .map(Organization::toString)
                    .collect(Collectors.joining());
        } finally {
            reentrantLock.unlock();
        }
    }

    public static boolean updateElement(OrganizationRaw newOrganization, Integer ID) throws IOException {
        try {
            reentrantLock.lock();
            Optional<Organization> organizationOpt = arrayDeque.stream()
                    .filter(org -> Objects.equals(org.getId(), ID))
                    .findFirst();
            if (organizationOpt.isPresent()) {
                Organization organization = organizationOpt.get();
                if (databaseCollectionManager.getUserIdByUsername(organization.getUsername()) == userId) {
                    databaseCollectionManager.updateOrganizationById(ID, newOrganization);
                    databaseCollectionManager.loadCollection();
                    return true;
                }
            } else {
                return false;
            }
        } catch (HandlingDatabaseException e) {
            throw new IOException();
        } finally {
            reentrantLock.unlock();
        }
        return false;
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

    public static void setUsername(int userId) {
        CollectionManager.userId = userId;
    }

    public static void setDatabaseCollectionManager(DatabaseCollectionManager databaseCollectionManager) {
        CollectionManager.databaseCollectionManager = databaseCollectionManager;
    }
}

