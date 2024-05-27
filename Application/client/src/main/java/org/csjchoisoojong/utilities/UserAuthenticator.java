package org.csjchoisoojong.utilities;


import org.csjchoisoojong.exceptions.EmptyException;
import org.csjchoisoojong.exceptions.NotInDeclaredLimitsException;
import org.csjchoisoojong.utility.ConsolePrinter;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserAuthenticator {
    private final Scanner userScanner;

    public UserAuthenticator(Scanner scanner) {
        this.userScanner = scanner;
    }

    public String askLogin() {
        String login;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter your login: ");
                ConsolePrinter.printInput(">");
                login = userScanner.nextLine().trim();
                if (login.isEmpty()) throw new EmptyException();
                break;
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("This login does not exist!");
            } catch (EmptyException exception) {
                ConsolePrinter.printError("The login cannot be empty!");
            } catch (IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return login;
    }

    public String askPassWord() {
        String password;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter your password: ");
                ConsolePrinter.printInput(">");
                password = userScanner.nextLine().trim();
                break;
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("Wrong login or password!");
            } catch (IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return password;
    }

    public boolean ask(String question) {
        String fullQuestion = question + " (yes / no)";
        String answer;
        while (true) {
            try {
                ConsolePrinter.printInformation(fullQuestion);
                ConsolePrinter.printInput(">");
                answer = userScanner.nextLine().trim();
                if (!answer.equals("yes") && !answer.equals("no")) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("The answer is not recognized!");
            } catch (NotInDeclaredLimitsException exception) {
                ConsolePrinter.printError("The answer must be represented by 'yes' or 'no'!");
            } catch (IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return answer.equals("yes");
    }
}
