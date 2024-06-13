package server;

import chess.*;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import dataaccess.*;
import service.GenericService;
import websocket.commands.*;
import websocket.messages.*;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebSocket
public class WebSocketServer {
    private Gson serializer = new Gson();
    private MySQLAuthDAO authDAO = GenericService.getAuthDAO();
    private MySQLUserDAO userDAO = GenericService.getUserDAO();
    private MySQLGameDAO gameDAO = GenericService.getGameDAO();

    private Map<Integer, Set<Session>> gameSessions = new HashMap<>();

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) {
        try {
            UserGameCommand command = serializer.fromJson(msg, UserGameCommand.class);
            String authToken = command.getAuthString();
            Map<String, AuthData> authData = authDAO.getAuth();

            if (authToken != null && authDAO.validateToken(authToken)) {
                String username = authData.get(authToken).username();
                UserGameCommand.CommandType type = command.getCommandType();

                switch (type) {
                    case CONNECT -> {
                        Connect connCommand = serializer.fromJson(msg, Connect.class);
                        connect(session, username, connCommand);
                    }
                    case MAKE_MOVE -> {
                        MakeMove makeMoveCommand = serializer.fromJson(msg, MakeMove.class);
                        makeMove(session, username, makeMoveCommand);
                    }
                    case LEAVE -> {
                    }
                    case RESIGN -> {
                    }
                }
            } else {
                sendMessage(session, new ErrorMessage("errorMessage"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage(session, new ErrorMessage("errorMessage"));
        }
    }

    private void saveSession(int gameID, Session session) {
        gameSessions.computeIfAbsent(gameID, k -> new HashSet<>()).add(session);
    }

    private void connect(Session session, String username, Connect command) {
        int gameID = command.getGameID();
        saveSession(gameID, session);
        try {
            GameData gamedata = gameDAO.getGame(gameID);
            ChessGame game = gamedata.game();
            sendMessage(session, new LoadGame(game));
            Notification notification = new Notification(username + " has connected.");
            sendMessageToOthers(session, gameID, notification);
        } catch (Exception e) {
            sendMessage(session, new ErrorMessage("errorMessage"));
        }
    }

    private void makeMove(Session session, String username, MakeMove command) {
        int gameID = command.getGameID();
        try {
            GameData gamedata = gameDAO.getGame(gameID);
            ChessGame game = gamedata.game();
            ChessMove move = command.getMove();
            ChessGame.TeamColor currentTeam = game.getTeamTurn();
            ChessGame.TeamColor currentPlayerColor = getCurrentPlayer(username, gamedata);

            if (currentTeam != currentPlayerColor || currentTeam == null) {
                if (currentTeam == null) {
                    sendMessage(session, new ErrorMessage("You are an observer"));
                    return;
                } else {
                    sendMessage(session, new ErrorMessage("Error, Not your Turn"));
                    return;
                }
            }

            game.makeMove(move);
            gameDAO.updateGame(gameID, gamedata);

            LoadGame updateGame = new LoadGame(game);
            sendMessage(session, updateGame);
            sendMessageToOthers(session, gameID, updateGame);

            Notification moveNotification = new Notification(username + " made a move: " + move);
            sendMessageToOthers(session, gameID, moveNotification);

            if (game.isInCheck((game.getTeamTurn()))){
                Notification checkNotification = new Notification("Check");
                sendMessage(session, checkNotification);
                sendMessageToOthers(session, gameID, checkNotification);
            }
            if (game.isInCheckmate((game.getTeamTurn()))){
                Notification checkMateNotification = new Notification("Checkmate");
                sendMessage(session, checkMateNotification);
                sendMessageToOthers(session, gameID, checkMateNotification);
            }
            if (game.isInStalemate((game.getTeamTurn()))){
                Notification stalemateNotification = new Notification("Stalemate");
                sendMessage(session, stalemateNotification);
                sendMessageToOthers(session, gameID, stalemateNotification);
            }

        } catch (Exception e) {
            sendMessage(session, new ErrorMessage("An error occurred while making the move."));
        }
    }

    private void sendMessage(Session session, ServerMessage message) {
        try {
            session.getRemote().sendString(serializer.toJson(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToOthers(Session rootClientSession, int gameID, ServerMessage message) {
        Set<Session> sessions = gameSessions.get(gameID);
        if (sessions != null) {
            for (Session session : sessions) {
                if (!session.equals(rootClientSession)) {
                    sendMessage(session, message);
                }
            }
        }
    }

    //I think I can get this to not allow observers or invalid players
    private ChessGame.TeamColor getCurrentPlayer(String username, GameData gameData) {
        if (username.equals(gameData.whiteUsername())) {
            return ChessGame.TeamColor.WHITE;
        } else if (username.equals(gameData.blackUsername())) {
            return ChessGame.TeamColor.BLACK;
        }
        // Handle observers
        return null;
    }
}
