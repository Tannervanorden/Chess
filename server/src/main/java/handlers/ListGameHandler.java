package handlers;

import com.google.gson.Gson;
import service.ListGamesService;
import spark.Request;
import spark.Response;

import java.util.Map;

//adding comment to test git push
public class ListGameHandler {
    private Gson gson = new Gson();;
    private ListGamesService listGamesService = new ListGamesService();

    public Object listGames(Request request, Response response) {
        try {
            String authToken = request.headers("authorization");

            if (authToken == null) {
                response.status(401);
                return gson.toJson(Map.of("Message", "Error, bad authToken"));
            }

            Map<String, Object> games = listGamesService.listGames(authToken);
            response.status(200);
            return gson.toJson(games);
        } catch (Exception e) {
            response.status(500);
            return gson.toJson(Map.of("Message", e.getMessage()));
        }
    }
}
