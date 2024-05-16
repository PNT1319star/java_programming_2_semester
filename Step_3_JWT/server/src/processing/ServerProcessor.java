package processing;

import authentication.JWTDecoder;
import connector.Receiver;
import connector.Sender;
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
    private final BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Response> responseQueue = new LinkedBlockingQueue<>();
    private final ExecutorService requestReader = Executors.newCachedThreadPool();
    private final ExecutorService requestProcessor = Executors.newFixedThreadPool(5);
    private final ExecutorService responseSender = Executors.newCachedThreadPool();
    private volatile boolean isRunning = true;

    public ServerProcessor(Receiver receiver, Sender sender) {
        this.receiver = receiver;
        this.sender = sender;
    }

    public void decodeAndProcessCommand() {
        //Request reader task
        requestReader.execute(() -> {
            while (isRunning) {
                try {
                    Request request = receiver.receive();
                    requestQueue.put(request);
                } catch (IOException | ClassNotFoundException e) {
                    ConsolePrinter.printError("Error receiving request: " + e.getMessage());
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    ConsolePrinter.printError("Interrupted while waiting for request: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        });

        //Request processer task
        for (int i = 0; i < 5; i++) {
            requestProcessor.execute(() -> {
                while (isRunning) {
                    try {
                        Request request = requestQueue.take();
                        Response response;
                        if (request.getJwtToken().isEmpty()) {
                            HashMap<String, AbstractCommand> commandHashMap = Invoker.getRoleCommandsList().get("server");
                            response = new Response(Invoker.executeCommand(request, commandHashMap));
                        } else {
                            String token = request.getJwtToken();
                            if (JWTDecoder.checkJWT(token)) {
                                String username = JWTDecoder.getUsername(token);
                                CollectionManager.setUsername(username);
                                List<String> functionList = JWTDecoder.getFunctionList(token);
                                HashMap<String, AbstractCommand> commandHashMap = new HashMap<>();
                                for (String function : functionList) {
                                    HashMap<String, AbstractCommand> smallCommandsList = Invoker.getRoleCommandsList().get(function);
                                    commandHashMap.putAll(smallCommandsList);
                                }
                                response = new Response(Invoker.executeCommand(request, commandHashMap));
                            } else response = new Response("Token has expired!");
                        }
                        responseQueue.put(response);
                    } catch (InterruptedException exception) {
                        ConsolePrinter.printError("Interrupted while processing request: " + exception.getMessage());
                        throw new RuntimeException(exception);
                    }
                }
            });
        }

        //Response sender task
        responseSender.execute(() -> {
            while (isRunning) {
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
            }
        });
    }
    public void stop() {
        isRunning = false;
        requestReader.shutdown();
        requestProcessor.shutdown();
        responseSender.shutdown();
    }
}
