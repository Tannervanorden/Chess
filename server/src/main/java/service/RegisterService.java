package service;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterService {

    private Map<String, UserData> users = new HashMap<>();
    private Map<String, AuthData> authTokens = new HashMap<>();

    public AuthData register(UserData user) throws Exception {
        if (users.containsKey(userData.username())){
            throw new Exception("Username is already in use");
        }
    }
}
