package service;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private ClearService clearService;


    @Test
    void clearPositive() {
        clearService = new ClearService();
        assertDoesNotThrow(clearService::clear);
    }

    @Test
    void createGamePositive() {
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
        ListGamesService listGamesService = new ListGamesService();
        assertNotNull(listGamesService);
    }

    @Test
    void listGamesNegative() {
        ListGamesService listGamesService = new ListGamesService();
        ListGamesService listGamesService1 = new ListGamesService();
        assertNotEquals(listGamesService1, listGamesService);
    }

    @Test
    void loginPositive() {
        LoginService loginService = new LoginService();
        assertNotNull(loginService);
    }

    @Test
    void loginNegative() {
        LoginService loginService = new LoginService();
        LoginService loginService1 = new LoginService();
        assertNotEquals(loginService, loginService1);
    }

    @Test
    void logoutPositive() {
        LogoutService LogoutService = new LogoutService();
        assertNotNull(LogoutService);
    }

    @Test
    void logoutNegative() {
        LogoutService LogoutService = new LogoutService();
        LogoutService LogoutService1 = new LogoutService();
        assertNotEquals(LogoutService, LogoutService1);
    }

    @Test
    void registerPositive() {
        RegisterService registerService = new RegisterService();
        assertNotNull(registerService);
    }

    @Test
    void registerNegative() {
        RegisterService registerService = new RegisterService();
        RegisterService registerService1 = new RegisterService();
        assertNotEquals(registerService, registerService1);
    }
}