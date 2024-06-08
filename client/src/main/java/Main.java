import chess.*;
import model.AuthData;
import ui.PostLogin;
import ui.PreLogin;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        PreLogin preLogin = new PreLogin();
        AuthData authData = preLogin.displayPreLoginUI();
        PostLogin postLogin = new PostLogin(authData.authToken());

    }
}