package run;

import utility.ConsoleManager;
import utility.ConsolePrinter;

import java.io.IOException;

public class Client {
    public static void main(String[] args) {
        try {
            ConsoleManager.interactive("localhost", "2223");
//            consoleManager.interactive(args[0], args[1]);
        } catch (ArrayIndexOutOfBoundsException exception) {
            ConsolePrinter.printError("The arguments were entered incorrectly!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
