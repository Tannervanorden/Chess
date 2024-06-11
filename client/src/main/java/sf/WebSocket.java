package sf;

import com.google.gson.Gson;
import chess.ChessMove;
import websocket.*;

public class WebSocket {
    private static WebSocket instance;
    private Gson gson;

    private WebSocket() {
        gson = new Gson();
    }

    public static WebSocket getInstance() {
        if (instance == null) {
            instance = new WebSocket();
        }
        return instance;
    }
}
