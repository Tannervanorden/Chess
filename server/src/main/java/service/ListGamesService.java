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
            Map<String, Object> response = new HashMap<>();
            response.put("games", games);
            return response;
        } catch (Exception e) {
            throw new Exception("Failed to get games");
        }
    }

}
