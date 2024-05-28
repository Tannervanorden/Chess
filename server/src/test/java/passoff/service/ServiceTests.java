package passoff.service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.GameData;
import org.junit.jupiter.api.Test;
import service.*;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private AuthDAO authDAO;
    private GameDAO gameDAO;

    private ClearService clearService;
    private CreateGameService createGameService;
    private JoinGameService joinGameService;
    private ListGamesService listGamesService;
    private LoginService loginService;
    private LogoutService logoutService;
    private RegisterService registerService;


    @Test
    void clearPositive() {
        clearService = new ClearService();
        assertDoesNotThrow(clearService::clear);
    }

    @Test
    void createGamePositive() throws Exception {
        CreateGameService createGameService = new CreateGameService();
        String authToken = "authToken";
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(1, "whiteUser", "blackUser", "game1", game);
        assertNotNull(gameData);

    }

    @Test
    void createGameNegative() {
        CreateGameService createGameService = new CreateGameService();
        String authToken = "invalidAuthToken";  // Provide an invalid auth token
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(1, "whiteUser", "blackUser", "game1", game);

        assertThrows(Exception.class, () -> {
            createGameService.createGame(gameData, authToken);
        });
    }

    @Test
    void joinGamePositive() {
        GameData gameData1 = new GameData(1, null, null, null, null);
        GameData gameData2 = new GameData(1, null, null, null, null);

        assertEquals(gameData1, gameData2);
    }

    @Test
    void joinGameNegative() {
        GameData gameData1 = new GameData(1, null, null, null, null);
        GameData gameData2 = new GameData(2, null, null, null, null);

        assertNotEquals(gameData1, gameData2);
    }

    @Test
    void listGamesPositive() {
    }

    @Test
    void listGamesNegative() {
    }

    @Test
    void loginPositive() {
    }

    @Test
    void loginNegative() {
    }

    @Test
    void logoutPositive() {
    }

    @Test
    void logoutNegative() {
    }

    @Test
    void registerPositive() {
    }

    @Test
    void registerNegative() {
    }
}