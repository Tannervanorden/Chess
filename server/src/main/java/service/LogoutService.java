package service;

import dataaccess.AuthDAO;
import dataaccess.MySQLAuthDAO;
import dataaccess.MySQLGameDAO;
import dataaccess.MySQLUserDAO;

public class LogoutService extends GenericService{
    public void logout(String token) throws Exception{
        MySQLAuthDAO authDAO = GenericService.getAuthDAO();

        if (authDAO.removeAuth(token) == null){
            throw new Exception("Token not found");
        }
    }

    public boolean isLoggedIn(String token) throws Exception{
        return authDAO.getAuth().containsKey(token);
    }
}
