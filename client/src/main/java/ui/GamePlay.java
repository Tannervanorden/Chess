package ui;

import chess.ChessGame;
import sf.WebSocketClient;

public class GamePlay {
    private WebSocketClient webSocket;

    private ChessGame game;

    public GamePlay(ChessGame game) {
        this.game = game;
    }
    public void displayChessBoard(String color) {
        Board board = new Board(game);
        if (color.equalsIgnoreCase("White")) {
            board.drawChessBoard(false);
        } else {
            board.drawChessBoard(true);
        }
    }
}
