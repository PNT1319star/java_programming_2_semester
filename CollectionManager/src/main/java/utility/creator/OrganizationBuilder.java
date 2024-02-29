package utility.creator;

import data.Address;
import data.Coordinates;
import data.Organization;
import data.OrganizationType;
import exceptions.NotDeclaredValueException;
import exceptions.NotInDeclaredLimitsException;
import exceptions.WrongAmountOfElementsException;
import exceptions.WrongInputInScriptException;


import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Utility class for getting all information about the organization from the user
 */
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
     * Sets the Scanner object used to read user input.
     *
     * @param scanner the Scanner object to set.
     */
    public void setUserScanner(Scanner scanner) {
        this.userScanner = scanner;
    }

    /**
     * Gets the Scanner object used to read user input.
     *
     * @return the Scanner object.
     */
    public Scanner getUserScanner() {
        return userScanner;
    }

    /**
     * Sets the file mode.
     * This mode is used when reading input from a file.
     */
    public void setFileMode() {
        this.fileMode = true;
    }

    /**
     * Sets the user mode.
     * This mode is used when reading input from the user.
     */
    public void setUserMode() {
        this.fileMode = false;
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
                System.out.println("Enter organization's name: ");
                System.out.print(">");
                name = userScanner.nextLine().trim();
                if(fileMode) System.out.println(name);
                if (name.equals("")) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException exception) {
                System.err.println("The 'name' value cannot be empty! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                System.err.println("The name isn't recognized!");
            } catch (IllegalStateException exception) {
                System.err.println("Unexpected error!");
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
                System.out.println("Enter x coordinate: ");
                System.out.print(">");
                strX = userScanner.nextLine().trim();
                if (fileMode) System.out.println(strX);
                x = Long.parseLong(strX);
                if(strX.isEmpty()) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException exception) {
                System.out.println("Coordinate x cannot be empty! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NumberFormatException exception) {
                System.out.println("Coordinate x must be a number! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                System.err.println("Unexpected error!");
                System.exit(0);
            } catch (NoSuchElementException exception) {
                System.err.println("Coordinate x isn't recodnized!");
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
                System.out.println("Enter y coordinate: ");
                System.out.print(">");
                strY = userScanner.nextLine().trim();
                if (fileMode) System.out.println(strY);
                y = Long.parseLong(strY);
                break;
            } catch (NumberFormatException exception) {
                System.err.println("Coordinate y must be a number");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                System.err.println("Coordinate y isn't  recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                System.err.println("Unexpected error!");
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
                System.out.println("Enter organization's full name: ");
                System.out.print(">");
                fullName = userScanner.nextLine().trim();
                if (fileMode) System.out.println(fullName);
                if (fullName.isEmpty()) throw new NotDeclaredValueException();
                if (fullName.length() > MAX_LENGTH) throw new NotInDeclaredLimitsException();
                break;
            } catch (NotDeclaredValueException exception) {
                System.err.println("Full name cannot be empty! Try again!");
            } catch (NotInDeclaredLimitsException exception) {
                System.err.println("Length of full name cannot exceed 2048! Try again!");
            } catch (NoSuchElementException exception) {
                System.err.println("Full name isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                System.err.println("Unexpected error!");
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
                System.out.println("Enter the organization's address: ");
                System.out.print(">");
                address = userScanner.nextLine().trim();
                if (fileMode) System.out.println(address);
                break;
            }
        } catch (NoSuchElementException exception) {
            System.err.println("Address isn't recognized!");
            if (fileMode) throw new WrongInputInScriptException();
        } catch (NullPointerException | IllegalStateException exception) {
            System.err.println("Unexpected Error!");
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
                System.out.println("Enter the number of employees of organization: ");
                System.out.print(">");
                strCount = userScanner.nextLine().trim();
                if (fileMode) System.out.println(strCount);
                employeesCount = Integer.parseInt(strCount);
                if (employeesCount <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NotInDeclaredLimitsException exception) {
                System.err.println("The number of employees must be greater than 0! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NumberFormatException exception) {
                System.err.println("The number of employees must be a number! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                System.err.println("Employees Count isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                System.err.println("Unexpected error!");
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
                System.out.println("Enter the sale revenue of organization: ");
                System.out.print(">");
                strAnnualTurnover = userScanner.nextLine().trim();
                if (fileMode) throw new WrongInputInScriptException();
                annualTurnover = Float.parseFloat(strAnnualTurnover);
                if (annualTurnover <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NotInDeclaredLimitsException exception) {
                System.err.println("The sale revenue of the organization must be greater than 0! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NumberFormatException exception) {
                System.err.println("The sale revenue of the organization must be a number! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                System.err.println("Annual turnover isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                System.err.println("Unexpected error!");
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
                System.out.println("Organization Type List: " + OrganizationType.nameList());
                System.out.println("Enter the organization type: ");
                System.out.print(">");
                strOrgType = userScanner.nextLine().trim();
                if (fileMode) System.out.println(strOrgType);
                organizationType = OrganizationType.valueOf(strOrgType.toUpperCase());
                break;
            } catch (IllegalArgumentException exception) {
                System.err.println("This type does not exist! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (IllegalStateException exception) {
                System.err.println("Unexpected error!");
                System.exit(0);
            } catch (NoSuchElementException exception) {
                System.err.println("Type isn't recognized!");
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