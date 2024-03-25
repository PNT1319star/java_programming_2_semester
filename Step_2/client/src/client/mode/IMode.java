package client.mode;

import client.Receiver;

import java.util.Scanner;

public interface IMode {

    /**
     * Executes the mode-specific functionality.
     */
    void executeMode();

    /**
     * Retrieves a scanner object.
     *
     * @return a scanner object for input processing.
     */
    Scanner getScanner();
}

