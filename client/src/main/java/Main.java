import chess.*;
import model.AuthData;
import sf.WebSocketClient;
import ui.PostLogin;
import ui.PreLogin;

public class Main {

        private WebSocketClient webSocket;

public static void main(String[] args) {
                try {
                        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
                        System.out.println("♕ 240 Chess Client: " + piece);

                        PreLogin preLogin = new PreLogin();
                        AuthData authData = preLogin.displayPreLoginUI();

                        WebSocketClient webSocket = new WebSocketClient();

                        PostLogin postLogin = new PostLogin(authData.authToken(), webSocket);
                        postLogin.displayPostLoginUI();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}