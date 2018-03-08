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
private HashMap<String, Shelter> _shelterList;

//attempted a null user
//private final User theNullUser = new User("No such user", "No such user", "No such user", AccountType.NA);

    private Model() {
        _userList = new HashMap<>();
        _shelterList = new HashMap<>();
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
    public boolean addShelter(Shelter shelter) {
        if(_shelterList.containsKey(shelter)) {
            return false;
        }
        if(_shelterList == null || shelter == null) {
            return false;
        }
        _shelterList.put(shelter.getName(), shelter);
        return true;
    }
    public HashMap getShelterList() {
        return _shelterList;
    }

    public HashMap getFilteredResults(String gender) {
        HashMap<String, Shelter> filteredResults = new HashMap<>();
        ArrayList<Shelter> shelters = new ArrayList<>(_shelterList.values());
        for (Shelter shelter : shelters) {
            String restrictions = shelter.getRestrictions();
            if (gender.equals("Female") && restrictions.indexOf("Men") == -1
                || gender.equals("Male") && restrictions.indexOf("Women") == -1
                || gender.equals("Any")) {
                filteredResults.put(shelter.getName(), shelter);
            }
        }
        return filteredResults;
    }
}
