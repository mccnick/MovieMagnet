package com.example.testing04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button logInBtn;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logInBtn = (Button) findViewById(R.id.button);
        username = findViewById(R.id.Text);


        logInBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String str = username.getText().toString();
                Intent intent = new Intent (MainActivity.this, MainActivity2.class);
                intent.putExtra("username", str);
                startActivity(intent);
            }
        });
    }
}