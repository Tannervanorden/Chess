package handlers;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler {
    private Gson gson = new Gson();
    private RegisterService registerService = new RegisterService();

    public Object register(Request request, Response response) {
        try {
            UserData userData = gson.fromJson(request.body(), UserData.class);

            AuthData result = registerService.register(userData);

            return gson.toJson(result);
        } catch (Exception e) {
            response.status(500);
            return gson.toJson(e);
        }
    }

}
