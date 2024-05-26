package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.GameData;

import javax.xml.crypto.Data;

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

    public Map<Integer,  GameData> getGames() {
        return games;
    }

    public void clear() {
        this.games.clear();
    }
}
