package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard board;

    public ChessGame() {
        // Initialize the game with default settings
        teamTurn = TeamColor.WHITE; // By default, white team starts first
        board = new ChessBoard(); // Initialize the chessboard
        board.resetBoard(); // Set up the default starting board
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) {
            return null;
        }

        Collection<ChessMove> possibleMoves = piece.pieceMoves(board, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();

        for (ChessMove move : possibleMoves) {
            // Make the move on a temporary board to check if it results in a check
            ChessBoard tempBoard = new ChessBoard(board);
            ChessPiece capturedPiece = tempBoard.getPiece(move.getEndPosition());
            tempBoard.addPiece(move.getEndPosition(), piece);
            tempBoard.addPiece(startPosition, null);

            // Check if the move puts the current player's king in check
            if (!isInCheckAfterMove(tempBoard, piece.getTeamColor(), move)) {
                validMoves.add(move);
            }

            // Revert the move on the tempBoard
            tempBoard.addPiece(startPosition, piece);
            tempBoard.addPiece(move.getEndPosition(), capturedPiece);
        }

        return validMoves;
    }

    /**
     * Helper method to determine if the move leaves the player in check.
     */
    private boolean isInCheckAfterMove(ChessBoard tempBoard, TeamColor teamColor, ChessMove move) {
        // Find the king's position after the move
        ChessPosition kingPosition = null;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = tempBoard.getPiece(new ChessPosition(row + 1, col + 1));
                if (piece != null && piece.getTeamColor() == teamColor && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = new ChessPosition(row + 1, col + 1);
                    break;
                }
            }
            if (kingPosition != null) {
                break;
            }
        }

        // Check if any opponent's piece can move to the king's position
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = tempBoard.getPiece(new ChessPosition(row + 1, col + 1));
                if (piece != null && piece.getTeamColor() != teamColor) {
                    Collection<ChessMove> moves = piece.pieceMoves(tempBoard, new ChessPosition(row + 1, col + 1));
                    for (ChessMove possibleMove : moves) {
                        if (possibleMove.getEndPosition().equals(kingPosition)) {
                            return true; // King is in check
                        }
                    }
                }
            }
        }

        return false; // King is not in check
    }




    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();

        ChessPiece piece = board.getPiece(startPosition);

        if (piece == null) {
            throw new InvalidMoveException("There is no piece at the starting position.");
        }

        if (piece.getTeamColor() != teamTurn) {
            throw new InvalidMoveException("It's not the turn for the piece's team to move.");
        }

        Collection<ChessMove> validMoves = piece.pieceMoves(board, startPosition);
        if (!validMoves.contains(move)) {
            if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                int promotionRow = (teamTurn == TeamColor.WHITE) ? 8 : 1;
                if (endPosition.getRow() != promotionRow) {
                    throw new InvalidMoveException("The move is not valid for the piece.");
                }
                // Code for handling pawn promotion can go here, if needed.
            } else {
                throw new InvalidMoveException("The move is not valid for the piece.");
            }
        }


        // Capture the piece at the end position if any
        ChessPiece capturedPiece = board.getPiece(endPosition);

        // Handle pawn promotion
        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            int promotionRow = (teamTurn == TeamColor.WHITE) ? 8 : 1;
            if (endPosition.getRow() == promotionRow) {
                ChessPiece.PieceType promotionPieceType = move.getPromotionPiece();
                if (promotionPieceType == null) {
                    throw new InvalidMoveException("Pawn promotion must specify a piece type.");
                }
                ChessPiece promotedPiece = new ChessPiece(teamTurn, promotionPieceType);
                board.addPiece(endPosition, promotedPiece);
                board.addPiece(startPosition, null);
            } else {
                board.addPiece(endPosition, piece);
                board.addPiece(startPosition, null);
            }
        } else {
            board.addPiece(endPosition, piece);
            board.addPiece(startPosition, null);
        }

        // Check for check, checkmate, and stalemate
        if (isInCheck(teamTurn)) {
            // Revert the move
            board.addPiece(startPosition, piece);
            board.addPiece(endPosition, capturedPiece);
            throw new InvalidMoveException("This move puts your king in check.");
        }

        // Switch turn to the other team
        teamTurn = (teamTurn == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;

        // Additional logic for checkmate and stalemate can be added here
    }





    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = null;

        // First, find the king's position
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board.getPiece(new ChessPosition(row + 1, col + 1));
                if (piece != null && piece.getTeamColor() == teamColor && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = new ChessPosition(row + 1, col + 1);
                    break;
                }
            }
            if (kingPosition != null) {
                break;
            }
        }

        // Check if any opponent's piece can move to the king's position
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board.getPiece(new ChessPosition(row + 1, col + 1));
                if (piece != null && piece.getTeamColor() != teamColor) {
                    Collection<ChessMove> moves = piece.pieceMoves(board, new ChessPosition(row + 1, col + 1));
                    for (ChessMove move : moves) {
                        if (move.getEndPosition().equals(kingPosition)) {
                            return true; // King is in check
                        }
                    }
                }
            }
        }
        return false; // King is not in check
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }
}
