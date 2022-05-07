package com.example.kotprog_ticket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private NotificationService mNotificationService;
    private FirebaseAuth mAuth;

    EditText firstNameET;
    EditText lastNameET;
    EditText emailAddressET;
    EditText passwordET;
    EditText password2ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNotificationService = new NotificationService(this);

        mAuth = FirebaseAuth.getInstance();

        emailAddressET = findViewById(R.id.reg_email);
        passwordET = findViewById(R.id.reg_password);
        password2ET = findViewById(R.id.reg_password_2);
    }

    public void Close(View view) {
        finish();
    }

    public void RegisterNewAccount(View view) {
        String email = emailAddressET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordConfirm = password2ET.getText().toString();

        if (!password.equals(passwordConfirm)) {
            Toast.makeText(RegisterActivity.this, "The passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                Log.d(LOG_TAG, "User created successfully");
                Toast.makeText(RegisterActivity.this, "Registration was successful. You can now login.", Toast.LENGTH_LONG).show();
            } else {
                Log.d(LOG_TAG, "User wasn't created successfully");
                String errorMessage = Objects.requireNonNull(task.getException()).getMessage();
                Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                //Toast.makeText(RegisterActivity.this, "Password should be longer than 6 characters.", Toast.LENGTH_LONG).show();
            }
        });
    }
}