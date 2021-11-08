package com.example.beer4life.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beer4life.Drink;
import com.example.beer4life.Heart;
import com.example.beer4life.MyService;
import com.example.beer4life.R;

import java.util.Random;

public class ActivityStart extends AppCompatActivity {
    private ImageView panel_IMG_drunk_game;
    private TextView panel_LBL_task;
    private ImageButton panel_BTN_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViews();

        panel_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }



    private void start() {
        Intent intent = new Intent(this, ActivityGame.class);
        startActivity(intent);
    }

    private void findViews() {
        panel_IMG_drunk_game = findViewById(R.id.panel_IMG_drunk_game);
        panel_LBL_task = findViewById(R.id.panel_LBL_task);
        panel_BTN_start = findViewById(R.id.panel_BTN_start);
    }
}