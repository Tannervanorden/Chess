package service;

import model.AuthData;
import model.UserData;
import dataaccess.UserDAO;
import dataaccess.AuthDAO;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterService extends GenericService {

    public AuthData register(UserData user) throws Exception {

        UserDAO userDAO = GenericService.getUserDAO();
        AuthDAO authDAO = GenericService.getAuthDAO();

        if (userDAO.getUser(user.username()) != null) {
            throw new Exception("Username is already in use");
        }

        //Create a AuthToken
        String token = UUID.randomUUID().toString();

        //save

        users.put(user.username(), user);
        AuthData authData = new AuthData(token, user.username());
        authTokens.put(token, authData);

        return authData;
    }
}
