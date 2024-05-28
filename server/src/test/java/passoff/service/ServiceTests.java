package passoff.service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
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
    void createGamePositive() {
    }

    @Test
    void createGameNegative() {
    }

    @Test
    void joinGamePositive() {
    }

    @Test
    void joinGameNegative() {
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