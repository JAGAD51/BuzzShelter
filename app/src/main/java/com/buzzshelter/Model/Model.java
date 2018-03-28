package com.buzzshelter.Model;

import com.buzzshelter.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.content.Intent;
/**
 * Created by user on 17/02/2018.
 */

public class Model {
    private static final Model _instance = new Model();

    public static Model getInstance() {
        return _instance;
    }

    //list of all users
    private HashMap<String, User> _userList;
    private HashMap<String, Shelter> _shelterList;

    //current user using the system
    private User _currentUser;
    private Model() {

    }

    public boolean addUser(User user, Context context) {
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        if(user == null || context == null || db == null) {
            return false;
        }
        db.createUSER(user);
        db.closeDB();
        return true;

    }

    public boolean validateUser(String givenId, String password, Context context) {
        if(givenId == null || password == null || context == null) {
            return false;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        User temp = db.fetchSpecificUserByID(givenId);

        if(temp != null) {
            if (password.equals(temp.getPassword())) {
                db.closeDB();
                return true;
            } else {
                db.closeDB();
                return false;

            }
        }
        db.closeDB();
        return false; 
    }

    public boolean addShelter(Shelter shelter, Context context) {
        if(shelter == null || context == null) {
            return false;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);

        Shelter temp = db.fetchSpecificShelterByName(shelter.getName());
        if (temp == null) {
            return false;
        }

        db.createSHELTER(shelter);
        return true;
    }

    public HashMap<String, Shelter> getShelterList(Context context) {
        if(context == null) {
            return null;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        HashMap<String, Shelter> map = db.makeShelterHashMap();
        db.closeDB();
        return map;
    }

    public HashMap<String, User> getUserList(Context context) {
        if(context == null) {
            return null;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        HashMap<String, User> map = db.makeUserHashMap();
        db.closeDB();
        return map;
    }

    public HashMap<String, Shelter> getFilteredResults(String query, Context context) {
        if(query == null || context == null) {
            return null;
        }
        HashMap<String, Shelter> filteredResults = new HashMap<>();
        ArrayList<Shelter> shelters = new ArrayList<>(getShelterList(context).values());
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

    public boolean isUserCheckedIn(Context context) {
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        String location = db.fetchSpecificUserByID(_currentUser.getId()).getLocationBedClaimed();
        db.closeDB();
        return location != null;
    }

    public boolean checkIn(int numBeds, String shelterName, Context context) {
        if(shelterName == null || context == null) {
            return false;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        _currentUser.setNumberBedClaimed(numBeds);
        _currentUser.setLocationBedClaimed(shelterName);
        Shelter shelter = _shelterList.get(shelterName);
        shelter.setVacancy(shelter.getVacancy() - numBeds);
        db.updateSHELTER(shelter);
        db.updateUSER(_currentUser);
        db.closeDB();
        return false;


    }

    public boolean checkOut(Context context) {
        if (context == null) {
            return false;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);

        Shelter shelter = _shelterList.get(_currentUser.getLocationBedClaimed());
        shelter.setVacancy(shelter.getVacancy() + _currentUser.getNumberBedClaimed());
        _currentUser.setNumberBedClaimed(0);
        _currentUser.setLocationBedClaimed(null);
        db.updateSHELTER(shelter);
        db.updateUSER(_currentUser);
        db.closeDB();
        return true;
    }
}
