package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.RegisterService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class RegisterHandler {
    private Gson gson = new Gson();
    private RegisterService registerService = new RegisterService();

    public Object register(Request request, Response response) {
        try {
            UserData userData = gson.fromJson(request.body(), UserData.class);

            if(userData.username() == null || userData.password() == null || userData.email() == null) {
                response.status(400);
                return gson.toJson(Map.of("message", "Error: bad request"));
            }
            else {
                AuthData result = registerService.register(userData);
                response.status(200);
                return gson.toJson(result);
            }

        } catch (Exception e) {
            if (e.getMessage().equals("Username is already in use")) {
                response.status(403);
                return gson.toJson(Map.of("message", "Error: username is already in use"));
            }
            else {
                response.status(500);
                return gson.toJson(Map.of("message", "Error: (description of error)"));
            }
        }
    }

}
