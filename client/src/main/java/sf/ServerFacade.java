package sf;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import model.GameData;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ServerFacade {
    private String urlString = "http://localhost:8080";
    private Gson gson = new Gson();

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
        return doGet(endpoint, authToken, GameData[].class);
    }


    public <T> T doPost(String endpoint, Object requestBody, Class<T> responseClass, String authToken) throws Exception {
        URL url = new URL(urlString + endpoint);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Set HTTP request headers, if necessary
        // connection.addRequestProperty("Accept", "text/html");
        if (authToken != null) {
            connection.setRequestProperty("Authorization", authToken);
        }

        connection.connect();

        try (OutputStream requestBodyStream = connection.getOutputStream();) {
            String jsonRequest = gson.toJson(requestBody);
            requestBodyStream.write(jsonRequest.getBytes());
            requestBodyStream.flush();
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

            try (InputStream responseStream = connection.getInputStream()) {
                InputStreamReader responseStreamReader = new InputStreamReader(responseStream);
                BufferedReader bufferStream = new BufferedReader(responseStreamReader);

                StringBuilder responseBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = bufferStream.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                return gson.fromJson(responseBuilder.toString(), responseClass);
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
    public <T> T doGet(String endpoint, Class<T> responseClass, String authToken) throws Exception {
        URL url = new URL(urlString + endpoint);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");

        // Set HTTP request headers, if necessary
        // connection.addRequestProperty("Accept", "text/html");
        if (authToken != null) {
            connection.setRequestProperty("Authorization", authToken);
        }

        connection.connect();


        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

            try (InputStream responseStream = connection.getInputStream()) {
                InputStreamReader responseStreamReader = new InputStreamReader(responseStream);
                BufferedReader bufferStream = new BufferedReader(responseStreamReader);

                StringBuilder responseBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = bufferStream.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                return gson.fromJson(responseBuilder.toString(), responseClass);
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
}
