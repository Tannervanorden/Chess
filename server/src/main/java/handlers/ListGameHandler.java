package handlers;

import com.google.gson.Gson;
import service.ListGamesService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class ListGameHandler {
    private Gson gson = new Gson();;
    private ListGamesService listGamesService = new ListGamesService();

    public Object listGames(Request request, Response response) {
        try {
            String authToken = request.headers("authorization");

            Map<String, Object> games = listGamesService.listGames(authToken);
            response.status(200);
            return gson.toJson(games);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.equals("Unauthorized")) {
                response.status(401);
                return gson.toJson(Map.of("message", "Error: Unauthorized"));
            }
            response.status(500);
            return gson.toJson(Map.of("message", e.getMessage()));
        }
    }
}
