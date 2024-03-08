package utility;

import data.Organization;
import utility.csv.CSVProcess;

import java.time.ZonedDateTime;
import java.util.*;

import exceptions.EmptyCollectionException;

public class CollectionManager {
    private static boolean addIfMaxFlag;
    public static Stack<String> historyCommandList = new Stack<>() ;
    private static List<Organization> clearList = new ArrayList<>();
    private static List<Organization> removeLowerList = new ArrayList<>();
    public static Stack<Organization> organizationStack = new Stack<>();

    private static ArrayDeque<Organization> arrayDeque;
    private static ZonedDateTime initializationDate;

    /**
     * Initializes the collection if it is null.
     */
    public static void initializationCollection() {
            arrayDeque = new ArrayDeque<>();
            initializationDate = ZonedDateTime.now();
    }

    /**
     * Retrieves the collection of organizations.
     *
     * @return The collection of organizations.
     */
    public static ArrayDeque<Organization> getCollection() {
        return arrayDeque;
    }

    /**
     * Retrieves an organization from the collection based on its ID.
     *
     * @param ID The ID of the organization.
     * @return The organization with the specified ID, or null if not found.
     */
    public static Organization getOrganizationByID (int ID) {
        for(Organization organization : arrayDeque) {
            int id = organization.getId().intValue();
            if (id == ID) {
                return organization;
            }
        }
        return null;
    }

