package handlers;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import service.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler  {
    private Gson gson = new Gson();
    private LoginService loginService = new LoginService();

    public Object login(Request request, Response response) {
        try {
            UserData userData = gson.fromJson(request.body(), UserData.class);

            AuthData result = loginService.login(userData);

            return gson.toJson(result);
        } catch (Exception e) {
            response.status(500);
            return gson.toJson(e);
        }
    }
}
