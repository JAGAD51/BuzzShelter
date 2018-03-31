package com.buzzshelter.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.buzzshelter.Model.Model;
import com.buzzshelter.DatabaseHelper;
import com.example.tonyzhang.buzzshelter.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button reg_button = (Button) findViewById(R.id.registration_register_button);
        Button login = (Button) findViewById(R.id.go_to_login);
        Button cancel = (Button) findViewById(R.id.cancel);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Model.getInstance().validateUser(username.getText().toString(), password.getText().toString(), getApplicationContext())) {
                    if (Model.getInstance().getLoginAttempts() >= 3) {
                        Toast.makeText(getApplicationContext(), "You are locked out for " + Model.getInstance().failedLogin(),
                                Toast.LENGTH_SHORT).show();
                    } else if (Model.getInstance().validateUser(username.getText().toString(), password.getText().toString(), getApplicationContext())) {

                        Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                        Model.getInstance().setLoginAttempts(0);
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Model.getInstance().failedLogin();
                        if (Model.getInstance().getLoginAttempts() < 3) {
                            Toast.makeText(getApplicationContext(), "The username or password is not correct. You have "
                                    + (3 - Model.getInstance().getLoginAttempts()) + " attempt(s) left.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "You have been locked out for 5 minutes.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
}
