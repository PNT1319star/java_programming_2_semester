package server;

import utility.ConsolePrinter;
import utility.csv.CSVProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SaveCommandListener {
    public void startListening() {
        Thread saveCommandListener = new Thread (() -> {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                while (true) {
                    String line = bufferedReader.readLine();
                    if(line != null && line.equalsIgnoreCase("save")) {
                        CSVProcess.writeCollection();
                        ConsolePrinter.printResult("Collection has been saved in the file!");
                    }
                }
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        });
        saveCommandListener.setDaemon(true);
        saveCommandListener.start();
    }
}
