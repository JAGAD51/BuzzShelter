package com.buzzshelter.Model;
import com.buzzshelter.DatabaseHelper;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by user on 17/02/2018.
 */

public class Model {
    private static final Model _instance = new Model();

    public static Model getInstance() {
        return _instance;
    }

    //list of all users
    //private HashMap<String, User> _userList;
    //private HashMap<String, Shelter> _shelterList;
    private int loginAttempts = 0;
    private CountDownTimer timer;
    private int mins;
    private int secs;
    User _currentUser = null;
    //current user using the system

    private Model() {


    }

    public boolean addUser(User user, Context context) {
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        System.out.println("\n\n\n" + db.toString());
        if(user == null || context == null || db == null) {
            Toast.makeText(context,"null is bad", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(db.fetchSpecificUserByID(user.getId()) != null) {
            Toast.makeText(context, "This User ID is in the database.", Toast.LENGTH_SHORT).show();
            return false;
        }
        db.createUSER(user);
        db.closeDB();

        Toast.makeText(context,"You are in the db", Toast.LENGTH_SHORT).show();
        return true;

    }

    public boolean validateUser(String givenId, String password, Context context) {
        if(givenId == null || password == null || context == null) {
            return false;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        _currentUser = db.fetchSpecificUserByID(givenId);

        if(_currentUser != null) {
            if (password.equals(_currentUser.getPassword())) {
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
            System.out.println("shelter context came in false");
            return false;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        Shelter temp = db.fetchSpecificShelterByName(shelter.getName());
        if (temp != null) {
            System.out.println("shelter already here");
            return false;
        }

        db.createSHELTER(shelter);
        return true;
    }

    public HashMap<String, Shelter> getShelterList(Context context) {
        if(context == null) {
            System.out.println("shelter context is null :(");
            return null;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        HashMap<String, Shelter> map = db.makeShelterHashMap();
        db.closeDB();
        System.out.println("SHELTER MAP MADE :)");
        for (String s: map.keySet()) {
            System.out.println(s);
        }
        System.out.println(map.keySet().size());
        System.out.flush();
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

    public HashMap<String, Shelter> getFilteredResults(String query, Context context) {
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
        Shelter shelter = db.fetchSpecificShelterByName(shelterName);
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

        Shelter shelter = db.fetchSpecificShelterByName(_currentUser.getLocationBedClaimed());
        shelter.setVacancy(shelter.getVacancy() + _currentUser.getNumberBedClaimed());
        _currentUser.setNumberBedClaimed(0);
        _currentUser.setLocationBedClaimed(null);
        db.updateSHELTER(shelter);
        db.updateUSER(_currentUser);
        db.closeDB();
        return true;
    }
}
