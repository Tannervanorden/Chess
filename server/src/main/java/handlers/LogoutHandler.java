package handlers;

import com.google.gson.Gson;
import service.LogoutService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class LogoutHandler {
    private Gson gson = new Gson();
    private LogoutService logoutService = new LogoutService();

    public Object logout(Request request, Response response) {
        try {
            String authToken = request.headers("authorization");
            if (!logoutService.isLoggedIn(authToken)) {
                response.status(401);
                return gson.toJson(Map.of("message", "Error: Unauthorized"));
            }

            logoutService.logout(authToken);
            response.status(200);
            return gson.toJson(new Object());

        } catch (Exception e) {
            response.status(500);
            return gson.toJson(Map.of("message", "Error: (description of error)"));
        }
    }

}
