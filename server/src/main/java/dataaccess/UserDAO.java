package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.UserData;

public class UserDAO {
    private Map<String, UserData> users;

    public UserDAO() {
        this.users = new HashMap<>();
    }

    public UserData getUser(String username){
        return users.get(username);
    }

    public void addUser(UserData userData){
        users.put(userData.username(), userData);
    }

    public Map<String, UserData> getUsers() {
        return users;
    }

    public void clear() {
        this.users.clear();
    }
}
