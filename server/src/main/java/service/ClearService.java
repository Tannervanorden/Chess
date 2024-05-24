package service;

import dataaccess.DataAccessException;
import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import dataaccess.GameDAO;
import model.AuthData;
import model.UserData;


public class ClearService extends GenericService {

    public ClearService() {
        public void clear() throws DataAccessException {

            UserDAO userDAO = GenericService.getUserDAO();
            AuthDAO authDAO = GenericService.getAuthDAO();
            GameDAO gameDAO = GenericService.getGameDAO();
    }
}
