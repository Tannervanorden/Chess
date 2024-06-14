package sf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.AuthData;
import model.JoinGameRequest;
import model.UserData;
import model.GameData;
import ui.GamePlay;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ServerFacade {
    int port;
    private String urlString = "http://localhost:8080";
    private Gson gson = new Gson();
    private WebSocketClient webSocketClient;
    private GamePlay gamePlay;

    public ServerFacade() {
        try {
            this.webSocketClient = new WebSocketClient(gamePlay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Login
    public AuthData login(String username, String password) throws Exception {
        String endpoint = "/session";
        UserData user = new UserData(username, password, null);
        return doPost(endpoint, user, AuthData.class, null);
    }

    //Register
    public AuthData register(String username, String password, String email) throws Exception {
        String endpoint = "/user";
        UserData user = new UserData(username, password, email);
        return doPost(endpoint, user, AuthData.class, null);
    }

    //Create Game
    public GameData createGame(String gameName, String authToken) throws Exception {
        String endpoint = "/game";
        GameData game = new GameData(0, null, null, gameName, null);
        return doPost(endpoint, game, GameData.class, authToken);
    }

    public List<GameData> listGames(String authToken) throws Exception {
        String endpoint = "/game";
        Map<String, List<GameData>> response = doGet(endpoint, new TypeToken<Map<String, List<GameData>>>(){}.getType(), authToken);
        if (response.containsKey("games")) {
            return response.get("games");
        } else {
            throw new Exception("Response does not contain 'games' key.");
        }
    }

    public void logout(String authToken) throws Exception {
        String endpoint = "/session";
        doDelete(endpoint, authToken);
    }

    public GameData joinGame(long gameID, String color, String authToken) throws Exception {
        String endpoint = "/game";
        JoinGameRequest joiningGameData;
        if (color.equals("White")) {
            joiningGameData = new JoinGameRequest("WHITE", gameID);
        } else {
            joiningGameData = new JoinGameRequest("BLACK", gameID);
        }
        return doPut(endpoint, joiningGameData, GameData.class, authToken);
    }

    public GameData observeGame(long gameID, String authToken) throws Exception {
        String endpoint = "/game";
        JoinGameRequest joiningGameData = new JoinGameRequest(null, gameID);
        return doPut(endpoint, joiningGameData, GameData.class, authToken);
    }

    private HttpURLConnection createConnection(String endpoint, String requestMethod, String authToken) throws IOException {
        URL url = new URL(urlString + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod(requestMethod);
        connection.setDoOutput(!requestMethod.equals("GET"));

        // Set HTTP request headers, if necessary
        // connection.addRequestProperty("Accept", "text/html");
        if (authToken != null) {
            connection.setRequestProperty("Authorization", authToken);
        }

        return connection;
    }

    private <T> T getResponse(HttpURLConnection connection, Class<T> responseClass, Type responseType) throws Exception {
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (InputStream responseStream = connection.getInputStream()) {
                InputStreamReader responseStreamReader = new InputStreamReader(responseStream);
                BufferedReader bufferStream = new BufferedReader(responseStreamReader);

                StringBuilder responseBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = bufferStream.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                if (responseBuilder.toString().equals("{}")) {
                    return null;
                }
                return gson.fromJson(responseBuilder.toString(), responseClass != null ? responseClass : responseType);
            }
        } else {
            // SERVER RETURNED AN HTTP ERROR
            try (InputStream responseBodyStream = connection.getErrorStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(responseBodyStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    responseBody.append(line);
                }

                throw new Exception(responseBody.toString());
            }
        }
    }

    public <T> T doPut(String endpoint, Object requestBody, Class<T> responseClass, String authToken) throws Exception {
        HttpURLConnection connection = createConnection(endpoint, "PUT", authToken);

        if (!(requestBody == "")) {
            try (OutputStream requestBodyStream = connection.getOutputStream();) {
                String json = gson.toJson(requestBody);
                requestBodyStream.write(json.getBytes());
                requestBodyStream.flush();
            }
        }

        return getResponse(connection, responseClass, null);
    }

    public void doDelete(String endpoint, String authToken) throws Exception {
        HttpURLConnection connection = createConnection(endpoint, "DELETE", authToken);
        getResponse(connection, null, null);
    }

    public <T> T doGet(String endpoint, Type responseType, String authToken) throws Exception {
        HttpURLConnection connection = createConnection(endpoint, "GET", authToken);
        return getResponse(connection, null, responseType);
    }


    public <T> T doPost(String endpoint, Object requestBody, Class<T> responseClass, String authToken) throws Exception {
        HttpURLConnection connection = createConnection(endpoint, "POST", authToken);

        if (!requestBody.equals("")) {
            try (OutputStream requestBodyStream = connection.getOutputStream();) {
                String jsonRequest = gson.toJson(requestBody);
                requestBodyStream.write(jsonRequest.getBytes());
                requestBodyStream.flush();
            }
        }

        return getResponse(connection, responseClass, null);
    }
}

