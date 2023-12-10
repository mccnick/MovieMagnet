package com.example.testing05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FloatingActionButton fab = findViewById(R.id.floatingBtn);

        fab.setOnTouchListener(new View.OnTouchListener() {
            float dX;
            float dY;
            float startX;
            float startY;
            int lastAction;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
               switch (event.getActionMasked()) {
                   case MotionEvent.ACTION_DOWN:
                       dX = v.getX() - event.getRawX();
                       dY = v.getY() - event.getRawY();
                       startX = event.getRawX();
                       startY = event.getRawY();
                       lastAction = MotionEvent.ACTION_DOWN;
                       break;
                   case MotionEvent.ACTION_MOVE:
                       v.setY(event.getRawY() + dY);
                       v.setX(event.getRawX() + dX);
                       lastAction = MotionEvent.ACTION_MOVE;
                       break;
                   case MotionEvent.ACTION_UP:
                       if (lastAction == 0) {
                           Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                           startActivity(intent);
                           lastAction = MotionEvent.ACTION_UP;
                       }
                   default:
                       return false;
               }
               return false;
            }
        });
    }
}