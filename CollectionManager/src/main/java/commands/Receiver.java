package commands;

import exceptions.EmptyCollectionException;
import utility.CollectionManager;
import utility.Console;
import utility.creator.OrganizationBuilder;
import utility.creator.OrganizationCreator;
import utility.mode.FileScriptMode;

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
        CollectionManager.addOrganization(OrganizationCreator.organizationCreator(scanner));
        System.out.println("The 'add' command has been executed successfully");
    }

    /**
     * Execute the add_if_max command
     */
    public void addIfMax(Scanner scanner) {
        CollectionManager.addIfMax(OrganizationCreator.organizationCreator(scanner));
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
                CollectionManager.updateElement(OrganizationCreator.organizationCreator(scanner), ID);
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
        CollectionManager.removeLowerElement(OrganizationCreator.organizationCreator(scanner));
        System.out.println("The 'head' command has been executed successfully");
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
        System.out.println("The 'print_unique_postal_address' command has been executed successfully");
        CollectionManager.printUniquePostalAddress();
    }

    public void rollBack(Scanner scanner) {

    }
}
