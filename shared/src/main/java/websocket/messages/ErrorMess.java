package websocket.messages;

public class ErrorMess extends ServerMessage {
    private String message;

    public ErrorMess(String message) {
        super(ServerMessageType.ERROR);
        this.message = message;
    }

    public String getMessage () {
        return message;
    }
}
