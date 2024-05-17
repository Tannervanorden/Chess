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
        // Initialize the game
        teamTurn = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
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
            ChessBoard copyBoard = new ChessBoard(board);
            ChessPiece capturedPiece = copyBoard.getPiece(move.getEndPosition());
            copyBoard.addPiece(move.getEndPosition(), piece);
            copyBoard.addPiece(startPosition, null);

            // Check if the move puts the current player's king in check
            if (!isInCheckAfterMove(copyBoard, piece.getTeamColor())) {
                validMoves.add(move);
            }

            // Undo move
            copyBoard.addPiece(startPosition, piece);
            copyBoard.addPiece(move.getEndPosition(), capturedPiece);
        }

        return validMoves;
    }

    /**
     * Helper method to determine if the move leaves the player in check.
     */
    private boolean isInCheckAfterMove(ChessBoard tempBoard, TeamColor teamColor) {
        ChessPosition kingPosition = findKingPosition(tempBoard, teamColor);
        return chessChecker(tempBoard,kingPosition,teamColor);
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
            throw new InvalidMoveException("It's not the piece's turn.");
        }

        Collection<ChessMove> validMoves = piece.pieceMoves(board, startPosition);
        // Pawn Promotion
        if (!validMoves.contains(move)) {
            if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                int promotionRow = (teamTurn == TeamColor.WHITE) ? 8 : 1;
                if (endPosition.getRow() != promotionRow) {
                    throw new InvalidMoveException("The move is not valid.");
                }

            } else {
                throw new InvalidMoveException("The move is not valid");
            }
        }

        // Handle pawn promotion and other moves
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
        } else if (!isInCheck(teamTurn)){
            board.addPiece(endPosition, piece);
            board.addPiece(startPosition, null);
        }
        else {
            throw new InvalidMoveException("This move puts your king in check.");
        }

        // Switch turn to the other team
        teamTurn = (teamTurn == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = findKingPosition(board, teamColor);
        return chessChecker(board,kingPosition,teamColor);
    }

    public boolean chessChecker(ChessBoard board, ChessPosition kingPosition, TeamColor teamColor)  {
        // Iterating through all pieces
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

    public ChessPosition findKingPosition(ChessBoard board, TeamColor teamColor) {
        ChessPosition kingPosition = null;
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
        return kingPosition;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // Check if the team is in check
        if (!isInCheck(teamColor)) {
            return false;
        }

        // Iterate through all pieces of the team
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPosition startPosition = new ChessPosition(row + 1, col + 1);
                ChessPiece piece = board.getPiece(startPosition);

                // If the piece belongs to the specified team
                if (piece != null && piece.getTeamColor() == teamColor) {
                    // Check if there are any valid moves for this piece
                    Collection<ChessMove> validMoves = validMoves(startPosition);
                    if (validMoves.isEmpty() && isInCheck(teamColor)) {
                        return true; // Team is not in checkmate if there are valid moves
                    }
                }
            }
        }

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
        // Check if the team is in check
        if (isInCheck(teamColor)) {
            return false;
        }

        return stalemateCheckmate(teamColor);
    }

    //Logic is more different than I thought, so this will only work for stalemate
    public boolean stalemateCheckmate(TeamColor teamColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPosition startPosition = new ChessPosition(row + 1, col + 1);
                ChessPiece piece = board.getPiece(startPosition);

                // If the piece belongs to the correct color
                if (piece != null && piece.getTeamColor() == teamColor) {
                    // Check if there are any valid moves
                    Collection<ChessMove> validMoves = validMoves(startPosition);
                    if (!validMoves.isEmpty()) {
                        return false; // No Valid Moves
                    }
                }
            }
        }
        return true;
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
