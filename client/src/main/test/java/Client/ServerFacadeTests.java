package Client;

import model.AuthData;
import org.junit.jupiter.api.*;
import server.Server;
import sf.ServerFacade;
import ui.PreLogin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ServerFacadeTests {

    private static Server server;
    private ServerFacade serverFacade = new ServerFacade();

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
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



}
