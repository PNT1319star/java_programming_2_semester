package processing;

import authentication.JWTDecoder;
import connector.Receiver;
import connector.Sender;
import interaction.Request;
import interaction.Response;
import utilities.Invoker;
import utility.ConsolePrinter;

import java.io.IOException;
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
                    String payload = JWTDecoder.checkJWT(request.getJwtToken());
                    if (payload.contains("Token has expired!")) {
                        response = new Response("Your token has expired!");
                    }
                    else if (payload.contains("Invalid JWT token")) {
                        response = new Response("The token is invalid");
                    } else {
                        response = new Response(Invoker.executeCommand(request));
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
