package com.buzzshelter.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.Shelter;
import com.example.tonyzhang.buzzshelter.R;

public class ClaimBedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_bed);
        TextView shelterNameText = (TextView) findViewById(R.id.bedShelterName);
        final TextView shelterVacancyText = (TextView) findViewById(R.id.bedShelterVacancy);
        final EditText bedsInput = (EditText) findViewById(R.id.bedInput);
        Button backButton = (Button) findViewById(R.id.bedBack);
        Button claimBedButton = (Button) findViewById(R.id.bedClaim);
        Button claimClearButton = (Button) findViewById(R.id.bedClear);

        //load shelter name text
        Intent intent = getIntent();
        final String shelterName = intent.getStringExtra("shelterName");
        final String shelterVacancy = intent.getStringExtra("shelterVacancy");
        shelterNameText.setText(shelterName);
        shelterVacancyText.setText("Vacancies: " + shelterVacancy);

        //back button handler
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent();
                backIntent.setClass(getBaseContext(), DetailedShelterActivity.class);
                backIntent.putExtra("name", shelterName);
                startActivity(backIntent);
            }
        });

        //claim bed button handler
        claimBedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (Model.getInstance().isUserCheckedIn(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Error: You cannot make 2 bed claims.", Toast.LENGTH_SHORT).show();
                    } else {
                        final int numBeds = Integer.parseInt(bedsInput.getText().toString());
                        final Shelter shelter = Model.getInstance().getShelterList(getApplicationContext()).get(shelterName);
                        final int shelterVacancy = shelter.getVacancy();
                        if (numBeds <= shelterVacancy && numBeds > 0) {
                            Model.getInstance().checkIn(numBeds, shelterName, getApplicationContext());
                            Toast.makeText(getApplicationContext(), "Claimed Bed Successfully", Toast.LENGTH_SHORT).show();

                            Intent finishedIntent = new Intent();
                            finishedIntent.setClass(getBaseContext(), DetailedShelterActivity.class);
                            finishedIntent.putExtra("name", shelterName);
                            startActivity(finishedIntent);
                        } else if (numBeds == 0) {
                            Toast.makeText(getApplicationContext(), "Error: Cannot claim 0 bed.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error: Not enough beds.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //clear bed claims
        claimClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Model.getInstance().isUserCheckedIn(getApplicationContext())) {
                    Model.getInstance().checkOut(getApplicationContext());
                shelterVacancyText.setText("Vacancies: " + Model.getInstance().getShelterList(getApplicationContext()).get(shelterName).getVacancy());
                } else {
                    Toast.makeText(getApplicationContext(), "Error: You have no bed claim to clear.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
