package sf;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;

public class ServerFacade {
    private String serverUrl = "http://localhost:8080";
    private Gson gson = new Gson();

    //Login
    public AuthData login(String username, String password) throws Exception {
        String endpoint = "/login";
        UserData user = new UserData(username, password, null);
        return sendPostRequest(endpoint, user, AuthData.class);
    }

    //Register
    public AuthData register(String username, String password, String email) throws Exception {
        String endpoint = "/register";
        UserData user = new UserData(username, password, email);
        return sendPostRequest(endpoint, user, AuthData.class);
    }




}
