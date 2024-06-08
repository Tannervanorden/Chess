package sf;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import model.GameData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerFacade {
    private String urlString = "http://localhost:8080";
    private Gson gson = new Gson();

    //Login
    public AuthData login(String username, String password) throws Exception {
        String endpoint = "/login";
        UserData user = new UserData(username, password, null);
        return doPost(endpoint, user, AuthData.class, null);
    }

    //Register
    public AuthData register(String username, String password, String email) throws Exception {
        String endpoint = "/register";
        UserData user = new UserData(username, password, email);
        return doPost(endpoint, user, AuthData.class, null);
    }

    //Create Game
    public GameData createGame(String gameName, String authToken) throws Exception {
        String endpoint = "/games";
        GameData game = new GameData(0, null, null, gameName, null);
        return doPost(endpoint, game, GameData.class, authToken);
    }


    public <T> T doPost(String endpoint, Object requestBody, Class<T> responseClass, String authToken) throws IOException {
        URL url = new URL(urlString);

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

        try(OutputStream requestBodyStream = connection.getOutputStream();) {
            // Write request body to OutputStream ...
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //connection.getHeaderField("Content-Length");

            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
        }
        else {
            // SERVER RETURNED AN HTTP ERROR

            InputStream responseBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
        }
    }
}
