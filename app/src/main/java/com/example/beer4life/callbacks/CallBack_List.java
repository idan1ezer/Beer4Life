package com.example.beer4life.callbacks;

import com.example.beer4life.generalObjects.Score;

import java.util.ArrayList;

public interface CallBack_List {
    ArrayList<Score> getScores();
    void getScoreLocation(double lat, double lon);
}
