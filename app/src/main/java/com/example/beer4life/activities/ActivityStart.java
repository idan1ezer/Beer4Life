package com.example.beer4life.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beer4life.R;
import com.example.beer4life.callbacks.CallBack_Settings;
import com.example.beer4life.fragment.FragmentSettings;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityStart extends AppCompatActivity {
    private ImageView panel_IMG_drunk_game;
    private TextView panel_LBL_task;
    private ImageButton panel_BTN_start;
    private ImageButton panel_BTN_settings;
    private int dif;
    private boolean sen;

    private FragmentSettings fragmentSettings;


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

        panel_BTN_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFragmentSettings();
            }
        });
    }


    private void initFragmentSettings() {
        fragmentSettings = new FragmentSettings();
        fragmentSettings.setActivity(this);
        fragmentSettings.setCallBackSettings(callBack_settings);
        getSupportFragmentManager().beginTransaction().add(R.id.panel_frameSettings, fragmentSettings).commit();
    }

    CallBack_Settings callBack_settings = new CallBack_Settings() {
        @Override
        public void setSettings(int difficulty, boolean sensors) {
            dif = difficulty;
            sen = sensors;

            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    };



    private void start() {
        finish();
        Intent intent = new Intent(this, ActivityGame.class);
        intent.putExtra("dif", dif);
        intent.putExtra("sen", sen);
        startActivity(intent);
    }

    private void findViews() {
        panel_IMG_drunk_game = findViewById(R.id.panel_IMG_drunk_game);
        panel_LBL_task = findViewById(R.id.panel_LBL_task);
        panel_BTN_start = findViewById(R.id.panel_BTN_start);
        panel_BTN_settings = findViewById(R.id.panel_BTN_settings);
    }
}