package com.example.beer4life.generalObjects;

import java.util.ArrayList;

public class DataManager {

    public static ArrayList<Score> generateScores() {
        ArrayList<Score> scores = new ArrayList<>();


        scores.add(new Score()
                .setTime("00:00")
                .setAddress("Ramat Gan")
                .setScore(100)
                .setLat(32.07248072148954)
                .setLon(34.82750519956097)
        );

        scores.add(new Score()
                .setTime("11:11")
                .setAddress("Tel Aviv")
                .setScore(50)
                .setLat(32.07248072148954)
                .setLon(34.82750519956097)
        );







        return scores;
    }
}
