package utility;

import data.Organization;
import utility.creator.IDGenerator;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public class CollectionManager {
    private static boolean addIfMaxFlag;
    private static ArrayDeque<Organization> arrayDeque;
    private static ZonedDateTime initializationDate;

    public static void initializationCollection() {
        arrayDeque = new ArrayDeque<>();
        initializationDate = ZonedDateTime.now();
    }

    public static boolean idExistence(int ID) {
        for (Organization organization : arrayDeque) {
            if (organization.getId().intValue() == ID) {
                return true;
            }
        }
        return false;
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
        if (arrayDeque.isEmpty()) {
            add(organization);
            return "The organization has been added to the collection!";
        } else {
            addIfMaxFlag = true;
            for (Organization organization1 : arrayDeque) {
                if (organization1.compareTo(organization) > 0) {
                    addIfMaxFlag = false;
                    break;
                }
            }
            if (addIfMaxFlag) {
                add(organization);
                return "The organization has been added to the collection!";
            }
            return "The organization can not be added to the collection!";
        }
    }

    public static String info() {
        String out = "Type of collection: " + arrayDeque.getClass().getName() + ".\nInitialization Date: " + initializationDate
                + ".\nNumber of elements: " + arrayDeque.size() + ".";
        return out;
    }

    public static String filterStartsWithFullName(String fullName) throws IOException {
        StringBuilder string = new StringBuilder();
        arrayDeque.forEach(organization -> {
            if (organization.getFullName().startsWith(fullName)) {
                string.append(organization.toString());
            }
        });
        return String.valueOf(string);
    }

    public static String head() {
        if (arrayDeque.size() == 0) {
            return "The collection is empty!";
        }
        return arrayDeque.peekFirst().toString();
    }

    public static String minByCreationDate() {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        Organization minCreationDateOrganization = arrayDeque.getFirst();
        for (Organization organization : arrayDeque) {
            if (organization.getCreationDate().compareTo(minCreationDateOrganization.getCreationDate()) < 0) {
                minCreationDateOrganization = organization;
            }
        }
        return minCreationDateOrganization.toString();
    }

    public static String printUniquePostalAddress() {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        Set<String> uniquePostalAddresses = new HashSet<>();
        for (Organization organization : arrayDeque) {
            uniquePostalAddresses.add(organization.getPostalAddress().getStreet());
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : uniquePostalAddresses) {
            stringBuilder.append(string);
        }
        return String.valueOf(stringBuilder);
    }

    public static String removeByID(String sID) throws IOException {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        Integer ID = Integer.parseInt(sID);
        for (Organization organization : arrayDeque) {
            if (organization.getId().equals(ID)) {
                arrayDeque.remove(organization);
                break;
            }
        }
        return "The organization with this ID has been removed!";
    }

    public static String removeLower(Organization organization) {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        for (Organization organization1 : arrayDeque) {
            if (organization1.compareTo(organization) < 0) {
                arrayDeque.remove(organization1);
            }
        }
        return "All organizations that meet the conditions have been deleted!";
    }

    public static String show() {
        if (arrayDeque.isEmpty()) return "The collection is empty!";
        StringBuilder stringBuilder = new StringBuilder();
        for (Organization organization : arrayDeque) {
            stringBuilder.append(organization.toString());
        }
        return String.valueOf(stringBuilder);
    }
    public static void updateElement(Organization newOrganization, Integer ID) {
        arrayDeque.forEach(organization -> {
            if (organization.getId().equals(ID) ) {
                organization.setName(newOrganization.getName() );
                organization.setCoordinates(newOrganization.getCoordinates());
                organization.setAnnualTurnover(newOrganization.getAnnualTurnover());
                organization.setType(newOrganization.getType());
                organization.setEmployeesCount(newOrganization.getEmployeesCount());
                organization.setFullName(newOrganization.getFullName());
                organization.setPostalAddress(newOrganization.getPostalAddress());
            }
        });
    }
}
