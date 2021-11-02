package com.example.beer4life;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
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

import com.google.android.material.transition.MaterialSharedAxis;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final int COL = 3;
    final int ROW = 5;
    final int DELAY = 700;
    final int MAX_LIVES = 3;


    private ImageButton panel_BTN_button1;
    private ImageButton panel_BTN_button2;
    private ImageView[] panel_IMG_player;
    //private ImageView[][] panel_IMG_beers;
    private Drink[][] panel_Drink;
    private Heart[] panel_IMG_hearts;
    private TextView panel_LBL_score;
    private int[] waterBeerChances = {1,1,0,1,1,0,1,0,1,1};
    private int lives = MAX_LIVES;
    private int score = 0;
    private int addBeerIndex = 0;


    final Handler handler = new Handler();
    private Runnable r = new Runnable() {
        public void run() {
            checkDisqualification();
            updateBeersPos();
            updateScore();
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
        for (int c=0; c < COL; c++) {
            for (int r = 0; r < ROW; r++) {
                if (panel_Drink[r][c].getImg().getVisibility() == View.VISIBLE){
                    panel_Drink[r][c].getImg().setVisibility(View.INVISIBLE);
                    r++;
                    if (r < ROW) {
                        if(panel_Drink[r-1][c].isBeer()) {
                            panel_Drink[r][c].getImg().setImageResource(R.drawable.ic_beer);
                            panel_Drink[r][c].setBeer(true);
                        }
                        else {
                            panel_Drink[r][c].getImg().setImageResource(R.drawable.ic_water_bottle);
                            panel_Drink[r][c].setBeer(false);
                        }
                        panel_Drink[r][c].getImg().setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    private void checkDisqualification() {
        int cur_pos = getCurPos();
        if ((panel_Drink[ROW-1][cur_pos].getImg().getVisibility() == View.VISIBLE) && (panel_Drink[ROW-1][cur_pos].isBeer() == true)) {
            panel_IMG_hearts[lives-1].getImg().setImageResource(R.drawable.ic_empty_heart);
            panel_IMG_hearts[lives-1].setFull(false);
            lives--;

            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(300);
            }
        }

        if (lives == 0) {
            Toast.makeText(this, "Game Over!", Toast.LENGTH_LONG).show();
            SystemClock.sleep(3000);
            System.exit(0);
        }
    }


    private void addRandomBeer() {
        Random r = new Random();

        if(addBeerIndex % 2 == 1) {
            int index = r.nextInt(3);
            int num = r.nextInt(waterBeerChances.length);
            if (waterBeerChances[num] == 1) {
                panel_Drink[0][index].getImg().setImageResource(R.drawable.ic_beer);
                panel_Drink[0][index].getImg().setVisibility(View.VISIBLE);
                panel_Drink[0][index].setBeer(true);
            }
            else {
                panel_Drink[0][index].getImg().setImageResource(R.drawable.ic_water_bottle);
                panel_Drink[0][index].getImg().setVisibility(View.VISIBLE);
                panel_Drink[0][index].setBeer(false);
            }
        }
        addBeerIndex++;
    }


    private void updateScore() {
        for (int column=0; column<COL-1; column++){
            if ((panel_Drink[ROW-1][column].getImg().getVisibility() == View.VISIBLE) && (panel_IMG_player[column].getVisibility() == View.INVISIBLE) && (panel_Drink[ROW-1][column].isBeer() == true))
                score+=50;
            if ((panel_Drink[ROW-1][column].getImg().getVisibility() == View.VISIBLE) && (panel_IMG_player[column].getVisibility() == View.VISIBLE) && (panel_Drink[ROW-1][column].isBeer() == false))
                score+=200;
        }
        panel_LBL_score.setText("" + score);
    }







    @SuppressLint("WrongViewCast")
    private void findViews() {
        panel_BTN_button1 = findViewById(R.id.panel_BTN_button1);
        panel_BTN_button2 = findViewById(R.id.panel_BTN_button2);

        panel_IMG_player = new ImageView[]{
                findViewById(R.id.panel_IMG_player1),
                findViewById(R.id.panel_IMG_player2),
                findViewById(R.id.panel_IMG_player3)
        };

        panel_Drink = new Drink[][] {
                {
                        new Drink().setImg(findViewById(R.id.panel_IMG_row1_beer1)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row1_beer2)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row1_beer3)).setBeer(true)
                },
                {
                        new Drink().setImg(findViewById(R.id.panel_IMG_row2_beer1)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row2_beer2)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row2_beer3)).setBeer(true)
                },
                {
                        new Drink().setImg(findViewById(R.id.panel_IMG_row3_beer1)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row3_beer2)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row3_beer3)).setBeer(true)
                },
                {
                        new Drink().setImg(findViewById(R.id.panel_IMG_row4_beer1)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row4_beer2)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row4_beer3)).setBeer(true)
                },
                {
                        new Drink().setImg(findViewById(R.id.panel_IMG_row5_beer1)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row5_beer2)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row5_beer3)).setBeer(true)
                }
        };

        panel_LBL_score = findViewById(R.id.panel_LBL_score);

        panel_IMG_hearts = new Heart[]{
                new Heart().setImg(findViewById(R.id.panel_IMG_heart1)).setFull(true),
                new Heart().setImg(findViewById(R.id.panel_IMG_heart2)).setFull(true),
                new Heart().setImg(findViewById(R.id.panel_IMG_heart3)).setFull(true)
        };

    }
}