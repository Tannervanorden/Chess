package ui;

import chess.ChessGame;
import sf.Observer;
import sf.WebSocketClient;
import websocket.messages.ServerMessage;

public class GamePlay implements Observer{
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

    @Override
    public void notify(ServerMessage message) {

    }
}
