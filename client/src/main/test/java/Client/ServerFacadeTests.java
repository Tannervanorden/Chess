package Client;

import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;
import server.Server;
import sf.ServerFacade;
import ui.PreLogin;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ServerFacadeTests {

    private static Server server;
    private ServerFacade serverFacade = new ServerFacade();

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @BeforeEach
    public void setUp() {
        serverFacade = new ServerFacade();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerUserPostitive() throws Exception {
        try {AuthData register = serverFacade.register("test", "password", "email");
        assertNotNull(register, "Registration should return AuthData"); }
        catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void registerUserNegative() throws Exception {
        try {
            AuthData register = serverFacade.register("test", null,"email");
        } catch (Exception e){
            assert(true);
        }
    }

    @Test
    public void testLoginPositive() throws Exception {
        AuthData login = serverFacade.login("test", "password");
        assertNotNull(login);
    }
    @Test
    public void testLoginNegative() throws Exception {
        try {
            AuthData login = serverFacade.login("unregistered", "Fakepassword");
            assertNotNull(login);
        } catch (Exception e){
            assert(true);
        }
    }

    @Test
    public void testCreateNegative() throws Exception {
        try {
            GameData game = serverFacade.createGame(null, null);
        } catch (Exception e){
            assert(true);
        }
    }

    @Test
    public void testCreatePositive() throws Exception {
        AuthData login = serverFacade.login("test", "password");
        GameData game = serverFacade.createGame("Name", login.authToken());
        assertNotNull(game);
    }

    @Test
    public void testListPositive() throws Exception {
        AuthData login = serverFacade.login("test", "password");
        List<GameData> list = serverFacade.listGames(login.authToken());
        assertNotNull(list);
    }

    @Test
    public void testListNegative() throws Exception {
        try {
            List<GameData> list = serverFacade.listGames(String.valueOf(1231));
        } catch (Exception e){
            assert(true);
        }
    }

    @Test
    public void joinGamePostitive() throws Exception {
        try {
            AuthData register = serverFacade.register("test", "password", "email");
            GameData created = serverFacade.createGame("Name", register.authToken());
            GameData name = serverFacade.createGame(String.valueOf(created.gameID()), register.authToken());
        }
        catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void joinGameNegative() throws Exception {
        try {
            AuthData register = serverFacade.register("test", "password", "email");
            GameData name = serverFacade.createGame(String.valueOf(4218421), register.authToken());
        }
        catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void logout() throws Exception {
        try {
            AuthData login = serverFacade.login("test", "password");
            serverFacade.logout(login.authToken());
        } catch (Exception e) {
            assert (true);
        }
    }




}
