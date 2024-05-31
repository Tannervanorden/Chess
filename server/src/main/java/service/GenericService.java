package service;

import dataaccess.*;

public class GenericService {
    static protected MySQLAuthDAO authDAO;
    static protected MySQLUserDAO userDAO;
    static protected MySQLGameDAO gameDAO;

    static {
        try {
            authDAO = new MySQLAuthDAO();
            System.out.println("Auth table created successfully.");
        } catch (DataAccessException e) {
            System.err.println("Error creating auth table: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            userDAO = new MySQLUserDAO();
            System.out.println("User table created successfully.");
        } catch (DataAccessException e) {
            System.err.println("Error creating user table: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            gameDAO = new MySQLGameDAO();
            System.out.println("Game table created successfully.");
        } catch (DataAccessException e) {
            System.err.println("Error creating game table: " + e.getMessage());
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
