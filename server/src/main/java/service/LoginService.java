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
            e.printStackTrace();
            return null;
        }
    }
}
