package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListGamesService extends GenericService {
    private GameDAO gameDAO = GenericService.getGameDAO();
    private AuthDAO authDAO = GenericService.getAuthDAO();

    public Map<String, Object> listGames(String authToken) throws Exception {
        if (!authDAO.validateToken(authToken)) {
            throw new Exception("Unauthorized");
        }

        try {
            Map<Integer, GameData> games = gameDAO.getGames();
            List<Map<String, Object>> gamesList = new ArrayList<>();

            //loop through each game in the gameDAO and add to an array list
            for (GameData game : games.values()) {
                Map<String, Object> gameData = new HashMap<>();
                gameData.put("gameID", game.gameID());
                gameData.put("gameName", game.gameName());
                gameData.put("whiteUsername", game.whiteUsername());
                gameData.put("blackUsername", game.blackUsername());
                gamesList.add(gameData);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("games", gamesList);
            return response;
        } catch (Exception e) {
            throw new Exception("Failed to get game list");
        }
    }

}
