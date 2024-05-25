package service;

import dataaccess.GameDAO;
import model.GameData;

public class CreateGameService extends GenericService {
    GameDAO gameDAO = GenericService.getGameDAO();

    public void createGame(GameData game) {
        gameDAO.addGame(game);
    }
}
