package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.GameData;

public class GameDAO {
    private Map<Integer, GameData> games;

    public GameDAO() {
        this.games = new HashMap<>();
    }

    public void addGame(GameData game) throws DataAccessException {
        if (games.containsKey(game.gameID())){
            throw new DataAccessException("Game already exists");
        }
        games.put(game.gameID(), game);
    }
    public void updateGame(int id, GameData game){
        games.put(game.gameID(), game);
    }
    public GameData getGame(int gameID) throws DataAccessException {
        return games.get(gameID);
    }


    public Map<Integer,  GameData> getGames() {
        return games;
    }

    public void clear() {
        this.games.clear();
    }
}
