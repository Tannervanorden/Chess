package service;

import dataaccess.AuthDAO;

public class LogoutService extends GenericService{
    public void logout(String token) throws Exception{
        AuthDAO authDAO = GenericService.getAuthDAO();

        if (authDAO.removeAuth(token) == null){
            throw new Exception("Token not found");
        }
    }
}
