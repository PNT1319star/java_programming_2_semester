package commands;

import data.Organization;
import exceptions.EmptyCollectionException;
import utility.CollectionManager;
import utility.creator.OrganizationCreator;
import utility.mode.FileScriptMode;

import java.util.List;
import java.util.Scanner;

/**
 * Utility class for managing commands in the program.
 */

public class Receiver {
    private final Invoker invoker;

    public Receiver (Invoker invoker) {
        this.invoker = invoker;
    }


    /**
     * Execute the help command
     */
    public void help() {
        invoker.getCommands().forEach((name, command) -> command.getCommandInformation());
        System.out.println("The 'help' command has been executed successfully");
    }
    /**
     * Execute the info command
     */
    public void info() {
        CollectionManager.information();
        System.out.println("The 'info' command has been executed successfully");
    }

    /**
     * Execute the show command
     */
    public void show() {
        CollectionManager.fullInformation();
        System.out.println("The 'show' command has been executed successfully");
    }

    /**
     * Execute the add command
     */
    public void add(Scanner scanner) {
        Organization organization = OrganizationCreator.organizationCreator(scanner);
        CollectionManager.getCommandOrders().put("remove",organization);
        CollectionManager.addOrganization(organization);
        System.out.println("The 'add' command has been executed successfully");
    }

    /**
     * Execute the add_if_max command
     */
    public void addIfMax(Scanner scanner) {
        Organization organization = OrganizationCreator.organizationCreator(scanner);
        CollectionManager.addIfMax(organization);
        if (CollectionManager.getAddIfMaxFlag()) CollectionManager.getCommandOrders().put("remove",organization);
        System.out.println("The 'add_if_max' command has been executed successfully");
    }

    /**
     * Execute the update command
     */
    public void update(String sID, Scanner scanner) {
        Integer ID;
        try {
            if (CollectionManager.getCollection().size() == 0) throw new EmptyCollectionException();
            ID = Integer.parseInt(sID);
            if (CollectionManager.idExistence(ID)) {
                Organization oldOrganization = CollectionManager.getOrganizationByID(ID);
                Organization newOrganization = OrganizationCreator.organizationCreator(scanner);
                CollectionManager.getCommandOrders().put("remove",newOrganization);
                CollectionManager.getCommandOrders().put("add",oldOrganization);
                CollectionManager.updateElement(newOrganization, ID);
                System.out.println("The 'update' command has been executed successfully");
                System.out.println("Organization is updated!");
            } else {
                System.out.println("The organization with this ID does not exist in the collection!");
            }
        } catch (NumberFormatException exception) {
            System.out.println("The ID is not correct!");
        } catch (EmptyCollectionException exception) {
            System.out.println("The collection is empty");
        }
    }

    /**
     * Execute the remove_by_id command
     */
    public void removeById(String sID) {
        Integer ID;
        try {
            ID = Integer.parseInt(sID);
            if (CollectionManager.idExistence(ID)) {
                Organization organization = CollectionManager.getOrganizationByID(ID);
                CollectionManager.getCommandOrders().put("add",organization);
                CollectionManager.removeElement(ID);
                System.out.println("The 'remove_by_id' command has been executed successfully");
                System.out.println("The organization with this ID has been deleted!");
            } else {
                System.out.println("The organization with this ID does not exist!");
            }
        } catch (NumberFormatException exception) {
            System.out.println("Invalid command argument!");
        }
    }

    /**
     * Execute the clear command
     */
    public void clear() {
        List<Organization> clearList = CollectionManager.getClearList();
        Organization[] clearArray = clearList.toArray(new Organization[clearList.size()]);
        for (int i = clearList.size(); i > 0; i--) {
            CollectionManager.getCommandOrders().put("add", clearArray[i]);
        }
        CollectionManager.clearCollection();
        System.out.println("The 'clear' command has been executed successfully");
        System.out.println("The collection is cleared!");
    }


    /**
     * Execute the execute_script command
     */
    public void executeScript(String path) {
        FileScriptMode.scriptMode(path);
        System.out.println("The 'execute_script' command has been executed successfully");
    }

    /**
     * Execute the exit command
     */
    public void exit() {
        System.out.println("The 'exit' command has been executed successfully");
        System.out.println("The program closed!");
        System.exit(0);
    }

    /**
     * Execute the head command
     */
    public void head() {
        System.out.println("The 'head' command has been executed successfully");
        CollectionManager.printFirstElement();
    }

    /**
     * Execute the save command
     */
    public void save() {
        System.out.println("The 'save' command has been executed successfully");
        System.out.println("The collection has been saved in the file!");
        CollectionManager.saveCollectionToFile();
    }
    /**
     * Execute the remove_lower command
     */
    public void removeLower(Scanner scanner) {
        Organization organization = OrganizationCreator.organizationCreator(scanner);
        CollectionManager.removeLowerElement(organization);
        List<Organization> removeLowerArrayList = CollectionManager.getRemoveLowerList();
        Organization[] removeLowerArray = removeLowerArrayList.toArray(new Organization[removeLowerArrayList.size()]);
        if (CollectionManager.getRemoveLowerFlag()) {
            for (int i = CollectionManager.getRemoveLowerList().size(); i > 0; i--) {
                CollectionManager.getCommandOrders().put("add",removeLowerArray[i]);
            }
        }
        System.out.println("The 'remove_lower' command has been executed successfully");
    }

    /**
     * Execute the min_by_creation_date command
     */
    public void minByCreationDate() {
        CollectionManager.printElementWithMinimalCreationDate();
        System.out.println("The 'min_by_creation_date' command has been executed successfully");
    }

    /**
     * Execute the filter_starts_with_full_name command
     */
    public void filterStartsWithFullName(String fullName) {
        CollectionManager.filterStartsWithFullName(fullName);
        System.out.println("The 'filter_starts_with_full_name' command has been executed successfully");
    }

    /**
     * Execute the print_unique_postal_address command
     */
    public void printUniquePostalAddress() {
        CollectionManager.printUniquePostalAddress();
        System.out.println("The 'print_unique_postal_address' command has been executed successfully");
    }

    public void rollBack(String strSteps) {
        try {
            int steps = Integer.parseInt(strSteps);
            CollectionManager.rollBack(steps);
            System.out.println("The 'roll_back' command has been executed successfully");
        } catch (EmptyCollectionException exception) {
            System.err.println("Can not roll back! You have not used any command that affects the collection!");
        }
    }
}
