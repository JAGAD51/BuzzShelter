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

    private ArrayList<Shelter> list = new ArrayList<Shelter>(Model.getInstance().getShelterList().values());
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button searchButton;
    private String gender;
    private int selectedId;

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

        loadShelters();

        //create search button and define its functionality
        searchButton = (Button) findViewById(R.id.searchBtn);

        radioGroup = (RadioGroup) findViewById(R.id.radioGender);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    radioButton = (RadioButton) findViewById(selectedId);
                    gender = radioButton.getText().toString();
                    if (gender != null) {
                        list = new ArrayList<Shelter>(Model.getInstance().getFilteredResults(gender).values());
                        loadShelters();
                    }
                }
            }
        });
    }

        private void loadShelters() {
            //shows a list of all the shelters in the database using their toString representation
            ListView shelterList = (ListView) findViewById(R.id.shelterData);
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
