package chess;

import java.util.Collection;
import java.util.Objects;
import java.util.ArrayList;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    public ChessPiece(ChessPiece original) { // Copy Constructor
        this.pieceColor = original.pieceColor;
        this.type = original.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType(){
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (type == PieceType.PAWN) {
            calculatePawnMoves(validMoves, board, myPosition);
        }

        if (type == PieceType.KNIGHT) {
            int[][] knightMoves = {
                    {-2, -1}, {-2, 1},
                    {-1, -2}, {-1, 2},
                    {1, -2}, {1, 2},
                    {2, -1}, {2, 1}
            };
            calculateMovesFromArray(validMoves, board, myPosition, knightMoves);
        }

        if (type == PieceType.BISHOP) {
            // Diagonal moves
            addMovesInAnyDirection(validMoves, board, myPosition, 1, 1); // Diagonal right down
            addMovesInAnyDirection(validMoves, board, myPosition, -1, 1); // Diagonal left down
            addMovesInAnyDirection(validMoves, board, myPosition, 1, -1); // Diagonal right up
            addMovesInAnyDirection(validMoves, board, myPosition, -1, -1); // Diagonal left up
        }
        else if (type == PieceType.ROOK) {
            addMovesInAnyDirection(validMoves, board, myPosition, 1, 0); // Down
            addMovesInAnyDirection(validMoves, board, myPosition, -1, 0); // Up
            addMovesInAnyDirection(validMoves, board, myPosition, 0, 1); // Right
            addMovesInAnyDirection(validMoves, board, myPosition, 0, -1); // Left
        }
        else if (type == PieceType.QUEEN) {
            addMovesInAnyDirection(validMoves, board, myPosition, 0, -1); // Left
            addMovesInAnyDirection(validMoves, board, myPosition, 1, 0); // Down
            addMovesInAnyDirection(validMoves, board, myPosition, 0, 1); // Right
            addMovesInAnyDirection(validMoves, board, myPosition, -1, 0); // Up

        }
        if (type == PieceType.KING) {
            int[][] kingMoves = {
                    {-1, -1}, {-1, 0}, {-1, 1},
                    {0, -1}, {0, 1},
                    {1, -1}, {1, 0}, {1, 1}
            };
            calculateMovesFromArray(validMoves, board, myPosition, kingMoves);
        }

        return validMoves;
    }

    private void calculatePawnMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int direction = (pieceColor == ChessGame.TeamColor.WHITE) ? 1 : -1;

        // Check if the square in front of the pawn is empty
        if (board.isValidPosition(row + direction, col)) {
            ChessPosition nextPositionOne = new ChessPosition(row + direction, col);
            ChessPiece pieceAtNextPositionOne = board.getPiece(nextPositionOne);
            if (pieceAtNextPositionOne == null && !((direction == 1 && row == 7) || (direction == -1 && row == 2))) {
                validMoves.add(new ChessMove(myPosition, nextPositionOne, null)); // No promotion

                // Check if it's the pawn's first move and the two squares ahead are empty
                if ((row == 2 && direction == 1) || (row == 7 && direction == -1)) {
                    ChessPosition nextPositionTwo = new ChessPosition(row + 2 * direction, col);
                    ChessPiece pieceAtNextPositionTwo = board.getPiece(nextPositionTwo);
                    if (pieceAtNextPositionTwo == null) {
                        validMoves.add(new ChessMove(myPosition, nextPositionTwo, null)); // No promotion
                    }
                }
            }
        }

        // Check diagonal captures
        int[] colOffsets = { -1, 1 };
        for (int colOffset : colOffsets) {
            if (board.isValidPosition(row + direction, col + colOffset)) {
                ChessPosition nextPosition = new ChessPosition(row + direction, col + colOffset);
                ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);
                if ((pieceAtNextPosition != null && pieceAtNextPosition.getTeamColor() != pieceColor) && !((direction == 1 && row == 7) || (direction == -1 && row == 2))) {
                    validMoves.add(new ChessMove(myPosition, nextPosition, null)); // No promotion
                }
                else if (((direction == 1 && row == 7) || (direction == -1 && row == 2)) && isDiagonalPiecePresent(board, row, col, direction)) {
                    ChessPosition promotionPosition = new ChessPosition(row + direction, col + colOffset);
                    validMoves.add(new ChessMove(myPosition, promotionPosition, ChessPiece.PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, promotionPosition, ChessPiece.PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, promotionPosition, ChessPiece.PieceType.ROOK));
                    validMoves.add(new ChessMove(myPosition, promotionPosition, ChessPiece.PieceType.KNIGHT));
                    break;

                }
            }
        }

        // Check for pawn promotion
        if ((direction == 1 && row == 7) || (direction == -1 && row == 2)) {
            // Promote the pawn to different piece types
            ChessPosition promotionPosition = new ChessPosition(row + direction, col);
//            validMoves.add(new ChessMove(myPosition, promotionPosition, null)); // No promotion
            validMoves.add(new ChessMove(myPosition, promotionPosition, ChessPiece.PieceType.QUEEN));
            validMoves.add(new ChessMove(myPosition, promotionPosition, ChessPiece.PieceType.BISHOP));
            validMoves.add(new ChessMove(myPosition, promotionPosition, ChessPiece.PieceType.ROOK));
            validMoves.add(new ChessMove(myPosition, promotionPosition, ChessPiece.PieceType.KNIGHT));
        }
    }



    private boolean isDiagonalPiecePresent(ChessBoard board, int row, int col, int direction) {
        int[] colOffsets = { -1, 1 };
        for (int colOffset : colOffsets) {
            int nextRow = row + direction;
            int nextCol = col + colOffset;
            if (board.isValidPosition(nextRow, nextCol)) {
                ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
                ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);
                if (pieceAtNextPosition != null && pieceAtNextPosition.getTeamColor() != pieceColor) {
                    return true;
                }
            }
        }
        return false;
    }

    private void calculateMovesFromArray(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition, int[][] moves) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        for (int[] move : moves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            if (board.isValidPosition(newRow, newCol)) {
                ChessPosition nextPosition = new ChessPosition(newRow, newCol);
                ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);

                if (pieceAtNextPosition == null || pieceAtNextPosition.getTeamColor() != pieceColor) {
                    validMoves.add(new ChessMove(myPosition, nextPosition, null));
                }
            }
        }
    }

    private void addMovesInAnyDirection(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition currentPosition, int rowChange, int colChange) {
        int row = currentPosition.getRow();
        int col = currentPosition.getColumn();

        while (true) {
            row += rowChange;
            col += colChange;

            if (!board.isValidPosition(row, col)) {
                break;
            }

            ChessPosition nextPosition = new ChessPosition(row, col);
            ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);

            // Add the move if it's within the board boundaries and the square is empty or contains an opponent's piece
            if (pieceAtNextPosition == null || pieceAtNextPosition.getTeamColor() != pieceColor) {
                validMoves.add(new ChessMove(currentPosition, nextPosition, null));
                if (pieceAtNextPosition != null) {
                    break;
                }
            } else {
                break; // Stop generating moves in this direction if blocked by a piece
            }
        }
    }

}
