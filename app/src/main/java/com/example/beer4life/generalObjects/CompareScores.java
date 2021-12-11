package com.example.beer4life.generalObjects;

import java.util.Comparator;

public class CompareScores implements Comparator<Score> {
    @Override
    public int compare(Score o1, Score o2) {
        if (o1.getScore() > o2.getScore())
            return -1;
        else if (o1.getScore() < o2.getScore())
            return 1;
        else
            return 0;
    }
}
