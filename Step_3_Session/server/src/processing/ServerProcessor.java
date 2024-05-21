package processing;

import connector.Receiver;
import connector.Sender;
import database.DatabaseSessionManager;
import exceptions.HandlingDatabaseException;
import interaction.Request;
import interaction.Response;
import processing.specificcommands.AbstractCommand;
import utilities.CollectionManager;
import utilities.Invoker;
import utility.ConsolePrinter;

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
            } catch (IOException | ClassNotFoundException ignored) {

            } catch (InterruptedException e) {
                ConsolePrinter.printError("Interrupted while waiting for request: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });

        //Request processor task
        for (int i = 0; i < 5; i++) {
            requestProcessor.execute(() -> {
                try {
                    Request request = requestQueue.take();
                    Response response;
                    if (request.getSessionId().isEmpty()) {
                        HashMap<String, AbstractCommand> commandHashMap = Invoker.getRoleCommandsList().get("server");
                        response = new Response(Invoker.executeCommand(request, commandHashMap));
                    } else {
                        String sessionId = request.getSessionId();
                        int userId = databaseSessionManager.getUserIdBySessionId(sessionId);
                        CollectionManager.setUsername(userId);
                        List<String> functionList = databaseSessionManager.getFunctionsList(sessionId);
                        HashMap<String, AbstractCommand> commandHashMap = new HashMap<>();
                        for (String function : functionList) {
                            HashMap<String, AbstractCommand> smallCommandsList = Invoker.getRoleCommandsList().get(function);
                            commandHashMap.putAll(smallCommandsList);
                        }
                        try {
                            response = new Response(Invoker.executeCommand(request, commandHashMap));
                        } catch (NullPointerException exception) {
                            response = new Response("You can not use this command!");
                        }
                    }
                    responseQueue.put(response);
                } catch (InterruptedException exception) {
                    ConsolePrinter.printError("Interrupted while processing request: " + exception.getMessage());
                    throw new RuntimeException(exception);
                } catch (HandlingDatabaseException exception) {
                    ConsolePrinter.printError("Something when wrong in the database");
                }
            });
        }

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
