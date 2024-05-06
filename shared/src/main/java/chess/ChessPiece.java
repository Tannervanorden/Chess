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

        return validMoves;
    }

    private void addDiagonalMove(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition currentPosition, int rowChange, int colChange) {
        int row = currentPosition.getRow();
        int col = currentPosition.getColumn();

        // Iterate through the diagonal until a piece or boundary is reached
        while (true) {
            row += rowChange;
            col += colChange;

            if (!board.isValidPosition(row, col)) {
                break; // Stop if we reach the boundary
            }

            ChessPosition nextPosition = new ChessPosition(row, col);
            ChessPiece nextPiece = board.getPieceAtPosition(nextPosition);

            // If there's no piece at the next position, or the piece is of the opposite color, it's a valid move
            if (nextPiece == null || nextPiece.getTeamColor() != pieceColor) {
                validMoves.add(new ChessMove(currentPosition, nextPosition,null));
            }

            // Stop if there's a piece blocking the diagonal
            if (nextPiece != null) {
                break;
            }
        }
    }

}
