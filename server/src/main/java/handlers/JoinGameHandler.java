package handlers;

import com.google.gson.Gson;
import model.GameData;
import service.JoinGameService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class JoinGameHandler {
    private Gson gson = new Gson();
    private JoinGameService joinGameService = new JoinGameService();

     public Object joinGame(Request request, Response response) {
         try {
             String authToken = request.headers("authorization");
             GameData requestBody = gson.fromJson(request.body(), GameData.class);

             if (authToken == null || authToken.isEmpty()) {
                 response.status(401);
                 return gson.toJson(Map.of("message", "Error: unauthorized"));
             }

             int id = requestBody.gameID();
             GameData game = joinGameService.joinGame(id, authToken);
         }
     }

}
