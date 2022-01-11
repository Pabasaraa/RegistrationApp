package com.example.registerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.registerapp.database.DBHandler;

public class Register extends AppCompatActivity {
    EditText name, username, email, dob, password;
    RadioButton male, female;
    Button reg;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.etNameR);
        username = findViewById(R.id.etUserNameR);
        email = findViewById(R.id.etEmailR);
        dob = findViewById(R.id.etDobR);
        password = findViewById(R.id.etPasswordR);
        male = findViewById(R.id.rbMaleR);
        female = findViewById(R.id.rbFemaleR);
        reg = findViewById(R.id.btnRegisterR);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(male.isChecked()){
                    gender = "Male";
                }else {
                    gender = "Female";
                }

                DBHandler dbHandler = new DBHandler(getApplicationContext());
                long rowID = dbHandler.addInfo(name.getText().toString(), username.getText().toString(), email.getText().toString(), dob.getText().toString(), password.getText().toString(), gender);
                Toast.makeText(Register.this, "Registered Successfully! ID:"+rowID, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }
}