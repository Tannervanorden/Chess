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
            calculateKnightMoves(validMoves, board, myPosition);
        }

        if (type == PieceType.BISHOP) {
            // Diagonal moves
            addDiagonalMove(validMoves, board, myPosition, 1, 1); //right
            addDiagonalMove(validMoves, board, myPosition, -1, 1);  // left
        }
        else if (type == PieceType.ROOK) {
            addStraightMove(validMoves, board, myPosition);
        }
        else if (type == PieceType.QUEEN) {
            addDiagonalMove(validMoves, board, myPosition, 1, 1); //right
            addDiagonalMove(validMoves, board, myPosition, -1, 1);  // left
            addStraightMove(validMoves, board, myPosition);
        }
        if (type == PieceType.KING) {

            // The king can move in 8 directions (horizontal, vertical, and diagonal)
            int[] rowOffsets = {1, 1, 1, 0, 0, -1, -1, -1};
            int[] colOffsets = {1, 0, -1, 1, -1, 1, 0, -1};

            for (int i = 0; i < 8; i++) {
                int newRow = row + rowOffsets[i];
                int newCol = col + colOffsets[i];

                if (board.isValidPosition(newRow, newCol)) {
                    ChessPosition nextPosition = new ChessPosition(newRow, newCol);
                    ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);
                    // Add the move if the position is empty or occupied by an opponent's piece
                    if (pieceAtNextPosition == null || pieceAtNextPosition.getTeamColor() != pieceColor) {
                        validMoves.add(new ChessMove(myPosition, nextPosition, null));
                    }
                }
            }
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

    private void calculateKnightMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int[][] knightMoves = {
                {-2, -1}, {-2, 1}, // Two squares up/down and one square left/right
                {-1, -2}, {-1, 2}, // One square up/down and two squares left/right
                {1, -2}, {1, 2},   // One square up/down and two squares left/right
                {2, -1}, {2, 1}    // Two squares up/down and one square left/right
        };

        for (int[] move : knightMoves) {
            int nextRow = row + move[0];
            int nextCol = col + move[1];

            if (board.isValidPosition(nextRow, nextCol)) {
                ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
                ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);

                // Add the move if the square is empty or contains an opponent's piece
                if (pieceAtNextPosition == null || pieceAtNextPosition.getTeamColor() != pieceColor) {
                    validMoves.add(new ChessMove(myPosition, nextPosition, null));
                }
            }
        }
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
