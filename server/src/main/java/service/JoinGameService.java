package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.GameData;

public class JoinGameService extends GenericService {
    private GameDAO gameDAO = GenericService.getGameDAO();
    private AuthDAO authDAO = GenericService.getAuthDAO();

    public GameData joinGame(int id, String authToken) throws Exception {
        if (!authDAO.validateToken(authToken)) {
            throw new Exception("Unauthorized");
        }
        String username = authDAO.getUsername(authToken);
        GameData game = gameDAO.getGame(id);

        if (game == null) {
            throw new Exception("Game not found");
        }

        if (game.whiteUsername() == null){
            game = new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game());
        } else if (game.blackUsername() == null){
            game = new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game());
        } else {
            throw new Exception("Game is already full");
        }
        gameDAO.updateGame(id, game);

        return game;
    }
}
