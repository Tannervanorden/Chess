package service;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
import service.ListGamesService;

public class ListGamesService {
    private Gson gson = new Gson();;
    private ListGamesService service = new ListGamesService();

    public Object listGames(Request request, Response response) {
        try {
            String authToken = request.headers("authorization");
        }
    }
}
