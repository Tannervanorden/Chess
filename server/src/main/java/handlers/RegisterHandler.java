package handlers;

import com.google.gson.Gson;
import model.UserData;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler {
    private Gson gson = new Gson();
    private RegisterService registerService = new RegisterService();

    public Object register(Request request, Response response) {
        UserData user = gson.fromJson(request.body(), UserData.class);

        var result = RegisterService.register(userData);
        return gson.toJson(result);
    }

}
