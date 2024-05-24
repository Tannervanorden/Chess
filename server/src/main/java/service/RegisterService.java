package service;

import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import dataaccess.UserDAO;
import dataaccess.AuthDAO;

import java.util.UUID;

public class RegisterService extends GenericService {

    public AuthData register(UserData user) throws DataAccessException {

        UserDAO userDAO = GenericService.getUserDAO();
        AuthDAO authDAO = GenericService.getAuthDAO();

        if (userDAO.getUser(user.username()) != null) {
            throw new DataAccessException("Username is already in use");
        }

        //Create a AuthToken
        String token = UUID.randomUUID().toString();

        //save
        userDAO.addUser(user);
        AuthData authData = new AuthData(token, user.username());
        authDAO.addAuth(token, authData);


        return authData;
    }
}
