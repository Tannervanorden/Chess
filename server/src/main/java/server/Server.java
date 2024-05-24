package server;

import handlers.ClearHandler;
import handlers.LoginHandler;
import handlers.RegisterHandler;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", ((request, response) -> (new RegisterHandler()).register(request, response)));
        Spark.post("/session", ((request, response) -> (new LoginHandler()).login(request, response)));
        Spark.delete("/db", ((request, response) -> (new ClearHandler()).clear(request, response)));

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
