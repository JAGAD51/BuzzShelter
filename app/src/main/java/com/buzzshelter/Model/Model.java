package com.buzzshelter.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 17/02/2018.
 */

public class Model {
private static final Model _instance = new Model();
public static Model getInstance() {return _instance; }


//list of all users
private HashMap<String, User> _userList;

//attempted a null user
//private final User theNullUser = new User("No such user", "No such user", "No such user", AccountType.NA);

    private Model() {
        _userList = new HashMap<>();

    }



    public boolean addUser(User user) {
        if(_userList.containsKey(user)) {
            return false;
        }
        if(_userList == null || user == null) {
            return false;
        }
        _userList.put(user.getId(), user);
        return true;
    }

    public boolean validateUser(String givenId, String password) {
        if(givenId == null || password == null) {
            return false;
        }
        if(_userList.containsKey(givenId)) {
            if (password.equals(_userList.get(givenId).getPassword())) {
                return true;
            } else {
                return false;
            }
        }
        return false; 
    }
}
