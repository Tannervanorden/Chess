package handlers;

import com.google.gson.Gson;
import model.GameData;
import spark.Request;
import spark.Response;
import service.CreateGameService;
import java.util.Map;

public class CreateGameHandler {
    private Gson gson = new Gson();
    private CreateGameService createGameService = new CreateGameService();

    public Object createGame(Request request, Response response) {
        try {
            GameData game = gson.fromJson(request.body(), GameData.class);
            String authToken = request.headers("authorization");

            if (game.gameName() == null) {
                response.status(400);
                return gson.toJson(Map.of("message", "Error; Invalid Request"));
            }

            GameData result = createGameService.createGame(game, authToken);
            response.status(200);
            return gson.toJson(result);
        } catch (Exception e) {
            if (e.getMessage().contains("Unauthorized")) {
                response.status(401);
                return gson.toJson(Map.of("message", "Error; unauthorized"));
            }
            response.status(500);
            return gson.toJson(Map.of("message", e.getMessage()));
        }
    }
}
