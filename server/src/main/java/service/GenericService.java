package service;

import dataaccess.*;

public class GenericService {
    static protected MySQLAuthDAO authDAO;
    static protected MySQLUserDAO userDAO;
    static protected MySQLGameDAO gameDAO;

    static {
        try {
            authDAO = new MySQLAuthDAO();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        try {
            userDAO = new MySQLUserDAO();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        try {
            gameDAO = new MySQLGameDAO();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public static MySQLAuthDAO getAuthDAO() {
        return authDAO;
    }

    public static MySQLUserDAO getUserDAO() {
        return userDAO;
    }

    public static MySQLGameDAO getGameDAO() {
        return gameDAO;
    }
}
