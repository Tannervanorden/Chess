package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;


public class Board {

    private ChessGame game;

    private static final int BOARD_SIZE = 8;

    public Board(ChessGame game) {
        this.game = game;
    }

    public static void main(String[] args) {
        ChessGame game = new ChessGame();

        Board board = new Board(game);

        board.drawChessBoard(true);
    }

    public void drawChessBoard(boolean isWhiteOnBottom) {
        ChessBoard board = game.getBoard();

        int startRow = isWhiteOnBottom ? BOARD_SIZE - 1 : 0;
        int endRow = isWhiteOnBottom ? -1 : BOARD_SIZE;
        int rowStep = isWhiteOnBottom ? -1 : 1;
        String top;
        if (isWhiteOnBottom) {
            top = "  h  g  f  e  d  c  b  a\n";
        }
        else {
            top = "   a  b  c  d  e  f  g  h\n";
        }
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA + top);

        for (int row = startRow; row != endRow; row += rowStep) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA + (8 - row) + " ");

            for (int col = 0; col < BOARD_SIZE; col++) {
                // Work on squares
                String color = (row + col) % 2 == 0 ? EscapeSequences.SET_BG_COLOR_LIGHT_GREY : EscapeSequences.SET_BG_COLOR_DARK_GREY;
                System.out.print(color);

                ChessPosition position = new ChessPosition(row + 1, col + 1);
                ChessPiece piece = board.getPiece(position);
                if (piece != null) {
                    if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
                        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                            System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + " R ");
                        } else {
                            System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE + " R ");
                        }
                    } else if (piece.getPieceType() == ChessPiece.PieceType.KING) {
                        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                            System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + " K ");
                        } else {
                            System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE + " K ");
                        }
                    } else if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                            System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + " N ");
                        } else {
                            System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE + " N ");
                        }
                    } else if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                            System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + " Q ");
                        } else {
                            System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE + " Q ");
                        }
                    } else if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
                        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                            System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + " B ");
                        } else {
                            System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE + " B ");
                        }
                    } else if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                            System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + " P ");
                        } else {
                            System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE + " P ");
                        }
                    }
                } else {
                    System.out.print(EscapeSequences.EMPTY);
                }
            }
            System.out.println(EscapeSequences.RESET_BG_COLOR);
        }
    }

}
