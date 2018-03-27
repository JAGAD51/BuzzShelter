package com.buzzshelter.Model;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private int loginAttempts = 0;
    private CountDownTimer timer;
    private int mins;
    private int secs;

    //current user using the system
    private User _currentUser;

    private Model() {
        _userList = new HashMap<>();
        _shelterList = new HashMap<>();
        _currentUser = null;
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
                _currentUser = _userList.get(givenId);
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

    public int getLoginAttempts() { return loginAttempts; }

    public void setLoginAttempts(int num) { loginAttempts = num; }

    public String failedLogin() {
        loginAttempts++;
        if (loginAttempts == 3) {
            timer = new CountDownTimer(300000, 1000) {
                @Override
                public void onTick(long time) {
                    secs = (int) (time / 1000);
                    mins = secs / 60;
                    secs = secs % 60;
                }

                @Override
                public void onFinish() {
                    loginAttempts = 0;
                }
            }.start();
        }
        return "" + mins + " minutes and " + secs + " seconds.";
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
            if (shelter.getName().toLowerCase().contains(query)) {
                filteredResults.put(shelter.getName(), shelter);
            }
        }
        return filteredResults;
    }

    public boolean isUserCheckedIn() {
        return _currentUser.getLocationBedClaimed() != null;
    }

    public void checkIn(int numBeds, String shelterName) {
        _currentUser.setNumberBedClaimed(numBeds);
        _currentUser.setLocationBedClaimed(shelterName);
        Shelter shelter = _shelterList.get(shelterName);
        shelter.setVacancy(shelter.getVacancy() - numBeds);
    }

    public void checkOut() {
        Shelter shelter = _shelterList.get(_currentUser.getLocationBedClaimed());
        shelter.setVacancy(shelter.getVacancy() + _currentUser.getNumberBedClaimed());
        _currentUser.setNumberBedClaimed(0);
        _currentUser.setLocationBedClaimed(null);
    }
}
