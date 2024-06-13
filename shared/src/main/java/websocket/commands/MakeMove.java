package websocket.commands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand {
    private ChessMove move;
    private int gameID;

    public MakeMove(String authToken, int gameID, ChessMove move) {
        super(authToken);
        this.commandType = CommandType.MAKE_MOVE;
        this.gameID = gameID;
        this.move = move;
    }

    public ChessMove getMove() {
        return move;
    }

    public int getGameID() {
        return gameID;
    }
}
