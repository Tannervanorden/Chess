package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.UserData;

public class UserDAO {
    private static UserDAO database;
    private Map<String, UserData> users;

    private UserDAO() {
        this.users = new HashMap<>();
    }


    public Map<String, UserData> getUsers() {
        return users;
    }

    public void clear() {
        this.users.clear();
    }
}
