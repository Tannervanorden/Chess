package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class LoginService extends GenericService {
    public AuthData login(UserData user) throws Exception {

        MySQLUserDAO userDAO = GenericService.getUserDAO();
        MySQLAuthDAO authDAO = GenericService.getAuthDAO();

        try {
            UserData existingU = userDAO.getUser(user.username());
            if (existingU == null) {
                throw new Exception("User not found");
            }

            if (!existingU.password().equals(user.password())) {
                throw new Exception("Unauthorized");
            }

            String token = UUID.randomUUID().toString();

            AuthData authData = new AuthData(token, user.username());
            authDAO.addAuth(token, authData);
            return authData;

        } catch (Exception e){
            return null;
        }
    }
}
