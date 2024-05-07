package utility;

import data.Address;
import data.Coordinates;
import data.Organization;
import data.OrganizationType;
import exceptions.WrongInputInScriptException;
import interaction.OrganizationRaw;

import java.util.Scanner;

/**
 * The OrganizationCreator class is responsible for creating Organization objects based on user input.
 */
public class OrganizationCreator {
    private static String name;
    private static Long x;
    private static long y;
    private static String fullName;
    private static String address;
    private static Integer employeesCount;
    private static Float annualTurnover;
    private static OrganizationType type;

    /**
     * Creates an Organization object based on user input.
     *
     * @param scanner the Scanner object used to read user input.
     * @return the created Organization object.
     */
    public static OrganizationRaw organizationCreator(Scanner scanner) {
        try {
            OrganizationBuilder organizationBuilder = new OrganizationBuilder(scanner);
            name = organizationBuilder.nameAsker();
            x = organizationBuilder.xAsker();
            y = organizationBuilder.yAsker();
            fullName = organizationBuilder.fullNameAsker();
            address = organizationBuilder.addressAsker();
            employeesCount = organizationBuilder.employeesCountAsker();
            annualTurnover = organizationBuilder.annualTurnoverAsker();
            type = organizationBuilder.organizationTypeAsker();
        }catch (WrongInputInScriptException exception) {
            ConsolePrinter.printError("Wrong input!");
        }

        return new OrganizationRaw(name,new Coordinates(x, y), annualTurnover, fullName,
                employeesCount, type, new Address(address));
    }
}
