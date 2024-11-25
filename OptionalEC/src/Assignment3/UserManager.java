package Assignment3;

import Assignment2.TradableDTO;
import exceptions.DataValidationException;
import exceptions.UsernameFormatIssueException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public final class UserManager {

    private static HashMap<String, User> userCollection = new HashMap<>();
    private static UserManager instance;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }
    private UserManager() {;
    }

    public void init(String[] usersIn) throws UsernameFormatIssueException, DataValidationException {
        for (String user: usersIn) {
            if (user == null || user.equals("")) {
                throw new DataValidationException("String cannot be null or already exist.");
            } else if (!userCollection.containsKey(user)) {
                User u = new User(user);
                userCollection.put(u.getUserId(), u);
            }
        }
    }

    public User getRandomUser() {
        if (userCollection.isEmpty()) {
            return null;
        } else {
            ArrayList<String> strings = new ArrayList<>(userCollection.keySet());
            Collections.shuffle(strings);
            String user = strings.get(0);
            return userCollection.get(user);
        }
    }

    public void addToUser(String userId, TradableDTO o) throws DataValidationException{
        if (userId == null || o == null|| !userCollection.containsKey(userId)) {
            throw new DataValidationException("UserId cannot be null.");
        } else {
            userCollection.get(userId).addTradable(o);
        }
    }

    public User getUser(String id) {
        if (!userCollection.containsKey(id)) {
            return null;
        } else {
            return userCollection.get(id);
        }
    }

    @Override
    public String toString() {
        String string = "";
        for (String s: userCollection.keySet()) {
            string += userCollection.get(s) + "\n";
        }
        return string;
    }
}
