package utilities;

import org.csjchoisoojong.data.Organization;
import database.DatabaseCollectionManager;
import org.csjchoisoojong.exceptions.HandlingDatabaseException;
import org.csjchoisoojong.interaction.OrganizationRaw;
import org.csjchoisoojong.utility.ConsolePrinter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;
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
                return "AddSuccess";
            } else {
                return "AddFailure";
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
                return "ClearSuccess";
            } else {
                databaseCollectionManager.loadCollection();
                return "ClearFailure";
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
                    return "AddIfMaxSuccess";
                } else {
                    throw new HandlingDatabaseException();
                }
            } catch (HandlingDatabaseException exception) {
                throw new IOException();
            } finally {
                reentrantLock.unlock();
            }
        }
        return "AddIfMaxFailure";
    }

    public static String info() {
        return "Type of collection: " + arrayDeque.getClass().getName() + ".\nInitialization Date: " + initializationDate
                + ".\nNumber of elements: " + arrayDeque.size() + ".";
    }

    public static String removeByID(String sID) throws IOException {
        if (arrayDeque.isEmpty()) return "EmptyCollection";
        try {
            reentrantLock.lock();
            int Id = Integer.parseInt(sID);
            if (databaseCollectionManager.deleteOrganizationById(Id, userId)) {
                databaseCollectionManager.loadCollection();
                return "RemoveSuccess";
            } else {
                databaseCollectionManager.loadCollection();
                return "RemoveFailure";
            }
        } catch (HandlingDatabaseException exception) {
            throw new IOException();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static String removeLower(OrganizationRaw organization) throws IOException {
        if (arrayDeque.isEmpty()) return "EmptyCollection";
        try {
            reentrantLock.lock();
            for (Organization organization1 : arrayDeque) {
                if (organization1.getAnnualTurnover() < organization.getAnnualTurnover() && databaseCollectionManager.getUserIdByUsername(organization1.getUsername()) == userId) {
                    if (databaseCollectionManager.deleteOrganizationById(organization1.getId(), userId)) {
                        databaseCollectionManager.loadCollection();
                    } else {
                        return "RemoveLowerFailure";
                    }
                }
            }
        } catch (HandlingDatabaseException exception) {
            throw new IOException();
        } finally {
            reentrantLock.unlock();
        }
        return "RemoveLowerSuccess";
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

    public static void setUserId(int userId){
        CollectionManager.userId = userId;
    }
    public static void setDatabaseCollectionManager(DatabaseCollectionManager databaseCollectionManager) {
        CollectionManager.databaseCollectionManager = databaseCollectionManager;
    }
}

