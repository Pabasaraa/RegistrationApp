package com.example.registerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
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
    public String CHANNEL_ID = "Register Confirm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Registered", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("This confirms that the user registration process has completed successfully.");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

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
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(Register.this, 1, intent, 0);
                startActivity(intent);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(Register.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("You have registered successfully")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Register.this);
                notificationManagerCompat.notify(0, builder.build());
            }
        });
    }
}