package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.Gson;
import sf.WebSocketClient;
import websocket.commands.Connect;
import websocket.commands.MakeMove;
import java.util.Scanner;

public class GamePlay {
    private Gson gson = new Gson();
    private WebSocketClient webSocket;
    private int gameId;
    private String authToken;
    private Scanner scanner = new Scanner(System.in);


    private ChessGame game;

    public GamePlay(ChessGame game, int gameId, String authToken) {
        try {
            this.webSocket = new WebSocketClient();

            Connect connect = new Connect(authToken, gameId);
            String jsonCommand = gson.toJson(connect);
            this.webSocket.send(jsonCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            System.out.print("Enter: \n");

            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter start position: ");
                String start = scanner.next();
                ChessPosition startPosition = positionGetter(start);
                System.out.print("Enter end position: ");
                String end = scanner.next();
                ChessPosition endPosition = positionGetter(end);

                ChessPiece.PieceType promotionPiece = null;

                ChessMove move = new ChessMove(startPosition, endPosition, promotionPiece);
                MakeMove command = new MakeMove(authToken, gameId, move);

                String jsonCommand = gson.toJson(command);
                try {
                    webSocket.send(jsonCommand);
                } catch (Exception e) {
                    System.out.println("Failed to send move command: " + e.getMessage());
                }

            }
        }
    }


    private ChessPosition positionGetter(String pos) {
        int file = pos.charAt(0) - 'a';
        int rank = pos.charAt(1) - '1';
        return new ChessPosition(file, rank);
    }
}
