package service;

import dataaccess.GameDAO;
import model.GameData;
import dataaccess.AuthDAO;

public class CreateGameService extends GenericService {
    GameDAO gameDAO = GenericService.getGameDAO();
    AuthDAO authDAO = GenericService.getAuthDAO();

    public void createGame(GameData game, String authToken) {
        if (authDAO.validateToken(authToken)){
            gameDAO.addGame(game);
        }
    }
}
