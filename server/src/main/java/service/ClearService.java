package service;

import dataaccess.DataAccessException;
import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import dataaccess.GameDAO;


public class ClearService {

    public ClearService(GameDAO gameDAO, UserDAO userDAO, AuthDAO authDAO) {
        gameDAO.clear();
        userDAO.clear();
        authDAO.clear();
    }

}
