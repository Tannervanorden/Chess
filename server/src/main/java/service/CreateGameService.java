package service;

import dataaccess.GameDAO;
import model.GameData;
import dataaccess.AuthDAO;

public class CreateGameService extends GenericService {
    GameDAO gameDAO = GenericService.getGameDAO();
    AuthDAO authDAO = GenericService.getAuthDAO();

    public GameData createGame(GameData game, String authToken) throws Exception {
        if (!authDAO.validateToken(authToken)){
            throw new Exception("Unauthorized");
        }
        try {
            gameDAO.addGame(game);
            return game;
        } catch (Exception e) {
            throw new Exception("Failed" + e.getMessage());
        }
    }
}
