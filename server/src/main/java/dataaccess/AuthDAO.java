package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.AuthData;

public class AuthDAO {
    private Map<String, AuthData> auth;

    public AuthDAO() {
        this.auth = new HashMap<>();
    }

    public void addAuth(String token, AuthData authData) {
        auth.put(token, authData);
    }

    public boolean validateToken(String token) {
        return auth.containsKey(token);
    }

    public Map<String, AuthData> getAuth() {
        return auth;
    }

    public String getUsername(String token) {
        AuthData authData = auth.get(token);
        if (authData == null) {
            return null;
        }
        return authData.username();
    }

    public AuthData removeAuth(String token) {
        return auth.remove(token);
    }

    public void clear() {
        this.auth.clear();
    }
}
