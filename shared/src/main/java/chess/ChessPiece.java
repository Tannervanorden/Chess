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


    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
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

//        if (type == PieceType.PAWN) {
//        }

        if (type == PieceType.BISHOP) {
            // Diagonal moves
            addDiagonalMove(validMoves, board, myPosition, 1, 1); //right
            addDiagonalMove(validMoves, board, myPosition, -1, 1);  // left
        }
        else if (type == PieceType.ROOK) {
            addStraightMove(validMoves, board, myPosition);
        }

        return validMoves;
    }

    private void addDiagonalMove(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition currentPosition, int rowChange, int colChange) {
        int row = currentPosition.getRow();
        int col = currentPosition.getColumn();

        // Generate diagonal moves in the forward direction
        while (true) {
            row += rowChange;
            col += colChange;

            if (!board.isValidPosition(row, col)) {
                break;
            }

            ChessPosition nextPosition = new ChessPosition(row, col);
            ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);



            // Add the diagonal move if it's within the board boundaries and the square is empty
            if (pieceAtNextPosition == null || pieceAtNextPosition.getTeamColor() != pieceColor) {
                validMoves.add(new ChessMove(currentPosition, nextPosition, null));
                if ((pieceAtNextPosition != null && pieceAtNextPosition.getTeamColor() != pieceColor)) {
                    break;
                }
            }
            else {
                break; // Stop generating moves in this direction if blocked by a piece
            }
        }

        // Reset row and col for backward iteration
        row = currentPosition.getRow();
        col = currentPosition.getColumn();

        // Generate diagonal moves in the backward direction
        while (true) {
            row -= rowChange;
            col -= colChange;

            if (!board.isValidPosition(row, col)) {
                break;
            }

            ChessPosition nextPosition = new ChessPosition(row, col);
            ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);

            // Add the diagonal move if it's within the board boundaries and the square is empty
            if (pieceAtNextPosition == null || pieceAtNextPosition.getTeamColor() != pieceColor) {
                validMoves.add(new ChessMove(currentPosition, nextPosition, null));
                if ((pieceAtNextPosition != null && pieceAtNextPosition.getTeamColor() != pieceColor)) {
                    break;
                }
            }
            else {
                break; // Stop generating moves in this direction if blocked by a piece
            }
        }
    }

    private void addStraightMove(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition currentPosition) {
        int row = currentPosition.getRow();
        int col = currentPosition.getColumn();

        while (true) {
            row += 1;

            if (!board.isValidPosition(row, col)) {
                break;
            }

            ChessPosition nextPosition = new ChessPosition(row, col);
            ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);

            // Add the diagonal move if it's within the board boundaries and the square is empty
            if (pieceAtNextPosition == null || pieceAtNextPosition.getTeamColor() != pieceColor) {
                validMoves.add(new ChessMove(currentPosition, nextPosition, null));
                if ((pieceAtNextPosition != null && pieceAtNextPosition.getTeamColor() != pieceColor)) {
                    break;
                }
            }
            else {
                break; // Stop generating moves in this direction if blocked by a piece
            }
        }
        row = currentPosition.getRow();
        col = currentPosition.getColumn();

        while (true) {
            row -= 1;

            if (!board.isValidPosition(row, col)) {
                break;
            }

            ChessPosition nextPosition = new ChessPosition(row, col);
            ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);

            // Add the diagonal move if it's within the board boundaries and the square is empty
            if (pieceAtNextPosition == null || pieceAtNextPosition.getTeamColor() != pieceColor) {
                validMoves.add(new ChessMove(currentPosition, nextPosition, null));
                if ((pieceAtNextPosition != null && pieceAtNextPosition.getTeamColor() != pieceColor)) {
                    break;
                }
            }
            else {
                break; // Stop generating moves in this direction if blocked by a piece
            }
        }

        row = currentPosition.getRow();
        col = currentPosition.getColumn();

        while (true) {
            col += 1;

            if (!board.isValidPosition(row, col)) {
                break;
            }

            ChessPosition nextPosition = new ChessPosition(row, col);
            ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);

            // Add the diagonal move if it's within the board boundaries and the square is empty
            if (pieceAtNextPosition == null || pieceAtNextPosition.getTeamColor() != pieceColor) {
                validMoves.add(new ChessMove(currentPosition, nextPosition, null));
                if ((pieceAtNextPosition != null && pieceAtNextPosition.getTeamColor() != pieceColor)) {
                    break;
                }
            }
            else {
                break; // Stop generating moves in this direction if blocked by a piece
            }
        }
        row = currentPosition.getRow();
        col = currentPosition.getColumn();

        while (true) {
            col -= 1;

            if (!board.isValidPosition(row, col)) {
                break;
            }

            ChessPosition nextPosition = new ChessPosition(row, col);
            ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);

            // Add the diagonal move if it's within the board boundaries and the square is empty
            if (pieceAtNextPosition == null || pieceAtNextPosition.getTeamColor() != pieceColor) {
                validMoves.add(new ChessMove(currentPosition, nextPosition, null));
                if ((pieceAtNextPosition != null && pieceAtNextPosition.getTeamColor() != pieceColor)) {
                    break;
                }
            }
            else {
                break; // Stop generating moves in this direction if blocked by a piece
            }
        }
    }

}
