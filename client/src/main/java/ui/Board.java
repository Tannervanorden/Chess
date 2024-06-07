package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;


public class Board {

    private ChessGame game;

    private static final int boardSize = 8;

    public Board(ChessGame game) {
        this.game = game;
    }

    public static void main(String[] args) {
        ChessGame game = new ChessGame();

        Board board = new Board(game);

        board.drawChessBoard();
    }

    private void drawChessBoard() {
        ChessBoard board = game.getBoard();
        System.out.print("   a  b  c  d  e  f  g  h\n");

        for (int row = 0; row < boardSize; row++) {
            System.out.print(8 - row + " ");

            for (int col = 0; col < boardSize; col++) {
                // Work on squares
                String color = (row + col) % 2 == 0 ? EscapeSequences.SET_BG_COLOR_WHITE : EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
                System.out.print(color);

                ChessPosition position = new ChessPosition(row + 1, col + 1);
                ChessPiece piece = board.getPiece(position);
                if (piece != null){
                    if (piece.getPieceType() == ChessPiece.PieceType.ROOK){
                        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                            System.out.print("R");
                        }
                        else {
                            System.out.print("R");
                        }
                    }
                }
                else {
                    System.out.print(EscapeSequences.EMPTY);
                }

            }
            System.out.println(EscapeSequences.RESET_BG_COLOR);
        }
    }
}
