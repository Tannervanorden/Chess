package service;

import dataaccess.DataAccessException;
import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import dataaccess.GameDAO;


public class ClearService extends GenericService {

        public void clear() throws DataAccessException {

            UserDAO userDAO = GenericService.getUserDAO();
            AuthDAO authDAO = GenericService.getAuthDAO();
            GameDAO gameDAO = GenericService.getGameDAO();

            userDAO.clear();
            authDAO.clear();
            gameDAO.clear();

    }
}
