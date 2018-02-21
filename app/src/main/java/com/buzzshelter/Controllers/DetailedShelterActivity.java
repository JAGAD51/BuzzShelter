package com.buzzshelter.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.Shelter;
import com.example.tonyzhang.buzzshelter.R;

/**
 * Created by jeffr on 2/21/2018.
 */

public class DetailedShelterActivity extends AppCompatActivity {

    private TextView text = findViewById(R.id.shelterInfo);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedshelter);
        Log.d("Test", "there");
        Button backButton = (Button) findViewById(R.id.back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ViewShelterActivity.class);
                startActivity(intent);
            }
        });

       /* Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Shelter shelter = (Shelter) Model.getInstance().getShelterList().get(name);
        text.setText("Name: " + shelter.getName() + "\n");
        text.append("Capacity: " + shelter.getCapacity() + "\n");*/
    }
}