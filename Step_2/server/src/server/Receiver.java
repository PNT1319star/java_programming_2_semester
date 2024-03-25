package server;

import data.Organization;
import utility.CollectionManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Receiver {
    private final Socket socket;

    public Receiver(Socket socket) {
        this.socket = socket;
    }

    public void add(Object object) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            String answer = CollectionManager.add((Organization) object);
            outputStream.writeObject(answer);
        }
    }

    public void addIfMax(Object object) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            String answer = CollectionManager.addIfMax((Organization) object);
            outputStream.writeObject(answer);
        }
    }

    public void clear() throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            String answer = CollectionManager.clear();
            outputStream.writeObject(answer);
        }
    }

    public void filterStartsWithFullName(String fullName) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            String answer = CollectionManager.filterStartsWithFullName(fullName);
            outputStream.writeObject(answer);
        }
    }

    public void head() throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            String answer = CollectionManager.head();
            outputStream.writeObject(answer);
        }
    }

    public void info() throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            String answer = CollectionManager.info();
            outputStream.writeObject(answer);
        }
    }

    public void minByCreationDate() throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            String answer = CollectionManager.minByCreationDate();
            outputStream.writeObject(answer);
        }
    }

    public void printUniquePostalAddress() throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            String answer = CollectionManager.printUniquePostalAddress();
            outputStream.writeObject(answer);
        }
    }

    public void removeByID(String sID) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            String answer = CollectionManager.removeByID(sID);
            outputStream.writeObject(answer);
        }
    }

    public void removeLower(Object object) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            String answer = CollectionManager.removeLower((Organization) object);
            outputStream.writeObject(answer);
        }
    }
    public void show() throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            String answer = CollectionManager.show();
            outputStream.writeObject(answer);
        }
    }
    public void update(String sID, Object object) throws IOException {
        int ID;
        String answer;
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            ID = Integer.parseInt(sID);
            if (CollectionManager.idExistence(ID)) {
                CollectionManager.updateElement((Organization) object, ID);
                answer = "The organization has been updated";
            } else {
                answer = "This ID does not exist in this collection!";
            }
            outputStream.writeObject(answer);
        }
    }
}
