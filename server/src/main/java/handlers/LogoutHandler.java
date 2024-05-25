package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.LogoutService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class LogoutHandler {
    private Gson gson = new Gson();
    private LogoutService logoutService = new LogoutService();

    public Object logout(Request request, Response response) {
        try {
            logoutService.logout();
            response.status(200);
            return gson.toJson(new Object());
        } catch (DataAccessException e) {
            response.status(500);
            return gson.toJson(Map.of("message", "Error: (description of error)"));
        }
    }

}
