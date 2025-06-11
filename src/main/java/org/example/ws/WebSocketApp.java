package org.example.ws;

public class WebSocketApp {
    public static void main(String[] args) {
        EchoClient client = new EchoClient("wss://echo.websocket.events");
        try {
            client.connect();
            String response = client.sendMessage("Hello WebSocket!");
            System.out.println("Response: " + response);
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
