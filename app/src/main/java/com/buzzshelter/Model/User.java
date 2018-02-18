package com.buzzshelter.Model;

/**
 * Created by user on 17/02/2018.
 */

public class User {
    private String _id;
    private String _name;
    private String _password;
    private AccountType _accountType;


    public User(String id, String name, String password, AccountType accountType) {
        _id = id;
        _name = name;
        _password = password;
        _accountType = accountType;
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

    //Setters
    public void setId(String newId) {
        _id = newId;
    }

    public void setName(String newName){
        _name = newName;
    }
    public void getPassword(String newPassword) {
        _password = newPassword;
    }

    public void getAccountType(AccountType newAccountType) {
        _accountType = newAccountType;
    }
}
