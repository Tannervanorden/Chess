package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.GameData;

public class GameDAO {
    private Map<Integer, GameDAO> games;

    public GameDAO() {
        this.games = new HashMap<>();
    }

    public Map<Integer,  GameDAO> getGames() {
        return games;
    }

    public void clear() {
        this.games.clear();
    }
}
