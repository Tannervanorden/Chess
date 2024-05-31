package service;

import dataaccess.*;
import model.GameData;

public class JoinGameService extends GenericService {
    MySQLAuthDAO authDAO = GenericService.getAuthDAO();
    MySQLGameDAO gameDAO = GenericService.getGameDAO();

    public GameData joinGame(int id, String playerColor, String authToken) throws Exception {
        if (!authDAO.validateToken(authToken)) {
            throw new Exception("Unauthorized");
        }
        String username = authDAO.getUsername(authToken);
        GameData game = gameDAO.getGame(id);

        if (username == null) {
            throw new Exception("Unauthorized");
        }

        if (game == null) {
            throw new Exception("Game not found");
        }
        if (playerColor == null) {
            throw new Exception("Invalid Player Color");
        }

        if (playerColor.equals("BLACK")){
            if (game.blackUsername() != null){
                throw new Exception("Error: already taken");
            }
        }
        if (playerColor.equals("WHITE")){
            if (game.whiteUsername() != null){
                throw new Exception("Error: already taken");
            }
        }


        if (playerColor.equals("WHITE")){
            game = new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game());
        } else if (playerColor.equals("BLACK")){
            game = new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game());
        } else {
            throw new Exception("Game is already full");
        }
        gameDAO.updateGame(id, game);

        return game;
    }
}
