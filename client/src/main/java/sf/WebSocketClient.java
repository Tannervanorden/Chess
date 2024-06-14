package sf;

import javax.websocket.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WebSocketClient extends Endpoint {
    private List<Observer> observers = new ArrayList<>();

    public Session session;

    public WebSocketClient() throws Exception {
        URI uri = new URI("ws://localhost:8080/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                System.out.println(message);
            }
        });
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
