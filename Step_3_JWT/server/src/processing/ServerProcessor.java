package processing;

import authentication.JWTDecoder;
import connector.Receiver;
import connector.Sender;
import interaction.Request;
import interaction.Response;
import processing.specificcommands.AbstractCommand;
import utilities.Invoker;
import utility.ConsolePrinter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerProcessor {
    private final Receiver receiver;
    private final Sender sender;
    private final Exchanger<Request> requestExchanger = new Exchanger<>();
    private final Exchanger<Response> responseExchanger = new Exchanger<>();
    private final ExecutorService requestReader = Executors.newCachedThreadPool();
    private final ExecutorService requestProcessor = Executors.newFixedThreadPool(5);
    private final ExecutorService responseSender = Executors.newCachedThreadPool();

    public ServerProcessor(Receiver receiver, Sender sender) {
        this.receiver = receiver;
        this.sender = sender;
    }

    public void decodeAndProcessCommand() {
        requestReader.execute(() -> {
            while (true) {
                try {
                    Request request = receiver.receive();
                    requestExchanger.exchange(request);
                } catch (IOException | ClassNotFoundException e) {
                    ConsolePrinter.printError("Error receiving request: " + e.getMessage());
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    ConsolePrinter.printError("Interrupted while waiting for request: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        });

        requestProcessor.execute(() -> {
            while (true) {
                try {
                    Request request = requestExchanger.exchange(null);
                    Response response;
                    if (request.getJwtToken().isEmpty()) {
                        HashMap<String, AbstractCommand> commandHashMap = Invoker.getRoleCommandsList().get("server");
                        response = new Response(Invoker.executeCommand(request, commandHashMap));
                    } else {
                        String token = request.getJwtToken();
                        if(JWTDecoder.checkJWT(token)) {
                            String username = JWTDecoder.getUsername(token);
                            List<String> functionList = JWTDecoder.getFunctionList(token);
                            HashMap<String, AbstractCommand> commandHashMap = new HashMap<>();
                            for (String function : functionList) {
                                HashMap<String, AbstractCommand> smallCommandsList = Invoker.getRoleCommandsList().get(function);
                                for (Map.Entry<String, AbstractCommand> entry : smallCommandsList.entrySet()) {
                                    commandHashMap.put(entry.getKey(), entry.getValue());
                                }
                            }
                            response = new Response(Invoker.executeCommand(request, commandHashMap));
                        } else response = new Response("Token has expired!");
                    }
                    responseExchanger.exchange(response);
                } catch (InterruptedException exception) {
                    ConsolePrinter.printError("Interrupted while processing request: " + exception.getMessage());
                    throw new RuntimeException(exception);
                }
            }
        });

        responseSender.execute(() -> {
            while (true) {
                try {
                    Response response = responseExchanger.exchange(null);
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
}
