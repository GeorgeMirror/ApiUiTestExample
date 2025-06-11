package org.example.ws;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EchoClientTest {

    private EchoClient client;

    @BeforeEach
    @DisplayName("Подключение к WebSocket серверу перед каждым тестом")
    public void setup() throws Exception {
        client = new EchoClient();
        client.connect();
    }

    @AfterEach
    @DisplayName("Закрытие WebSocket соединения после каждого теста")
    public void teardown() throws Exception {
        if (client != null && client.isOpen()) {
            client.close();
        }
    }

    @Test
    @DisplayName("Успешное подключение к WebSocket серверу")
    public void testConnect() {
        assertTrue(client.isOpen(), "WebSocket должен быть открыт после connect()");
    }

    @Test
    @DisplayName("Успешное закрытие WebSocket соединения")
    public void testClose() throws Exception {
        client.close();
        assertFalse(client.isOpen(), "WebSocket должен быть закрыт после close()");
    }

    @Test
    @DisplayName("Отправка сообщения и получение эхо-ответа")
    public void testSendMessage() throws InterruptedException {
        String message = "Hello WebSocket!";
        String response = client.sendMessage(message);
        assertEquals(message, response);
    }

    @Test
    @DisplayName("Обработка JSON с ошибкой от сервера")
    public void testSendMessageWithError() {
        String errorJson = "{\"error\":\"Invalid request\"}";
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            client.sendMessage(errorJson);
        });
        assertTrue(thrown.getMessage().contains("Error received from server"));
    }
}
