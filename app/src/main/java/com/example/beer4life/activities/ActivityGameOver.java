package com.example.beer4life.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beer4life.Heart;
import com.example.beer4life.R;

import java.util.Random;

public class ActivityGameOver extends AppCompatActivity {
    private Heart[] panel_IMG_hearts;
    private TextView panel_LBL_score;
    private ImageView panel_IMG_wasted;
    private TextView panel_LBL_quote;
    private ImageButton panel_BTN_retry;
    private ImageButton panel_BTN_quit;

    private String[] quotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        findViews();
        setQuote();

        panel_BTN_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry();
            }
        });

        panel_BTN_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit();
            }
        });
    }

    private void retry() {
        Intent intent = new Intent(this, ActivityGame.class);
        startActivity(intent);
    }

    private void quit() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private void setQuote() {
        quotes = new String[]{
                "\"Our hearts were drunk with a beauty Our eyes could never see.\" \n - George William Russell",
                "\"Sometimes when you're drunk you can see better.\" \n - Damien Hirst",
                "\"An intelligent man is sometimes forced to be drunk to spend time with his fools.\" \n - Ernest Hemingway",
                "\"It's okay saying sorry, but when you are drunk you say what you really feel.\" \n - Vidal Sassoon",
                "\"You're not drunk if you can lie on the floor without holding on.\" \n - Dean Martin",
                "\"I'm like the drunk in the bar who wants just one more for the road.\" \n - Archie Moore",
                "\"The best audience is intelligent, well-educated and a little drunk.\" \n - Alben W. Barkley"
        };

        Random rand = new Random();
        int index = rand.nextInt(quotes.length);
        panel_LBL_quote.setText(quotes[index]);
    }


    private void findViews() {
        panel_LBL_score = findViewById(R.id.panel_LBL_score);

        panel_IMG_hearts = new Heart[]{
                new Heart().setImg(findViewById(R.id.panel_IMG_heart1)).setFull(false),
                new Heart().setImg(findViewById(R.id.panel_IMG_heart2)).setFull(false),
                new Heart().setImg(findViewById(R.id.panel_IMG_heart3)).setFull(false)
        };

        panel_LBL_quote = findViewById(R.id.panel_LBL_quote);
        panel_IMG_wasted = findViewById(R.id.panel_IMG_wasted);

        panel_BTN_retry = findViewById(R.id.panel_BTN_retry);
        panel_BTN_quit = findViewById(R.id.panel_BTN_quit);
    }
}