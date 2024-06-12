package websocket.commands;

public class Connect extends UserGameCommand {
    public Connect(String authToken) {
        super(authToken);
        this.commandType = CommandType.CONNECT;
    }
}
