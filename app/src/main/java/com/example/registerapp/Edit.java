package com.example.registerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.registerapp.database.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class Edit extends AppCompatActivity {
    EditText etName, etEmail, etPassword, etDob;
    Button btnEdit;
    TextView etUsername;
    RadioButton male, female;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        String username = intent.getExtras().getString("username");

        etName = findViewById(R.id.etNameEP);
        etUsername = findViewById(R.id.etUsernameEP);
        etEmail = findViewById(R.id.etEmailEP);
        etPassword = findViewById(R.id.etPasswordEP);
        etDob = findViewById(R.id.etDobEP);
        btnEdit = findViewById(R.id.btnEditEP);
        male = findViewById(R.id.rbMaleEP);
        female = findViewById(R.id.rbFemaleEP);

        DBHandler dbHandler = new DBHandler(getApplicationContext());
        List userInfo = new ArrayList<>();

        userInfo = dbHandler.readInfo(username);
        etName.setText(userInfo.get(0).toString());
        etUsername.setText(userInfo.get(1).toString());
        etEmail.setText(userInfo.get(2).toString());
        etPassword.setText(userInfo.get(4).toString());
        etDob.setText(userInfo.get(3).toString());
        gender = userInfo.get(5).toString();

        if(gender.equals("Male")) {
            male.setChecked(true);
        }else {
            female.setChecked(true);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (male.isChecked()){
                    gender = "Male";
                }else{
                    gender = "Female";
                }
                DBHandler db = new DBHandler(getApplicationContext());
                boolean status = db.updateInfo(etName.getText().toString(), etUsername.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString(), etDob.getText().toString(), gender);

                if (status) {
                    Toast.makeText(Edit.this, "Information Updated Successfully!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Profile.class);
                    startActivity(i);
                }else {
                    Toast.makeText(Edit.this, "Couldn't Complete", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}