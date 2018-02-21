package com.buzzshelter.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.Shelter;
import com.example.tonyzhang.buzzshelter.R;

import java.util.ArrayList;

/**
 * Created by jeffr on 2/21/2018.
 */

public class ViewShelterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelterview);

        Button backButton = (Button) findViewById(R.id.back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //shows a list of all the shelters in the database using their toString representation
        ListView shelterList = (ListView) findViewById(R.id.shelterData);
        ArrayList<Shelter> list = new ArrayList<Shelter>(Model.getInstance().getShelterList().values());
        ArrayAdapter<Shelter> shelterAdapter =
                new ArrayAdapter<Shelter>(this, android.R.layout.simple_list_item_1, list);
        shelterList.setAdapter(shelterAdapter);
    }
}
