package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.LoginService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class LoginHandler  {
    private Gson gson = new Gson();
    private LoginService loginService = new LoginService();

    public Object login(Request request, Response response) {
        try {
            UserData userData = gson.fromJson(request.body(), UserData.class);
            AuthData result = loginService.login(userData);

            if (result != null) {
                response.status(200);
                return gson.toJson(result);
            }
            else {
                response.status(401);
                return gson.toJson(Map.of("message", "Error: unauthorized" ));
            }
        }
        catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.equals("Unauthorized")){
                response.status(401);
                return gson.toJson(Map.of("message", "Error: unauthorized" ));
            }
            response.status(500);
            return gson.toJson(Map.of("message", "Error: (description of error)"));
        }
    }
}
