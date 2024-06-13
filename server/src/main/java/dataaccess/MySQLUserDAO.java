package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLUserDAO extends GenericDAO{
    private static String tableName = "user";

    public MySQLUserDAO() throws DataAccessException {
        configureDatabase(createStatements);
    }

    private final String[] createStatements = {
            "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    " username VARCHAR(200) PRIMARY KEY," +
                    " password VARCHAR(200) NOT NULL," +
                    " email VARCHAR(200) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci"
    };

    public UserData getUser(String username) throws DataAccessException {
        String query = "SELECT * FROM " + tableName + " WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement statement = conn.prepareStatement(query)) {
        statement.setString(1, username);
        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));

            }
        }
        } catch (SQLException ex) {
            throw new DataAccessException("Error" + ex.getMessage());
        }
        return null;
    }

    public void addUser(UserData user) throws DataAccessException {
        String query = "INSERT INTO " + tableName + "(username, password, email) VALUES (?,?,?)";
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, user.username());
            statement.setString(2, hashedPassword);
            statement.setString(3, user.email());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error: can't add user" + ex.getMessage());
        }
    }
}
