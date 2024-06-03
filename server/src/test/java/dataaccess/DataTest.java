package dataaccess;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataTest {
    private MySQLAuthDAO authDAO;
    private MySQLGameDAO gameDAO;
    private MySQLUserDAO userDAO;


    @BeforeEach
    public void setUp() {
        try {
            authDAO = new MySQLAuthDAO();
            authDAO.clear();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        try {
            userDAO = new MySQLUserDAO();
            userDAO.clear();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        try {
            gameDAO = new MySQLGameDAO();
            gameDAO.clear();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    public void testAddAuthPositive() throws DataAccessException {
        AuthData authData = new AuthData("token1", "user1");
        authDAO.addAuth("token1", authData);
        assertEquals(authData, authDAO.getAuth().get("token1"));
    }

    @Test
    @Order(2)
    public void testAddAuthNegative() throws DataAccessException {
        AuthData authData = new AuthData("token1", "user1");
        authDAO.addAuth("token1", authData);
        assertNull(authDAO.getAuth().get("token2"));
    }


    @Test
    @Order(3)
    public void testValidateAuthPositive() throws DataAccessException {
        AuthData authData = new AuthData("token1", "user1");
        authDAO.addAuth("token1", authData);
        assertTrue(authDAO.validateToken("token1"));
    }
    @Test
    @Order(4)
    public void testValidateAuthNegative() throws DataAccessException {
        AuthData authData = new AuthData("token1", "user1");
        authDAO.addAuth("token1", authData);
        assertFalse(authDAO.validateToken("token2"));
    }
    @Test
    @Order(5)
    public void testClear() throws DataAccessException {
        AuthData authData = new AuthData("token1", "user1");
        authDAO.addAuth("token1", authData);
        authDAO.clear();
        assertNull(authDAO.getAuth().get("token1"));
    }
    @Test
    @Order(6)
    public void testGetAuthPositive() throws DataAccessException {
        AuthData authData = new AuthData("token", "user");
        authDAO.addAuth("token", authData);
        assertEquals(authData, authDAO.getAuth().get("token"));

    }

    @Test
    @Order(7)
    public void testGetAuthNegative() throws DataAccessException {
        AuthData authData = new AuthData("token", "user");
        authDAO.addAuth("token", authData);
        assertNotNull(authDAO);

    }

    @Test
    @Order(8)
    public void testRemoveAuthPositive() throws DataAccessException {
        AuthData authData = new AuthData("token", "user");
        authDAO.addAuth("token", authData);
        authDAO.removeAuth("token");
        assertNull(authDAO.getAuth().get("token"));
    }

    @Test
    @Order(9)
    public void testRemoveAuthNegative() throws DataAccessException {
        AuthData authData = new AuthData("token", "user");
        authDAO.addAuth("token", authData);
        authDAO.removeAuth("token1");
        assertNotNull(authDAO.getAuth().get("token"));
    }

    @Test
    @Order(10)
    public void testGetUsernamePostive() throws DataAccessException {
        AuthData authData = new AuthData("token", "user");
        authDAO.addAuth("token", authData);
        String username = authDAO.getUsername("token");
        assertNotNull(username);
    }

    @Test
    @Order(11)
    public void testGetUsernamenNegative() throws DataAccessException {
        AuthData authData = new AuthData("token", "user");
        authDAO.addAuth("token", authData);
        String username = authDAO.getUsername("tokenFake");
        assertNull(username);
    }



    @Test
    @Order(12)
    public void testAddGamePositive() throws DataAccessException {
        GameData game = new GameData(1, "whiteUsername", "blackUsermane", "test", null);
        gameDAO.addGame(game);
        GameData checkGame = gameDAO.getGame(1);
        assertEquals(game, checkGame);
    }

    @Test
    @Order(13)
    public void testAddGameNegative() throws DataAccessException {
        GameData game = new GameData(1, "whiteUsername", "blackUsermane", "test", null);
        GameData game2 = new GameData(2, "whiteUsername", "blackUsermane", "test", null);
        gameDAO.addGame(game);
        gameDAO.addGame(game2);
        GameData checkGame = gameDAO.getGame(2);
        assertNotEquals(game, checkGame);
    }

    @Test
    @Order(14)
    public void testGetGamePositive() throws DataAccessException {
        GameData game = new GameData(1, "whiteUsername", "blackUsermane", "test", null);
        gameDAO.addGame(game);
        GameData checkGame = gameDAO.getGame(1);
        assertEquals(game, checkGame);
    }

    @Test
    @Order(15)
    public void testGetGameNegative() throws DataAccessException {
        GameData game = new GameData(1, "whiteUsername", "blackUsermane", "test", null);
        GameData game2 = new GameData(2, "whiteUsername", "blackUsermane", "test", null);
        gameDAO.addGame(game);
        gameDAO.addGame(game2);
        assertNotEquals(gameDAO.getGame(1), gameDAO.getGame(2));
    }





}
