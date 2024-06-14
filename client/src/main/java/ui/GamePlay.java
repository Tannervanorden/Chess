package ui;

import chess.ChessGame;
import sf.WebSocketClient;

public class GamePlay {
    private WebSocketClient webSocket;

    private ChessGame game;

    public GamePlay(ChessGame game) {
        this.game = game;
    }
}
