package com.example.beer4life;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    final int COL = 3;
    final int ROWS = 3;


    private Button panel_BTN_button1;
    private Button panel_BTN_button2;
    private ImageView[] panel_IMG_player;
    private ImageView[][] panel_IMG_beers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
    }

    private void findViews() {
        panel_BTN_button1 = findViewById(R.id.panel_BTN_button1);
        panel_BTN_button2 = findViewById(R.id.panel_BTN_button2);

        panel_IMG_player = new ImageView[]{
                findViewById(R.id.panel_IMG_row1_player),
                findViewById(R.id.panel_IMG_row2_player),
                findViewById(R.id.panel_IMG_row3_player)
        };

        panel_IMG_beers = new ImageView[][] {
                {
                        findViewById(R.id.panel_IMG_row1_beer1),
                        findViewById(R.id.panel_IMG_row1_beer2),
                        findViewById(R.id.panel_IMG_row1_beer3)
                },
                {
                        findViewById(R.id.panel_IMG_row2_beer1),
                        findViewById(R.id.panel_IMG_row2_beer2),
                        findViewById(R.id.panel_IMG_row2_beer3)
                },
                {
                        findViewById(R.id.panel_IMG_row3_beer1),
                        findViewById(R.id.panel_IMG_row3_beer2),
                        findViewById(R.id.panel_IMG_row3_beer3)
                }
        };


    }
}