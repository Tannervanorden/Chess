package server;

import chess.ChessMove;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import dataaccess.*;
import service.GenericService;
import websocket.commands.*;
import com.google.gson.Gson;
import websocket.messages.*;

import java.util.HashMap;
import java.util.Map;

@WebSocket
public class WebSocketServer {
    private Gson serializer = new Gson();
    private MySQLAuthDAO authDAO = GenericService.getAuthDAO();
    private MySQLUserDAO userDAO = GenericService.getUserDAO();
    private MySQLGameDAO gameDAO = GenericService.getGameDAO();

    private Map<String, Session> authTokenSession = new HashMap<>();


    @OnWebSocketMessage
    public void onMessage(Session session, String msg) {
        try {
            UserGameCommand command = serializer.fromJson(msg, UserGameCommand.class);

            // Throws a custom UnauthorizedException. Yours may work differently.
            String username = getUsername(command.getAuthString());

            saveSession(command.getGameID(), session);

            switch (command.getCommandType()) {
                case CONNECT -> connect(session, username, (ConnectCommand) command);
                case MAKE_MOVE -> makeMove(session, username, (MakeMoveCommand) command);
                case LEAVE -> leaveGame(session, username, (LeaveGameCommand) command);
                case RESIGN -> resign(session, username, (ResignCommand) command);
            }
        } catch (UnauthorizedException ex) {
            // Serializes and sends the error message
            sendMessage(session.getRemote(), new ErrorMessage("Error: unauthorized"));
        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
        }
    }

