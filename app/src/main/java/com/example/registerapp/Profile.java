package com.example.registerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.registerapp.database.DBContract;
import com.example.registerapp.database.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {
    TextView name, username, email, dob, gender;
    Button editP, deleteP, searchP;
    EditText searchInputs;
    String passUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.tvNameP);
        username = findViewById(R.id.tvUsernameP);
        email = findViewById(R.id.tvEmailP);
        dob = findViewById(R.id.tvDobP);
        gender = findViewById(R.id.tvGenderP);
        editP = findViewById(R.id.btnEditP);
        deleteP = findViewById(R.id.btnDeleteP);
        searchP = findViewById(R.id.btnSearchP);
        searchInputs = findViewById(R.id.etSearchP);

        name.setText("Name\t:");
        username.setText("Username\t:");
        email.setText("Email Address\t:");
        dob.setText("DOB\t:");
        gender.setText("Gender\t:");

        searchP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                List users = new ArrayList<>();
                users = dbHandler.readInfo(searchInputs.getText().toString());

                if(users.isEmpty()){
                    Toast.makeText(Profile.this, "No-one Found!", Toast.LENGTH_SHORT).show();
                    username.setText(null);
                }else {
                    name.setText("Name\t:\t\t"+users.get(0).toString());
                    username.setText("Username\t:\t\t"+users.get(1).toString());
                    email.setText("Email\t:\t\t"+users.get(2).toString());
                    dob.setText("DOB\t:\t\t"+users.get(3).toString());
                    gender.setText("Gender\t:\t\t"+users.get(5).toString());
                    passUsername = users.get(1).toString();
                }

                editP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Edit.class);
                        intent.putExtra("username", passUsername);
                        startActivity(intent);
                    }
                });

                deleteP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBHandler db = new DBHandler(getApplicationContext());
                        db.deleteInfo(searchInputs.getText().toString());

                        name.setText("Name\t:");
                        username.setText("Username\t:");
                        email.setText("Email Address\t:");
                        dob.setText("DOB\t:");
                        gender.setText("Gender\t:");
                    }
                });
            }
        });
    }
}