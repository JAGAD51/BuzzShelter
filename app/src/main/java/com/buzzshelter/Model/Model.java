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
    private HashMap<String, User> _userList;
    private HashMap<String, Shelter> _shelterList;
    private int loginAttempts = 0;
    private CountDownTimer timer;
    private int mins;
    private int secs;

    //current user using the system
    private User _currentUser;
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
            Toast.makeText(context, "This User ID is taken.", Toast.LENGTH_SHORT).show();

            return false;
        }
        db.createUSER(user);
        db.closeDB();

        Toast.makeText(context,"You are in the db", Toast.LENGTH_SHORT).show();
        return true;

    }

    public boolean validateUser(String givenId, String password, Context context) {
        if(givenId == null || password == null || context == null) {
            System.out.println("imput for validateuser was null");
            return false;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        User temp = db.fetchSpecificUserByID(givenId);

        if(temp != null) {
            System.out.println("\ntemp.getPassword() (from db): " + temp.getPassword());
            System.out.println("\npassword input: " + password);
            System.out.flush();

            if (password.equals(temp.getPassword())) {
                db.closeDB();
                return true;
            } else {
                db.closeDB();
                return false;

            }
        }
        System.out.println("You're not here ;)");
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
        if (query == null || context == null) {
            return null;
        }

            HashMap<String, Shelter> filteredResults = new HashMap<>();
            ArrayList<Shelter> shelters = new ArrayList<>(getShelterList(context).values());
            for (Shelter shelter : shelters) {
                String restrictions = shelter.getRestrictions();
                /**
                System.out.println("restrictions: " + restrictions);
                System.out.println("query is: " + query);
                System.out.println(restrictions.toLowerCase().contains("newborns"));
                 **/
                if ((query.toLowerCase().contains("newborns") && restrictions.toLowerCase().contains("newborns"))
                                || (query.equals("Children") && restrictions.toLowerCase().contains("children"))
                                || (query.equals("Young Adults") && restrictions.toLowerCase().contains("young adult"))
                                || (query.equals("Any"))) {
                    System.out.println("added to filtered results");
                    System.out.flush();
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
