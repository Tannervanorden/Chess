package service;

import dataaccess.DataAccessException;
import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import dataaccess.GameDAO;


public class ClearService {
    private GameDAO gameDAO;
    private UserDAO userDAO;
    private AuthDAO authDAO;

    public ClearService() {
        gameDAO.clear();
        userDAO.clear();
        authDAO.clear();
    }

}
