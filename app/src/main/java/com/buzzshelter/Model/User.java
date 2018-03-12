package com.buzzshelter.Model;

/**
 * Created by user on 17/02/2018.
 */

public class User {
    private String _id;
    private String _name;
    private String _password;
    private AccountType _accountType;
    private int _numberBedClaimed;
    private String _locationBedClaimed;


    public User(String id, String name, String password, AccountType accountType) {
        _id = id;
        _name = name;
        _password = password;
        _accountType = accountType;
        _numberBedClaimed = 0;
        _locationBedClaimed = null;
    }

    //Getters
    public String getId() {
        return _id;
    }

    public String getName(){
        return _name;
    }

    public String getPassword() {
        return _password;
    }

    public AccountType getAccountType() {
        return _accountType;
    }

    public int getNumberBedClaimed() {
        return _numberBedClaimed;
    }

    public String getLocationBedClaimed() {
        return _locationBedClaimed;
    }

    //Setters
    public void setId(String newId) {
        _id = newId;
    }
    public void setName(String newName){
        _name = newName;
    }
    public void setPassword(String newPassword) {
        _password = newPassword;
    }
    public void setAccountType(AccountType newAccountType) {
        _accountType = newAccountType;
    }
    public void setNumberBedClaimed(int beds) {
        _numberBedClaimed = beds;
    }
    public void setLocationBedClaimed(String shelterName) {
        _locationBedClaimed = shelterName;
    }
}
