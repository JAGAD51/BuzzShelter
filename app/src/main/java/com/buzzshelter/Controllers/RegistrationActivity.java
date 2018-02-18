package com.buzzshelter.Controllers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.buzzshelter.Model.AccountType;
import com.buzzshelter.Model.User;
import com.example.tonyzhang.buzzshelter.R;
public class RegistrationActivity extends AppCompatActivity {


    private EditText name;
    private EditText id;
    private EditText password;
    private Spinner accountType;
    private User user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /**
         * Grab the dialog widgets so we can get info
         */
        Button cancel_button = (Button) findViewById(R.id.registration_cancel_button);
        Button register_button = (Button) findViewById(R.id.registration_register_button);
        name = (EditText) findViewById(R.id.registration_name_field);
        id = (EditText) findViewById(R.id.registration_id_field);
        password = (EditText) findViewById(R.id.registration_password_field);
        accountType = (Spinner) findViewById(R.id.registration_accountType_spinner);

        ArrayAdapter<AccountType> accountAdapter = new ArrayAdapter<AccountType>(this, android.R.layout.simple_spinner_item);
        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountType.setAdapter(accountAdapter);


        cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringId = id.getText().toString();
                String stringName =  name.getText().toString();
                String stringPassword = password.getText().toString();
                AccountType accountTypeAccountType =  (AccountType) accountType.getSelectedItem();

                user = new User(stringId ,stringName, stringPassword, accountTypeAccountType);

            }
        });



    }

}
