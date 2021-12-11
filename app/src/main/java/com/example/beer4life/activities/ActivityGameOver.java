package com.example.beer4life.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beer4life.generalObjects.CompareScores;
import com.example.beer4life.generalObjects.Heart;
import com.example.beer4life.R;
import com.example.beer4life.generalObjects.MSP;
import com.example.beer4life.generalObjects.MyDB;
import com.example.beer4life.generalObjects.Score;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ActivityGameOver extends AppCompatActivity {
    private Heart[] panel_IMG_hearts;
    private TextView panel_LBL_score;
    private ImageView panel_IMG_wasted;
    private TextView panel_LBL_quote;
    private ImageButton panel_BTN_retry;
    private ImageButton panel_BTN_quit;
    private ImageButton panel_BTN_top10;

    private String[] quotes;
    private double lat;
    private double lon;
    private int score;

    private MyDB myDB;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        findViews();
        setQuote();
        initBTNs();
        loadFromSP();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lat = extras.getDouble("lat");
            lon = extras.getDouble("lon");
            score = extras.getInt("score");
        }
        if (myDB == null)
            myDB = new MyDB();
        myDB.getScores().add(addScore());

        saveToSP();
    }

    private Score addScore() {
        Score s = new Score().setScore(score).setLat(lat).setLon(lon).setTime(String.valueOf(LocalTime.now().format(dtf)));
        return s;
    }

    public void loadFromSP() {
        if (myDB == null)
            myDB = new MyDB();

        String js = MSP.getMe().getString("MY_DB", "");
        myDB = new Gson().fromJson(js, MyDB.class);
    }

    public void saveToSP() {
        if (myDB == null)
            myDB = new MyDB();

        Collections.sort(myDB.getScores(), new CompareScores());
        myDB = getTop10();
        String json = new Gson().toJson(myDB);
        MSP.getMe().putString("MY_DB", json);
    }

    private MyDB getTop10() {
        if (myDB == null)
            myDB = new MyDB();

        ArrayList<Score> res = new ArrayList<>();
        int counter = 0;
        while (res.size() < myDB.getScores().size()) {
            res.add(myDB.getScores().get(counter));
            counter++;
        }
        return myDB.setScores(res);
    }


    private void initBTNs() {
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

        panel_BTN_top10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                top10();
            }
        });
    }

    private void retry() {
        finish();
        Intent intent = new Intent(this, ActivityGame.class);
        startActivity(intent);
    }

    private void quit() {
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private void top10() {
        finish();
        Intent intent = new Intent(this, ActivityTop10.class);
        intent.putExtra("score", score);
        intent.putExtra("lat", lat);
        intent.putExtra("lon", lon);
        intent.putExtra("db", new Gson().toJson(myDB));
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
        panel_BTN_top10 = findViewById(R.id.panel_BTN_top10);
    }
}