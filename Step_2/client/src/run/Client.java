package run;

import utility.ConsoleManager;
import utility.ConsolePrinter;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try {
            ConsoleManager consoleManager = new ConsoleManager();
            consoleManager.interactive("localhost", "2223");
//            consoleManager.interactive(args[0], args[1]);
        } catch (ArrayIndexOutOfBoundsException exception) {
            ConsolePrinter.printError("The arguments were entered incorrectly!");
        }
    }
}
