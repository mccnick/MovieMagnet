package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegistrationActivity extends AppCompatActivity {

    EditText user, email, password, birthday; // Add birthday EditText
    Button registerButton;
    SharedPreferences sharedPreferences; // SharedPreferences for storing user data

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        user = findViewById(R.id.user);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        birthday = findViewById(R.id.birthday); // Initialize birthday EditText
        registerButton = findViewById(R.id.registerButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get the entered email, password, username, and birthday as strings
                String enteredUser = user.getText().toString();
                String enteredEmail = email.getText().toString();
                String enteredPassword = password.getText().toString();
                String enteredBirthday = birthday.getText().toString(); // Get birthday input

                // Check if the entered email is valid, username is more than 3 characters, and birthday is not empty
                if (isValidEmail(enteredEmail) && enteredUser.length() > 3 && !enteredBirthday.isEmpty()) {
                    // Store user data in SharedPreferences (in practice, use secure storage)
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", enteredUser);
                    editor.putString("password", enteredPassword);
                    editor.putString("birthday", enteredBirthday); // Store birthday
                    editor.apply();

                    // Show a success message
                    Toast.makeText(RegistrationActivity.this, "Account successfully created", Toast.LENGTH_SHORT).show();

                    // Redirect to the login page
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Invalid email, username, or birthday, show an error message
                    Toast.makeText(RegistrationActivity.this, "Please enter valid email, username (more than 3 characters), and birthday", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Function to validate an email address using regular expression
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
