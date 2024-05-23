package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.AuthData;
import model.GameData;
import model.UserData;



public class Database {
    private static Database database;
    private Map<String, UserData> users;
    private Map<Integer, GameData> games;
    private Map<String, AuthData> authTokens;

    private Database() {
        this.users = new HashMap<>();
        this.games = new HashMap<>();
        this.authTokens = new HashMap<>();
    }

    public Map<String, UserData> getUsers() {
        return users;
    }



}
