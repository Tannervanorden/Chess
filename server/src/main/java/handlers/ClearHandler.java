package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.ClearService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class ClearHandler {
    private Gson gson = new Gson();
    private ClearService clearService = new ClearService();

    public Object clear(Request request, Response response) {
        try {
            clearService.clear();
            response.status(200);
            return gson.toJson(new Object());
        } catch (DataAccessException e) {
            response.status(500);
            return gson.toJson(Map.of("message", "Error: (description of error)"));
        }
    }
}
