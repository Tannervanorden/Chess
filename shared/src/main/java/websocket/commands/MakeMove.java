package websocket.commands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand {
    private ChessMove move;

    public MakeMove(String authToken, ChessMove move) {
        super(authToken);
        this.commandType = CommandType.MAKE_MOVE;
        this.move = move;
    }

    public ChessMove getMove() {
        return move;
    }
}
