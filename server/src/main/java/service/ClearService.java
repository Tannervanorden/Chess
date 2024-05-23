package service;

import dataaccess.DataAccessException;
import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import dataaccess.GameDAO;


public class ClearService {
    private GameDAO gameDAO;
    private UserDAO userDAO;
    private AuthDAO authDAO;

    public ClearService(GameDAO gameDAO, UserDAO userDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        gameDAO.clear();
        userDAO.clear();
        authDAO.clear();
    }

}
