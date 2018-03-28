package com.buzzshelter.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.Shelter;
import com.example.tonyzhang.buzzshelter.R;

import java.util.ArrayList;

/**
 * Created by jeffr on 2/21/2018.
 */

public class DetailedShelterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedshelter);
        Button backButton = (Button) findViewById(R.id.back);
        Button claimBedsButton = (Button) findViewById(R.id.claimBed);

        //gets the passed-in shelter, retrieves its info, and displays it in the listview
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        final Shelter shelter = (Shelter) Model.getInstance().getShelterList(getApplicationContext()).get(name);
        ArrayList<String> details = new ArrayList<>();
        details.add("Name: " + shelter.getName());
        details.add("Capacity: " + shelter.getCapacity());
        details.add("Restrictions: " + shelter.getRestrictions());
        details.add("Longitude: " + shelter.getLongitude());
        details.add("Latitude: " + shelter.getLatitude());
        details.add("Address: " + shelter.getAddress());
        details.add("Phone Number: " + shelter.getPhoneNumber());
        details.add("Vacancy: " + shelter.getVacancy());
        ListView shelterDetails = (ListView) findViewById(R.id.shelterDetails);
        ArrayAdapter<String> shelterAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, details);
        shelterDetails.setAdapter(shelterAdapter);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ViewShelterActivity.class);
                startActivity(intent);
            }
        });

        claimBedsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent claimBedIntent = new Intent();
                claimBedIntent.setClass(getBaseContext(), ClaimBedActivity.class);
                claimBedIntent.putExtra("shelterName", shelter.getName());
                claimBedIntent.putExtra("shelterVacancy", "" + shelter.getVacancy());
                startActivity(claimBedIntent);
            }
        });
    }
}