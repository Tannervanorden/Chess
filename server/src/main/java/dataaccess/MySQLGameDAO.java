package dataaccess;

import com.google.gson.Gson;
import chess.ChessGame;
import model.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySQLGameDAO extends GenericDAO {
    private static String tableName = "game";
    private Gson gson = new Gson();

    public MySQLGameDAO() throws DataAccessException {
        configureDatabase(createStatements);
    }

    private final String[] createStatements = {
            "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    "  `gameID` INT PRIMARY KEY," +
                    "  `whiteUsername` VARCHAR(100) NULL," +
                    "  `blackUsername` VARCHAR(100) NULL," +
                    "  `gameName` VARCHAR(100) NULL," +
                    "  `game` TEXT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci"
    };

    public GameData getGame(int gameID) throws DataAccessException {
        String query = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM " + tableName + " WHERE gameID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, gameID);
            try (ResultSet resultSet = statement.executeQuery()) {
                //Check if there even is a result after querying
                if (resultSet.next()) {
                    int id = resultSet.getInt("gameID");
                    String whiteUsername = resultSet.getString("whiteUsername");
                    String blackUsername = resultSet.getString("blackUsername");
                    String gameName = resultSet.getString("gameName");
                    String gameJson = resultSet.getString("game");
                    // Deserialize gson to a ChessGame class
                    ChessGame game = gson.fromJson(gameJson, ChessGame.class);
                    return new GameData(id, whiteUsername, blackUsername, gameName, game);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to get game: " + ex.getMessage());
        }
    }

    public void updateGame(int id, GameData game) throws DataAccessException {
        String query = "UPDATE " + tableName + " SET whiteUsername = ?, blackUsername = ?, gameName = ?, game = ? WHERE gameID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, game.whiteUsername());
            statement.setString(2, game.blackUsername());
            statement.setString(3, game.gameName());
            String gameJson = gson.toJson(game.game());
            statement.setString(4, gameJson);
            statement.setInt(5, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error updating game: " + ex.getMessage());
        }
    }


    public void addGame(GameData game) throws DataAccessException {
        String query = "INSERT INTO " + tableName + " (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, game.gameID());
            statement.setString(2, game.whiteUsername());
            statement.setString(3, game.blackUsername());
            statement.setString(4, game.gameName());
            String gameJson = gson.toJson(game.game());
            statement.setString(5, gameJson);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error adding game: " + ex.getMessage());
        }
    }

    public Map<Integer, GameData> getGames() throws DataAccessException {
        String query = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM " + tableName;
        Map<Integer, GameData> games = new HashMap<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            // Get all Games
            while (resultSet.next()) {
                int id = resultSet.getInt("gameID");
                String whiteUsername = resultSet.getString("whiteUsername");
                String blackUsername = resultSet.getString("blackUsername");
                String gameName = resultSet.getString("gameName");
                String gameJson = resultSet.getString("game");
                // Deserialize gson to a ChessGame class
                ChessGame game = gson.fromJson(gameJson, ChessGame.class);
                GameData gameData = new GameData(id, whiteUsername, blackUsername, gameName, game);
                games.put(id, gameData);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to get games: " + ex.getMessage());
        }
        return games;
    }


}
