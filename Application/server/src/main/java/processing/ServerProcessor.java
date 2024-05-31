package processing;

import connector.Receiver;
import connector.Sender;
import database.DatabaseSessionManager;
import org.csjchoisoojong.exceptions.HandlingDatabaseException;
import org.csjchoisoojong.interaction.Request;
import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.specificcommands.AbstractCommand;
import utilities.CollectionManager;
import utilities.Invoker;
import org.csjchoisoojong.utility.ConsolePrinter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerProcessor {
    private final Receiver receiver;
    private final Sender sender;
    private final DatabaseSessionManager databaseSessionManager;
    private final BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Response> responseQueue = new LinkedBlockingQueue<>();
    private final ExecutorService requestReader = Executors.newCachedThreadPool();
    private final ExecutorService requestProcessor = Executors.newFixedThreadPool(5);
    private final ExecutorService responseSender = Executors.newCachedThreadPool();

    public ServerProcessor(Receiver receiver, Sender sender, DatabaseSessionManager databaseSessionManager) {
        this.receiver = receiver;
        this.sender = sender;
        this.databaseSessionManager = databaseSessionManager;

    }

    public void decodeAndProcessCommand() {
        //Request reader task
        requestReader.execute(() -> {
            try {
                Request request = receiver.receive();
                if (request != null) requestQueue.put(request);
            } catch (IOException | ClassNotFoundException exception) {
                ConsolePrinter.printError("There is no any requests!");
            } catch (InterruptedException e) {
                ConsolePrinter.printError("Interrupted while waiting for request: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });

        //Request processor task
        requestProcessor.execute(() -> {
            Response response;
            try {
                Request request = requestQueue.take();
                if (request.getSessionId().isEmpty()) response = Invoker.executeCommand(request);
                else {
                    String sessionId = request.getSessionId();
                    int userId = databaseSessionManager.getUserIdBySessionId(sessionId);
                    CollectionManager.setUserId(userId);
                    response = Invoker.executeCommand(request);
                }
                if (response != null) responseQueue.put(response);
            } catch (InterruptedException exception) {
                ConsolePrinter.printError("Interrupted while processing request: " + exception.getMessage());
                throw new RuntimeException(exception);
            } catch (NullPointerException exception) {
                throw new RuntimeException(exception);
            } catch (HandlingDatabaseException e) {
                ConsolePrinter.printError("Something when wrong in the database");
            }
        });

        //Response sender task
        responseSender.execute(() -> {
            try {
                Response response = responseQueue.take();
                sender.send(response);
            } catch (IOException e) {
                ConsolePrinter.printError("Error sending response: " + e.getMessage());
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                ConsolePrinter.printError("Interrupted while sending response: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }

}
