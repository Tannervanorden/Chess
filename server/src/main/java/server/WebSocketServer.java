package server;

import chess.ChessMove;
import model.AuthData;
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
                    }
                    case LEAVE -> {
                    }
                    case RESIGN -> {
                    }
                }
            } else {
                sendMessage(session, new ErrorMess());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage(session, new ErrorMess());
        }
    }

    private void saveSession(int gameID, Session session) {
        gameSessions.computeIfAbsent(gameID, k -> new HashSet<>()).add(session);
    }

    private void connect(Session session, String username, Connect command) {
        System.out.println("CONNECT");
        int gameID = command.getGameID();
        saveSession(gameID, session);

        sendMessage(session, new LoadGame());

        sendMessageToOthers(session, gameID, new Notification(username + " has connected."));
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
}
