package com.example.myapplication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.SecondActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button switchButton = findViewById(R.id.switchButton);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to switch to Screen B
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                // Add some simple text data to be sent to Screen B
                intent.putExtra("message", "Hello from Screen A!");

                // Start Screen B
                startActivity(intent);
            }
        });
    }
}
