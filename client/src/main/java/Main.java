import chess.*;
import model.AuthData;
import sf.ServerFacade;
import sf.WebSocketClient;
import ui.PostLogin;
import ui.PreLogin;

public class Main {

public static void main(String[] args) {
                try {
                        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
                        System.out.println("♕ 240 Chess Client: " + piece);
                        ServerFacade serverFacade = new ServerFacade();

                        PreLogin preLogin = new PreLogin(serverFacade);
                        AuthData authData = preLogin.displayPreLoginUI();

                        PostLogin postLogin = new PostLogin(authData.authToken(), serverFacade);
                        postLogin.displayPostLoginUI();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}