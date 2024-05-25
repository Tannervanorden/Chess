package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.AuthData;
import model.UserData;

public class AuthDAO {
    private Map<String, AuthData> auth;

    public AuthDAO() {
        this.auth = new HashMap<>();
    }

    public void addAuth(String token, AuthData authData) {
        auth.put(token, authData);
    }

    public Map<String, AuthData> getAuth() {
        return auth;
    }

    public void removeAuth(String token) {
        auth.remove(token);
    }

    public void clear() {
        this.auth.clear();
    }
}
