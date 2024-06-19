package org.csjchoisoojong.utility;

import org.csjchoisoojong.data.OrganizationType;
import org.csjchoisoojong.exceptions.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class OrganizationBuilder {

    private Scanner userScanner;
    private boolean fileMode;

    /**
     * Creates a new instance of the OrganizationBuilder class.
     *
     * @param scanner the Scanner object used to read user input.
     */
    public OrganizationBuilder(Scanner scanner) {
        this.userScanner = scanner;
    }

    /**
     * Asks the user for the organization's name.
     *
     * @return the organization's name.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */

    public String nameAsker() throws WrongInputInScriptException {
        String name;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter organization's name: ");
                ConsolePrinter.printInput(">");
                name = userScanner.nextLine().trim();
                if(fileMode) ConsolePrinter.printInformation(name);
                if (name.isEmpty()) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException exception) {
                ConsolePrinter.printError("The 'name' value cannot be empty! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("The name isn't recognized!");
            } catch (IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return name;
    }

    /**
     * Asks the user for the x coordinate.
     *
     * @return the x coordinate.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public Long xAsker() throws WrongInputInScriptException {
        String strX;
        long x;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter x coordinate: ");
                ConsolePrinter.printInput(">");
                strX = userScanner.nextLine().trim();
                if (fileMode) ConsolePrinter.printInformation(strX);
                x = Long.parseLong(strX);
                if(strX.isEmpty()) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException exception) {
                ConsolePrinter.printError("Coordinate x cannot be empty! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NumberFormatException exception) {
                ConsolePrinter.printError("Coordinate x must be a number! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("Coordinate x isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            }
        }
        return x;
    }

    /**
     * Asks the user for the y coordinate.
     *
     * @return the y coordinate.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public long yAsker() throws WrongInputInScriptException {
        String strY;
        long y;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter y coordinate: ");
                ConsolePrinter.printInput(">");
                strY = userScanner.nextLine().trim();
                if (fileMode) ConsolePrinter.printInformation(strY);
                y = Long.parseLong(strY);
                break;
            } catch (NumberFormatException exception) {
                ConsolePrinter.printError("Coordinate y must be a number");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("Coordinate y isn't  recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * Asks the user for the organization's full name.
     *
     * @return the organization's full name.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public String fullNameAsker() throws WrongInputInScriptException {
        final int MAX_LENGTH = 2048;
        String fullName;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter organization's full name: ");
                ConsolePrinter.printInput(">");
                fullName = userScanner.nextLine().trim();
                if (fileMode) ConsolePrinter.printInformation(fullName);
                if (fullName.isEmpty()) throw new NotDeclaredValueException();
                if (fullName.length() > MAX_LENGTH) throw new NotInDeclaredLimitsException();
                break;
            } catch (NotDeclaredValueException exception) {
                ConsolePrinter.printError("Full name cannot be empty! Try again!");
            } catch (NotInDeclaredLimitsException exception) {
                ConsolePrinter.printError("Length of full name cannot exceed 2048! Try again!");
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("Full name isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return fullName;
    }

    /**
     * Asks the user for the organization's address.
     *
     * @return the organization's address.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public String addressAsker() throws WrongInputInScriptException {
        String address = null;
        try {
            while (true) {
                ConsolePrinter.printInformation("Enter the organization's address: ");
                ConsolePrinter.printInput(">");
                address = userScanner.nextLine().trim();
                if (fileMode) ConsolePrinter.printInformation(address);
                break;
            }
        } catch (NoSuchElementException exception) {
            ConsolePrinter.printError("Address isn't recognized!");
            if (fileMode) throw new WrongInputInScriptException();
        } catch (NullPointerException | IllegalStateException exception) {
            ConsolePrinter.printError("Unexpected Error!");
            System.exit(0);
        }
        return address;
    }

    /**
     * Asks the user for the number of employees of the organization.
     *
     * @return the number of employees.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public Integer employeesCountAsker() throws WrongInputInScriptException {
        String strCount;
        int employeesCount;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter the number of employees of organization: ");
                ConsolePrinter.printInput(">");
                strCount = userScanner.nextLine().trim();
                if (fileMode) ConsolePrinter.printInformation(strCount);
                employeesCount = Integer.parseInt(strCount);
                if (employeesCount <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NotInDeclaredLimitsException exception) {
                ConsolePrinter.printError("The number of employees must be greater than 0! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NumberFormatException exception) {
                ConsolePrinter.printError("The number of employees must be a number! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("Employees Count isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return employeesCount;
    }

    /**
     * Asks the user for the annual turnover of the organization.
     *
     * @return the annual turnover.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public Float annualTurnoverAsker() throws WrongInputInScriptException {
        String strAnnualTurnover;
        float annualTurnover;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter the sale revenue of organization: ");
                ConsolePrinter.printInput(">");
                strAnnualTurnover = userScanner.nextLine().trim();
                if (fileMode) throw new WrongInputInScriptException();
                annualTurnover = Float.parseFloat(strAnnualTurnover);
                if (annualTurnover <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NotInDeclaredLimitsException exception) {
                ConsolePrinter.printError("The sale revenue of the organization must be greater than 0! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NumberFormatException exception) {
                ConsolePrinter.printError("The sale revenue of the organization must be a number! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("Annual turnover isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return annualTurnover;
    }

    /**
     * Asks the user for the organization type.
     *
     * @return the organization type.
     * @throws WrongInputInScriptException if an error occurs while reading input in script mode.
     */
    public OrganizationType organizationTypeAsker() throws WrongInputInScriptException {
        String strOrgType;
        OrganizationType organizationType;
        while (true) {
            try {
                ConsolePrinter.printInformation("Organization Type List: " + OrganizationType.nameList());
                ConsolePrinter.printInformation("Enter the organization type: ");
                ConsolePrinter.printInput(">");
                strOrgType = userScanner.nextLine().trim();
                if (fileMode) ConsolePrinter.printInformation(strOrgType);
                organizationType = OrganizationType.valueOf(strOrgType.toUpperCase());
                break;
            } catch (IllegalArgumentException exception) {
                ConsolePrinter.printError("This type does not exist! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("Type isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            }
        }
        return organizationType;
    }

    @Override
    public String toString() {
        return "OrganizationBuilder (helper class for user requests)";
    }
}

