package com.buzzshelter.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.InputStream;
import java.util.List;

import com.buzzshelter.Model.AccountType;
import com.buzzshelter.Model.CSVFile;
import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.Shelter;
import com.buzzshelter.Model.User;
import com.example.tonyzhang.buzzshelter.R;

public class WelcomeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //creates a default user (for convenience's sake)
        User user = new User("bob", "bob", "bob", AccountType.ADMIN);
        Model.getInstance().addUser(user);

        //reads in the default set of homeless shelters and adds them to the model
        InputStream inputStream = getResources().openRawResource(R.raw.stats);
        CSVFile csvFile = new CSVFile(inputStream);
        List shelterList = csvFile.read();
        for(int i = 1; i < shelterList.size(); i++) {
            String[] data = (String[]) shelterList.get(i);
            String name = data[1];
            String capacity = data[2];
            String restrictions = data[3];
            String longitude = data[4];
            String latitude = data[5];
            String address = data[6];
            String phone = data[8];
            Model.getInstance().addShelter(new Shelter(name, capacity, restrictions,
                    longitude, latitude, address, phone));
        }

        //for button to redirect to login screen
        Button button = (Button) findViewById(R.id.go_to_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //for button to redirect to login screen
        Button reg_button = (Button) findViewById(R.id.go_to_register);
        reg_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

}
