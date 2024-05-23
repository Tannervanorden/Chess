package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.AuthData;

public class AuthDAO {
    private Map<String, AuthDAO> auth;

    private AuthDAO() {
        this.auth = new HashMap<>();
    }

    public Map<String, AuthDAO> getAuth() {
        return auth;
    }

    public void clear() {
        this.auth.clear();
    }
}
