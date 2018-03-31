package com.buzzshelter.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.Shelter;
import com.example.tonyzhang.buzzshelter.R;

/**
 * Created by jeffr on 2/21/2018.
 */

public class EnterShelterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entershelter);

        final EditText shelterName = findViewById(R.id.enterName);
        final EditText shelterCapacity = findViewById(R.id.enterCapacity);
        final EditText shelterRestrictions = findViewById(R.id.enterRestrictions);
        final EditText shelterLongitude = findViewById(R.id.enterLongitude);
        final EditText shelterLatitude = findViewById(R.id.enterLatitude);
        final EditText shelterAddress = findViewById(R.id.enterAddress);
        final EditText shelterPhone = findViewById(R.id.enterPhone);

        Button backButton = (Button) findViewById(R.id.back);
        Button addButton = (Button) findViewById(R.id.add);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = shelterName.getText().toString();
                String capacity = shelterCapacity.getText().toString();
                String restrictions = shelterRestrictions.getText().toString();
                String longitude = shelterLongitude.getText().toString();
                String latitude = shelterLatitude.getText().toString();
                String address = shelterAddress.getText().toString();
                String phone = shelterPhone.getText().toString();

                Model.getInstance().addShelter(new Shelter(name, capacity, restrictions,
                        longitude, latitude, address, phone), getApplicationContext());

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
