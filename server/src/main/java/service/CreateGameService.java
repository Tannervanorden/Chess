package service;

import dataaccess.*;
import model.GameData;

import java.util.UUID;

public class CreateGameService extends GenericService {
    MySQLAuthDAO authDAO = GenericService.getAuthDAO();
    MySQLGameDAO gameDAO = GenericService.getGameDAO();

    public GameData createGame(GameData game, String authToken) throws Exception {
        String gameName = game.gameName();
        if (!authDAO.validateToken(authToken)){
            throw new Exception("Unauthorized");
        }
        try {
            int gameID = Math.abs(UUID.randomUUID().hashCode());
            GameData newGame = new GameData(gameID, null, null, gameName, null);
            gameDAO.addGame(newGame);
            return newGame;
        } catch (Exception e) {
            throw new Exception("Failed" + e.getMessage());
        }
    }
}
