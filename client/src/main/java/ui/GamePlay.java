package ui;

import chess.ChessGame;
import sf.Observer;
import sf.WebSocketClient;
import websocket.messages.ServerMessage;

import java.util.Scanner;

public class GamePlay implements Observer{
    private WebSocketClient webSocket;
    private int gameId;
    private String authToken;
    private Scanner scanner = new Scanner(System.in);

    private ChessGame game;

    public GamePlay(ChessGame game, int gameId, String authToken) {
        this.game = game;
        this.gameId = gameId;
        this.authToken = authToken;
    }
    public void displayChessBoard(String color) {
        Board board = new Board(game);
        if (color.equalsIgnoreCase("White")) {
            board.drawChessBoard(false);
        } else {
            board.drawChessBoard(true);
        }
        displayGamePlayMenu();
    }

    public void displayGamePlayMenu() {
        while (true) {
            System.out.print("Gameplay Menu:\n");
            System.out.print("1. Make a Move\n");
            System.out.print("2. Surrender\n");
            System.out.print("3. Exit to Main Menu\n");
            System.out.print("Enter: ");

            int choice = scanner.nextInt();

            if (choice == 1) {

            }
        }
    }

    @Override
    public void notify(ServerMessage message) {

    }
}
