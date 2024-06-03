package server;

import dataaccess.DataAccessException;
import dataaccess.MySQLAuthDAO;
import dataaccess.MySQLGameDAO;
import dataaccess.MySQLUserDAO;
import handlers.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", ((request, response) -> (new RegisterHandler()).register(request, response)));
        Spark.post("/session", ((request, response) -> (new LoginHandler()).login(request, response)));
        Spark.post("/game", ((request, response) -> (new CreateGameHandler()).createGame(request, response)));
        Spark.get("/game", ((request, response) -> (new ListGameHandler()).listGames(request, response)));
        Spark.delete("/session", ((request, response) -> (new LogoutHandler()).logout(request, response)));
        Spark.delete("/db", ((request, response) -> (new ClearHandler()).clear(request, response)));
        Spark.put("/game", ((request, response) -> (new JoinGameHandler()).joinGame(request, response)));

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
