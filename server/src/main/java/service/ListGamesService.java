package service;

import dataaccess.*;
import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListGamesService extends GenericService {
    MySQLAuthDAO authDAO = GenericService.getAuthDAO();
    MySQLGameDAO gameDAO = GenericService.getGameDAO();

    public Map<String, Object> listGames(String authToken) throws Exception {
        if (!authDAO.validateToken(authToken)) {
            throw new Exception("Unauthorized");
        }

        try {
            Map<Integer, GameData> games = gameDAO.getGames();
            List<GameData> gamesList = new ArrayList<>(games.values());

            Map<String, Object> result = new HashMap<>();
            result.put("games", gamesList);
            return result;
        } catch (Exception e) {
            throw new Exception("Failed to get game list");
        }
    }

}
