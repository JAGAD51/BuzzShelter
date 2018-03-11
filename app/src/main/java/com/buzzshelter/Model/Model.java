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

    public HashMap<String, Shelter> getShelterList() {
        return _shelterList;
    }
    public HashMap<String, User> getUserList() {
        return _userList;
    }

    public HashMap<String, Shelter> getFilteredResults(String query) {
        HashMap<String, Shelter> filteredResults = new HashMap<>();
        ArrayList<Shelter> shelters = new ArrayList<>(_shelterList.values());
        for (Shelter shelter : shelters) {
            String restrictions = shelter.getRestrictions();
            if ((query.equals("Families with Newborns")
                    && restrictions.toLowerCase().contains("newborns"))
                    || (query.equals("Children")
                    && restrictions.equals("Children"))
                    || (query.equals("Young Adults")
                    && restrictions.toLowerCase().contains("young adult"))
                    || (query.equals("Any"))) {
                filteredResults.put(shelter.getName(), shelter);
            }
            if ((query.equals("Female") && !restrictions.contains("Men"))
                    || (query.equals("Male") && !restrictions.contains("Women"))
                    || (query.equals("Any"))) {
                filteredResults.put(shelter.getName(), shelter);
            }
            if (shelter.getName().contains(query)) {
                filteredResults.put(shelter.getName(), shelter);
            }
        }
        return filteredResults;
    }
}
