package com.example.beer4life.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.beer4life.R;
import com.example.beer4life.callbacks.CallBack_List;
import com.example.beer4life.callbacks.CallBack_Settings;
import com.example.beer4life.generalObjects.Adapter_Score;
import com.example.beer4life.generalObjects.DataManager;
import com.example.beer4life.generalObjects.MyDB;
import com.example.beer4life.generalObjects.Score;

import java.util.ArrayList;


public class FragmentList extends Fragment {

    private ArrayList<Score> scores;
    private RecyclerView leaderboard_LST_scores;

    private AppCompatActivity activity;
    private CallBack_List callBack_list;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallBackList(CallBack_List callBack_list) {
        this.callBack_list = callBack_list;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        //ArrayList<Score> scores = DataManager.generateScores();
        scores = callBack_list.getScores();
        Adapter_Score adapter_score = new Adapter_Score(getActivity(), scores);


        // Grid
        leaderboard_LST_scores.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        // Vertically
        //main_LST_movies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        leaderboard_LST_scores.setHasFixedSize(true);
        leaderboard_LST_scores.setItemAnimator(new DefaultItemAnimator());
        leaderboard_LST_scores.setAdapter(adapter_score);

        adapter_score.setScoreMapClickListener(new Adapter_Score.ScoreMapClickListener() {
            @Override
            public void scoreMapClicked(Score score, int pos) {
                double lat = score.getLat();
                double lon = score.getLon();
                callBack_list.getScoreLocation(lat,lon);
            }
        });
    }

    private void findViews(View view) {
        leaderboard_LST_scores = view.findViewById(R.id.leaderboard_LST_scores);
    }
}