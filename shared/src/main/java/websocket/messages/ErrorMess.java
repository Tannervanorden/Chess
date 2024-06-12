package websocket.messages;

public class ErrorMess extends ServerMessage {
    public ErrorMess() {
        super(ServerMessageType.ERROR);
    }
}
