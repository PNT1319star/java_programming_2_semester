package utility;

import data.Organization;
import utility.csv.CSVProcess;

import java.time.ZonedDateTime;
import java.util.*;

import exceptions.EmptyCollectionException;

public class CollectionManager {
    private static ArrayDeque<Organization> arrayDeque;
    private static ZonedDateTime initializationDate;
    public static void initializationCollection() {
        if (arrayDeque == null) {
            arrayDeque = new ArrayDeque<>();
            initializationDate = ZonedDateTime.now();
        }
    }

    public static ArrayDeque<Organization> getCollection() {
        return arrayDeque;
    }

    public static boolean idExistence (int ID) {
        for (Organization organization : arrayDeque) {
            return organization.getId().equals(ID);
        }
        return false;
    }

    public static void information() {
        System.out.println("Type of collection: " + arrayDeque.getClass().getName() + ".\nInitialization Date: " + initializationDate
                + ".\nNumber of elements: " + arrayDeque.size() + ".");

    }

    public static void fullInformation() {
        try{
            if (arrayDeque.isEmpty()) throw new EmptyCollectionException();
            for (Organization organization : arrayDeque) {
                System.out.println(organization.toString());
            }
        } catch (EmptyCollectionException exception) {
            System.out.println("The collection is empty.");
        }

    }

    public static void addOrganization(Organization organization) {
        arrayDeque.add(organization);
    }

    public static void addIfMax(Organization organization) {
        if (arrayDeque.size() == 0) {
            addOrganization(organization);
        } else {
            boolean flag = true;
            for (Organization organization1 : arrayDeque) {
                if (organization1.compareTo(organization) > 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) addOrganization(organization);
        }
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

    public static void clearCollection() {
        arrayDeque.clear();
    }

    public  static void removeElement(Integer ID) {
        for (Organization organization : arrayDeque) {
            if (organization.getId().equals(ID)) {
                arrayDeque.remove(organization);
                break;
            }
        }
    }

    public static void filterStartsWithFullName(String fullName) {
        arrayDeque.forEach(organization -> {
            if (organization.getFullName().startsWith(fullName)) {
                System.out.print(organization.toString());
            }
        });
    }

    public static void printFirstElement() {
        try {
            if (arrayDeque.size() == 0) throw new EmptyCollectionException();
            System.out.println("First organization: " + arrayDeque.peekFirst());
        } catch (EmptyCollectionException exception) {
            System.out.println("Collection is empty!");
        }
    }

    public static void printElementWithMinimalCreationDate(){
        try {
            if (arrayDeque.size() == 0) throw new EmptyCollectionException();

            Organization minCreationDateOrganization = arrayDeque.getFirst();
            for (Organization organization : arrayDeque) {
                if (organization.getCreationDate().compareTo(minCreationDateOrganization.getCreationDate()) < 0) {
                    minCreationDateOrganization = organization;
                }
            }
            System.out.println("Element with minimal creation date: " + minCreationDateOrganization);
        } catch (EmptyCollectionException exception) {
            System.out.println("Collection is empty!");
        }
    }

    public static void printUniquePostalAddress() {
        try {
            if (arrayDeque.size() == 0) throw new EmptyCollectionException();

            Set<String> uniquePostalAddresses = new HashSet<>();
            for (Organization organization : arrayDeque) {
                uniquePostalAddresses.add(organization.getPostalAddress().getStreet());
            }
            System.out.println("Unique postal addresses: ");
            for (String postalAddress : uniquePostalAddresses) {
                System.out.println(postalAddress);
            }
        } catch (EmptyCollectionException exception) {
            System.out.println("Collection is empty");
        }
    }
    public static void removeLowerElement(Organization organization) {
        try {
            if (arrayDeque.size() == 0) throw new EmptyCollectionException();

            for (Organization organization1 : arrayDeque) {
                if (organization1.compareTo(organization) < 0) {
                    arrayDeque.remove(organization1);
                }
            }
        } catch (EmptyCollectionException exception) {
            System.out.println("Collection is empty!");
        }
    }

    public static void getCollectionFromFile(String fileName) {
        CSVProcess.loadCollection(fileName);
    }

    public static void saveCollectionToFile() {
        CSVProcess.writeCollection();
    }
}

