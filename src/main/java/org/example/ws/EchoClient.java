package org.example.ws;

import javax.websocket.*;
import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
public class EchoClient {

    private final String uri;
    private Session session;
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    private final CountDownLatch connectedLatch = new CountDownLatch(1);
    private final CountDownLatch closedLatch = new CountDownLatch(1);

    // Конструктор без параметров — используем URL по умолчанию
    public EchoClient() {
        this.uri = "wss://echo.websocket.events";
    }

    // Конструктор с параметром URI
    public EchoClient(String uri) {
        this.uri = uri;
    }

    public void connect() throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, new URI(uri));

        if (!connectedLatch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timeout while waiting for WebSocket connection.");
        }

        // Ожидаем приветственное сообщение и выбрасываем его
        String greeting = messageQueue.poll(2, TimeUnit.SECONDS);
        if (greeting != null) {
            System.out.println("Greeting received and skipped: " + greeting);
        }
    }

    public String sendMessage(String message) throws InterruptedException {
        messageQueue.clear(); // очищаем очередь от старых сообщений
        session.getAsyncRemote().sendText(message);

        // Ждём echo-ответ
        String response = messageQueue.poll(5, TimeUnit.SECONDS);
        if (response == null) {
            throw new RuntimeException("No response received for message: " + message);
        }

        // Проверяем, не является ли ответ JSON с ошибкой
        if (response.startsWith("{") && response.contains("\"error\"")) {
            throw new RuntimeException("Error received from server: " + response);
        }

        return response;
    }

    public void close() throws Exception {
        if (session != null && session.isOpen()) {
            session.close();
        }
        closedLatch.await(3, TimeUnit.SECONDS);
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        connectedLatch.countDown();
        System.out.println("Connected to server");
    }

    @OnMessage
    public void onMessage(String message) {
        messageQueue.offer(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error: " + throwable.getMessage());
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Connection closed: " + closeReason);
        closedLatch.countDown();
    }

    public boolean isOpen() {
        return session != null && session.isOpen();
    }
}