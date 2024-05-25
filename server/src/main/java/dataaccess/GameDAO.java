package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.GameData;

public class GameDAO {
    private Map<Integer, GameData> games;

    public GameDAO() {
        this.games = new HashMap<>();
    }

    public void addGame(GameData game) {
        games.put(game.gameID(), game);
    }

    public Map<Integer,  GameData> getGames() {
        return games;
    }

    public void clear() {
        this.games.clear();
    }
}
