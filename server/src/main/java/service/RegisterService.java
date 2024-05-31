package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class RegisterService extends GenericService {

    public AuthData register(UserData user) throws Exception {

        MySQLUserDAO userDAO = GenericService.getUserDAO();
        MySQLAuthDAO authDAO = GenericService.getAuthDAO();

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
