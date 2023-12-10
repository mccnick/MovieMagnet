package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Get the username from the previous activity
        String username = getIntent().getStringExtra("username");
        boolean isFirstTimeUser = getIntent().getBooleanExtra("isFirstTimeUser", false);

        // Display a welcome message based on whether it's a first-time user or returning user
        TextView welcomeMessage = findViewById(R.id.welcomeMessage);

        if (isFirstTimeUser) {
            // First-time user
            welcomeMessage.setText("Welcome to Movie Magnet, " + username + "!");
        } else {
            // Returning user
            welcomeMessage.setText("Welcome back, " + username + "!");
        }
    }
}
