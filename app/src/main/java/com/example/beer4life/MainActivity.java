package com.example.beer4life;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final int COL = 3;
    final int ROW = 3;
    final int DELAY = 500;


    private Button panel_BTN_button1;
    private Button panel_BTN_button2;
    private ImageView[] panel_IMG_player;
    private ImageView[][] panel_IMG_beers;



    final Handler handler = new Handler();
    private Runnable r = new Runnable() {
        public void run() {
            updateBeersPos();
            checkGameOver();
            addRandomBeer();
            handler.postDelayed(r, DELAY);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();




        panel_BTN_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(-1);
            }
        });

        panel_BTN_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(1);
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        startTicker();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTicker();
    }

    private void startTicker() {
        handler.postDelayed(r, DELAY);
    }

    private void stopTicker() {
        handler.removeCallbacks(r);
    }







    private void move(int direction) {
        int cur_pos = getCurPos();
        int new_pos = cur_pos + direction;

        if ((cur_pos != -999) && ((new_pos) >= 0) && (new_pos <= COL-1)){
            panel_IMG_player[cur_pos].setVisibility(View.INVISIBLE);
            panel_IMG_player[new_pos].setVisibility(View.VISIBLE);
        }
    }

    private int getCurPos() {
        for(int i = 0; i < panel_IMG_player.length; i++) {
            if(panel_IMG_player[i].getVisibility() == View.VISIBLE)
                return i;
        }
        return -999;
    }


    // NEED TO UNDERSTAND WHY GAME OVER NOT DETECTED

    private void updateBeersPos() {
        for (int r=0; r < ROW; r++){
            for (int c = 0; c < COL; c++){
                if (panel_IMG_beers[r][c].getVisibility() == View.VISIBLE){
                    panel_IMG_beers[r][c].setVisibility(View.INVISIBLE);
                    c++;
                    if (c < COL)
                        panel_IMG_beers[r][c].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void checkGameOver() {
        int cur_pos = getCurPos();
        if (panel_IMG_beers[ROW-1][cur_pos].getVisibility() == View.VISIBLE){
            Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addRandomBeer() {
        Random r = new Random();
        int index = r.nextInt(3);
        panel_IMG_beers[index][0].setVisibility(View.VISIBLE);
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