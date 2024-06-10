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
    public void registerUser() throws Exception {
        AuthData register = serverFacade.register("test", "password", "email");
        assertNotNull(register, "Registration should return AuthData");
    }

    @Test
    public void testLogin() throws Exception {
        AuthData login = serverFacade.login("test", "password");
        assertNotNull(login);
    }


}
