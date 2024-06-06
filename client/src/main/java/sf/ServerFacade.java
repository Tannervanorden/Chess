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


}
