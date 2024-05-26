package service;

import dataaccess.GameDAO;
import model.GameData;
import dataaccess.AuthDAO;

import java.util.UUID;

public class CreateGameService extends GenericService {
    GameDAO gameDAO = GenericService.getGameDAO();
    AuthDAO authDAO = GenericService.getAuthDAO();

    public GameData createGame(GameData game, String authToken) throws Exception {
        if (!authDAO.validateToken(authToken)){
            throw new Exception("Unauthorized");
        }
        try {
            int gameID = UUID.randomUUID().hashCode();
            GameData newGame = new GameData(gameID, null, null, game.gameName(), null);
            gameDAO.addGame(newGame);
            return game;
        } catch (Exception e) {
            throw new Exception("Failed" + e.getMessage());
        }
    }
}