    /**
     * Checks if an organization with the specified ID exists in the collection.
     *
     * @param ID The ID of the organization.
     * @return true if an organization with the specified ID exists, false otherwise.
     */
    public static boolean idExistence(int ID) {
        for (Organization organization : arrayDeque) {
            if (organization.getId().intValue() == ID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints information about the collection, such as its type, initialization date, and number of elements.
     */
    public static void information() {
        ConsolePrinter.printInformation("Type of collection: " + arrayDeque.getClass().getName() + ".\nInitialization Date: " + initializationDate
                + ".\nNumber of elements: " + arrayDeque.size() + ".");

    }

    /**
     * Prints detailed information about all organizations in the collection.
     * Throws an EmptyCollectionException if the collection is empty.
     */
    public static void fullInformation() {
        try{
            if (arrayDeque.isEmpty()) throw new EmptyCollectionException();
            for (Organization organization : arrayDeque) {
                ConsolePrinter.printInformation(organization.toString());
            }
        } catch (EmptyCollectionException exception) {
            ConsolePrinter.printError("The collection is empty.");
        }

    }

    /**
     * Adds an organization to the collection.
     *
     * @param organization The organization to be added.
     */
    public static void addOrganization(Organization organization) {
        arrayDeque.add(organization);
    }

    /**
     * Adds an organization to the collection if it has the maximum value based on the natural ordering.
     * If the collection is empty, the organization is added automatically.
     *
     * @param organization The organization to be added.
     */
    public static void addIfMax(Organization organization) {
        if (arrayDeque.size() == 0) {
            addOrganization(organization);
        } else {
            addIfMaxFlag = true;
            for (Organization organization1 : arrayDeque) {
                if (organization1.compareTo(organization) > 0) {
                    addIfMaxFlag = false;
                    break;
                }
            }
            if (addIfMaxFlag) addOrganization(organization);
        }
    }
    /**
     * Updates an element in the collection with the specified ID.
     * The attributes of the organization are updated with the attributes of the newOrganization.
     *
     * @param newOrganization The updated organization.
     * @param ID              The ID of the organization to be updated.
     */
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

    /**
     * Clears the collection by removing all organizations.
     * The removed organizations are stored in the clearList.
     */
    public static void clearCollection() {
        for (Organization organization : arrayDeque) {
            clearList.add(organization);
        };
        arrayDeque.clear();
    }

    /**
     * Removes an organization from the collection based on its ID.
     *
     * @param ID The ID of the organization to be removed.
     */
    public  static void removeElement(Integer ID) {
        for (Organization organization : arrayDeque) {
            if (organization.getId().equals(ID)) {
                arrayDeque.remove(organization);
                break;
            }
        }
    }

    /**
     * Filters and prints organizations in the queue whose full name starts with a given string.
     *
     * @param fullName The string to check the full name of organizations.
     */
    public static void filterStartsWithFullName(String fullName) {
        arrayDeque.forEach(organization -> {
            if (organization.getFullName().startsWith(fullName)) {
                ConsolePrinter.printInformation(organization);
            }
        });
    }

    /**
     * Prints the first element in the queue.
     * If the queue is empty, displays the message "Collection is empty!".
     */
    public static void printFirstElement() {
        try {
            if (arrayDeque.size() == 0) throw new EmptyCollectionException();
            ConsolePrinter.printInformation("First organization: " + arrayDeque.peekFirst());
        } catch (EmptyCollectionException exception) {
            ConsolePrinter.printError("Collection is empty!");
        }
    }

    /**
     * Prints the element with the minimal creation date in the queue.
     * If the queue is empty, displays the message "Collection is empty!".
     */
    public static void printElementWithMinimalCreationDate(){
        try {
            if (arrayDeque.size() == 0) throw new EmptyCollectionException();

            Organization minCreationDateOrganization = arrayDeque.getFirst();
            for (Organization organization : arrayDeque) {
                if (organization.getCreationDate().compareTo(minCreationDateOrganization.getCreationDate()) < 0) {
                    minCreationDateOrganization = organization;
                }
            }
            ConsolePrinter.printInformation("Element with minimal creation date: " + minCreationDateOrganization);
        } catch (EmptyCollectionException exception) {
            ConsolePrinter.printError("Collection is empty!");
        }
    }

    /**
     * Prints unique postal addresses from organizations in the queue.
     * If the queue is empty, displays the message "Collection is empty!".
     */
    public static void printUniquePostalAddress() {
        try {
            if (arrayDeque.size() == 0) throw new EmptyCollectionException();

            Set<String> uniquePostalAddresses = new HashSet<>();
            for (Organization organization : arrayDeque) {
                uniquePostalAddresses.add(organization.getPostalAddress().getStreet());
            }
            ConsolePrinter.printInformation("Unique postal addresses: ");
            for (String postalAddress : uniquePostalAddresses) {
                ConsolePrinter.printInformation(postalAddress);
            }
        } catch (EmptyCollectionException exception) {
            ConsolePrinter.printError("Collection is empty");
        }
    }

    /**
     * Removes organizations in the queue that are lower than the given organization.
     * If the queue is empty, displays the message "Collection is empty!".
     *
     * @param organization The organization to compare with.
     */
    public static void removeLowerElement(Organization organization) {
        try {
            if (arrayDeque.size() == 0) throw new EmptyCollectionException();

            for (Organization organization1 : arrayDeque) {
                if (organization1.compareTo(organization) < 0) {
                    removeLowerList.add(organization1);
                    arrayDeque.remove(organization1);
                }
            }
        } catch (EmptyCollectionException exception) {
            ConsolePrinter.printError("Collection is empty!");
        }
    }

    /**
     * Rolls back the specified number of steps by undoing the previous commands.
     * Throws an EmptyCollectionException if the commandOrders map is empty.
     *
     * @param steps The number of steps to roll back.
     * @throws EmptyCollectionException If the commandOrders map is empty.
     */
    public static void rollBack(int steps) throws EmptyCollectionException{
        if (historyCommandList.isEmpty()) throw new EmptyCollectionException();
        for (int i = 0; i < steps; i++){
            String command = historyCommandList.pop();
            switch (command) {
                case "add" :
                    CollectionManager.removeElement(organizationStack.pop().getId());
                    break;
                case "remove" :
                    CollectionManager.addOrganization(organizationStack.pop());
                    break;
                case "remove_lower" :
                    Organization[] removeLowerArray = removeLowerList.toArray(new Organization[removeLowerList.size()]);
                    for (int j = 0; j < removeLowerList.size(); j++) {
                        CollectionManager.addOrganization(removeLowerArray[j]);
                    }
                    removeLowerList.clear();
                    break;
                case "update":
                    Organization newOrganization = organizationStack.pop();
                    CollectionManager.removeElement(newOrganization.getId());
                    Organization oldOrganization = organizationStack.pop();
                    CollectionManager.addOrganization(oldOrganization);
                    break;
                case "clear" :
                    Organization[] clearArray = clearList.toArray(new Organization[clearList.size()]);
                    for (int j = 0; j < clearList.size(); j++) {
                        CollectionManager.addOrganization(clearArray[j]);
                    }
                    clearList.clear();
                    break;

            }
        }
    }

    /**
     * Loads the collection from a file with the specified name.
     *
     * @param fileName The name of the file to load the collection from.
     */
    public static void getCollectionFromFile(String fileName) {
        arrayDeque = CSVProcess.loadCollection(fileName);
    }

    /**
     * Saves the collection to a file.
     */
    public static void saveCollectionToFile() {
        CSVProcess.writeCollection();
    }

    /**
     * Returns the addIfMaxFlag value.
     *
     * @return The addIfMaxFlag value.
     */
    public static boolean getAddIfMaxFlag() {
        return addIfMaxFlag;
    }

}

