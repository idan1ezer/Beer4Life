package com.example.beer4life.generalObjects;

import java.util.ArrayList;

public class MyDB {

    private ArrayList<Score> scores = new ArrayList<>();

    public MyDB() { }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public MyDB setScores(ArrayList<Score> scores) {
        this.scores = scores;
        return this;
    }

    @Override
    public String toString() {
        return "MyDB{" +
                "scores=" + scores +
                '}';
    }
}