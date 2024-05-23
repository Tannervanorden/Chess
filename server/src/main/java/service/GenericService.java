package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class GenericService {
    static protected AuthDAO authDAO = new AuthDAO();
    static protected UserDAO userDAO = new UserDAO();
    static protected GameDAO gameDAO = new GameDAO();

    public static AuthDAO getAuthDAO() {
        return authDAO;
    }

    public static void setAuthDAO(AuthDAO authDAO) {
        GenericService.authDAO = authDAO;
    }

    public static UserDAO getUserDAO() {
        return userDAO;
    }

    public static void setUserDAO(UserDAO userDAO) {
        GenericService.userDAO = userDAO;
    }

    public static GameDAO getGameDAO() {
        return gameDAO;
    }

    public static void setGameDAO(GameDAO gameDAO) {
        GenericService.gameDAO = gameDAO;
    }
}
