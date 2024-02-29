package commands;

import data.Organization;
import exceptions.EmptyCollectionException;
import utility.CollectionManager;
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
        System.out.println("\u001B[33mThe 'help' command has been executed successfully\u001B[0m");
    }

    /**
     * Execute the info command
     */
    public void info() {
        CollectionManager.information();
        System.out.println("\u001B[33mThe 'info' command has been executed successfully\u001B[0m");
    }

    /**
     * Execute the show command
     */
    public void show() {
        CollectionManager.fullInformation();
        System.out.println("\u001B[33mThe 'show' command has been executed successfully\u001B[0m");
    }

    /**
     * Execute the add command
     */
    public void add(Scanner scanner) {
        Organization organization = OrganizationCreator.organizationCreator(scanner);
        CollectionManager.addOrganization(organization);
        System.out.println("\u001B[33mThe 'add' command has been executed successfully\u001B[0m");
        CollectionManager.historyCommandList.push("add");
        CollectionManager.organizationStack.push(organization);
    }

    /**
     * Execute the add_if_max command
     */
    public void addIfMax(Scanner scanner) {
        Organization organization = OrganizationCreator.organizationCreator(scanner);
        CollectionManager.addIfMax(organization);
        if (CollectionManager.getAddIfMaxFlag()) {
            CollectionManager.historyCommandList.push("add");
            CollectionManager.organizationStack.push(organization);
        }
        System.out.println("\u001B[33mThe 'add_if_max' command has been executed successfully\u001B[0m");
    }

    /**
     * Execute the update command
     */
    public void update(String sID, Scanner scanner) {
        int ID;
        try {
            if (CollectionManager.getCollection().size() == 0) throw new EmptyCollectionException();
            ID = Integer.parseInt(sID);
            if (CollectionManager.idExistence(ID)) {
                Organization tempOrganization1 = CollectionManager.getOrganizationByID(ID);
                Organization oldOrganization = tempOrganization1.clone();
                CollectionManager.historyCommandList.push("update");
                CollectionManager.organizationStack.push(oldOrganization);
                Organization newOrganization = OrganizationCreator.organizationCreator(scanner);
                CollectionManager.updateElement(newOrganization, ID);
                Organization tempOrganization = CollectionManager.getOrganizationByID(ID);
                CollectionManager.organizationStack.push(tempOrganization);
                System.out.println("\u001B[33mThe 'update' command has been executed successfully\u001B[0m");
                System.out.println("\u001B[33mOrganization id \u001B[0m" + ID+ " \u001B[33mis updated!\u001B[0m");
            } else {
                System.err.println("The organization with this ID does not exist in the collection!");
            }
        } catch (NumberFormatException exception) {
            System.err.println("The ID is not correct!");
        } catch (EmptyCollectionException exception) {
            System.err.println("The collection is empty");
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
                CollectionManager.historyCommandList.push("remove");
                CollectionManager.organizationStack.push(organization);
                CollectionManager.removeElement(ID);
                System.out.println("\u001B[33mThe 'remove_by_id' command has been executed successfully\u001B[0m");
                System.out.println("\u001B[33mThe organization with this ID has been deleted!\u001B[0m");
            } else {
                System.err.println("The organization with this ID does not exist!");
            }
        } catch (NumberFormatException exception) {
            System.err.println("Invalid command argument!");
        }
    }

    /**
     * Execute the clear command
     */
    public void clear() {
        CollectionManager.historyCommandList.push("clear");
        CollectionManager.clearCollection();
        System.out.println("\u001B[33mThe 'clear' command has been executed successfully\u001B[0m");
        System.out.println("\u001B[33mThe collection is cleared!\u001B[0m");
    }

    /**
     * Execute the execute_script command
     */
    public void executeScript(String path) {
        FileScriptMode.scriptMode(path);
        System.out.println("\u001B[33mThe 'execute_script' command has been executed successfully\u001B[0m");
    }

    /**
     * Execute the exit command
     */
    public void exit() {
        System.out.println("\u001B[33mThe 'exit' command has been executed successfully\u001B[0m");
        System.out.println("\u001B[33mProgram has been closed!\u001B[0m");
        System.exit(0);
    }

    /**
     * Execute the head command
     */
    public void head() {
        System.out.println("\u001B[33mThe 'head' command has been executed successfully\u001B[0m");
        CollectionManager.printFirstElement();
    }

    /**
     * Execute the save command
     */
    public void save() {
        System.out.println("\u001B[33mThe 'save' command has been executed successfully\u001B[0m");
        System.out.println("\u001B[33mThe collection has been saved in the file!\u001B[0m");
        CollectionManager.saveCollectionToFile();
    }
    /**
     * Execute the remove_lower command
     */
    public void removeLower(Scanner scanner) {
        Organization organization = OrganizationCreator.organizationCreator(scanner);
        CollectionManager.removeLowerElement(organization);
        CollectionManager.historyCommandList.push("remove_lower");
        System.out.println("\u001B[33mThe 'remove_lower' command has been executed successfully\u001B[0m");
    }

    /**
     * Execute the min_by_creation_date command
     */
    public void minByCreationDate() {
        CollectionManager.printElementWithMinimalCreationDate();
        System.out.println("\u001B[33mThe 'min_by_creation_date' command has been executed successfully\u001B[0m");
    }

    /**
     * Execute the filter_starts_with_full_name command
     */
    public void filterStartsWithFullName(String fullName) {
        CollectionManager.filterStartsWithFullName(fullName);
        System.out.println("\u001B[33mThe 'filter_starts_with_full_name' command has been executed successfully\u001B[0m");
    }

    /**
     * Execute the print_unique_postal_address command
     */
    public void printUniquePostalAddress() {
        CollectionManager.printUniquePostalAddress();
        System.out.println("\u001B[33mThe 'print_unique_postal_address' command has been executed successfully\u001B[0m");
    }

    /**
     * Execute the roll_back command
     */
    public void rollBack(String strSteps) {
        try {
            int steps = Integer.parseInt(strSteps);
            CollectionManager.rollBack(steps);
            System.out.println("\u001B[33mThe 'roll_back' command has been executed successfully\u001B[0m");
        } catch (EmptyCollectionException exception) {
            System.err.println("Can not roll back! You have not used any command that affects the collection!");
        }
    }
}
