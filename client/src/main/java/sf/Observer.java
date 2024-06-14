package sf;

import websocket.messages.ServerMessage;

public interface Observer {
    public void notify (ServerMessage message);
}
