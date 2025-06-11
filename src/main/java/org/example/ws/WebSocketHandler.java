package org.example.ws;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class WebSocketHandler extends WebSocketClient {
    private final CompletableFuture<String> messageFuture = new CompletableFuture<>();
    private final CompletableFuture<Boolean> connectionFuture = new CompletableFuture<>();

    public WebSocketHandler(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        connectionFuture.complete(true);
    }

    @Override
    public void onMessage(String message) {
        messageFuture.complete(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        messageFuture.completeExceptionally(ex);
    }

    public CompletableFuture<Boolean> getConnectionFuture() {
        return connectionFuture;
    }

    public CompletableFuture<String> getMessageFuture() {
        return messageFuture;
    }
}
