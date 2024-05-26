package service;

import com.google.gson.Gson;
import model.GameData;
import spark.Request;
import spark.Response;
import spark.Route;
import service.ListGamesService;

import java.util.Map;

public class ListGamesService {
    private Gson gson = new Gson();;
    private ListGamesService listGamesService = new ListGamesService();

    public Object listGames(Request request, Response response) {
        try {
            String authToken = request.headers("authorization");

            if (authToken == null) {
                response.status(401);
                return gson.toJson(Map.of("Message", "Error, bad authToken"));
            }

            List<GameData> games = listGamesService.listGames(authToken);
            response.status(200);
            return gson.toJson(games);
        }
    }
}
