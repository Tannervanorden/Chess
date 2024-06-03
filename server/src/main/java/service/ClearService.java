package service;

import dataaccess.*;


public class ClearService extends GenericService {

        public void clear() throws DataAccessException {

            MySQLUserDAO userDAO = GenericService.getUserDAO();
            MySQLAuthDAO authDAO = GenericService.getAuthDAO();
            MySQLGameDAO gameDAO = GenericService.getGameDAO();

            String tableUserName = "user";
            String tableGameName = "game";
            String tableAuthName = "auth";
            userDAO.clear(tableUserName);
            authDAO.clear(tableAuthName);
            gameDAO.clear(tableGameName);

    }
}
