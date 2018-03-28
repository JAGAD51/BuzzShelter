package com.buzzshelter.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.Shelter;
import com.example.tonyzhang.buzzshelter.R;

import java.util.ArrayList;

/**
 * Created by jeffr on 2/21/2018.
 */

public class ViewShelterActivity extends AppCompatActivity implements OnItemClickListener {


    private ArrayList<Shelter> list;
    private RadioGroup radioGroup;
    private RadioGroup radioAge;
    private RadioButton radioButton;
    private RadioButton ageButton;
    private Button searchButton;
    private Button backButton;
    private Button mapViewButton;
    private String gender;
    private String age;
    private String name;
    private int selectedId;
    private int secondId;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelterview);

        //Button backButton = (Button) findViewById(R.id.back);

//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
       list = new ArrayList<>(Model.getInstance().getShelterList(getApplicationContext()).values());

        System.out.println("SHELTER LIST MADE IN ACTIVITY");
        for (Shelter s : list) {
            System.out.println(s);

        }
        loadShelters();

        backButton = findViewById(R.id.backButton);
        //create search button and define its functionality
        searchButton = findViewById(R.id.searchBtn);

        mapViewButton = findViewById(R.id.mapView);

        radioGroup = findViewById(R.id.radioGender);
        radioAge = findViewById(R.id.radioAge);
        search = findViewById(R.id.textBox);

        //method for searching based on id when button is clicked
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = radioGroup.getCheckedRadioButtonId();
                secondId = radioAge.getCheckedRadioButtonId();
                name = search.getText().toString();
                if (!name.isEmpty()) {
                    list = new ArrayList<>(Model.getInstance().getFilteredResults(name, getApplicationContext()).values());
                    loadShelters();
                } else if (selectedId != -1 && secondId != -1) {
                    radioButton = findViewById(selectedId);
                    ageButton = findViewById(secondId);

                    gender = radioButton.getText().toString();
                    age = ageButton.getText().toString();

                    if (gender != null) {
                        list = new ArrayList<>(Model.getInstance().getFilteredResults(gender, getApplicationContext()).values());
                        for (Shelter s: list) {
                            System.out.println(s);

                        }

                    }
                    if (age != null) {
                        list.retainAll(Model.getInstance().getFilteredResults(age, getApplicationContext()).values());
                        for (Shelter s: list) {
                            System.out.println(s);

                        }
                    }

                    for (Shelter s: list) {
                        System.out.println(s);

                    }
                    loadShelters();
                } else if (selectedId != -1) {
                    radioButton = findViewById(selectedId);
                    gender = radioButton.getText().toString();
                    if (gender != null) {
                        list = new ArrayList<>(Model.getInstance().getFilteredResults(gender, getApplicationContext()).values());
                    }
                    loadShelters();
                } else if (secondId != -1) {
                    ageButton = findViewById(secondId);
                    age = ageButton.getText().toString();
                    if (age != null) {
                        list = new ArrayList<>(Model.getInstance().getFilteredResults(age, getApplicationContext()).values());
                        loadShelters();
                    }
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mapViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                String[] listNames = new String[list.size()];
                int i = 0;
                for (Shelter shelter : list) {
                    listNames[i] = shelter.getName();
                    i++;
                }
                intent.putExtra("list", listNames);
                startActivity(intent);
            }
        });
    }

        private void loadShelters() {

            //shows a list of all the shelters in the database using their toString representation
            ListView shelterList = findViewById(R.id.shelterData);
            ArrayAdapter<Shelter> shelterAdapter =
                    new ArrayAdapter<Shelter>(this, android.R.layout.simple_list_item_1, list);
            shelterList.setAdapter(shelterAdapter);
            shelterList.setOnItemClickListener(this);
        }

        //displays detailed info about a shelter if you click on it
        public void onItemClick(AdapterView<?> l, View v, int position, long id) {
            Intent intent = new Intent();
            intent.setClass(this, DetailedShelterActivity.class);
            intent.putExtra("name", list.get(position).getName());
            startActivity(intent);
        }
}
