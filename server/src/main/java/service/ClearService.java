package service;

import dataaccess.*;


public class ClearService extends GenericService {

        public void clear() throws DataAccessException {

            MySQLUserDAO userDAO = GenericService.getUserDAO();
            MySQLAuthDAO authDAO = GenericService.getAuthDAO();
            MySQLGameDAO gameDAO = GenericService.getGameDAO();

            userDAO.clear();
            authDAO.clear();
            gameDAO.clear();

    }
}
