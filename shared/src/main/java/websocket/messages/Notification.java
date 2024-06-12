package websocket.messages;

public class Notification extends ServerMessage {
    private String notification;

    public Notification(String notification) {
        super(ServerMessageType.NOTIFICATION);
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }
}
