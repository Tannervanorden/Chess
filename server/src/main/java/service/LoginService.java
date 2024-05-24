package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class LoginService extends GenericService {
    public AuthData login(UserData user) throws Exception {

        UserDAO userDAO = GenericService.getUserDAO();
        AuthDAO authDAO = GenericService.getAuthDAO();

        if (userDAO.getUser(user.username()) == null) {
            throw new Exception("User does not exist");
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
