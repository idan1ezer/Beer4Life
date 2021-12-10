package com.example.beer4life.generalObjects;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beer4life.R;
import com.example.beer4life.fragment.FragmentList;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class Adapter_Score extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private ArrayList<Score> scores = new ArrayList<>();
    private ScoreMapClickListener scoreMapClickListener;

    public Adapter_Score(FragmentActivity activity, ArrayList<Score> scores) {
        //this.activity = activity;
        this.scores = scores;
    }

    public Adapter_Score setScoreMapClickListener(ScoreMapClickListener scoreMapClickListener) {
        this.scoreMapClickListener = scoreMapClickListener;
        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_leaderboard, viewGroup, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ScoreViewHolder scoreViewHolder = (ScoreViewHolder) holder;
        Score score = getItem(position);
        Log.d("pttt", "position= " + position);

        scoreViewHolder.leaderboard_LBL_date.setText(""+score.getTime());
        scoreViewHolder.leaderboard_LBL_address.setText(""+score.getAddress());
        scoreViewHolder.leaderboard_LBL_score.setText(""+score.getScore());
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    private Score getItem(int position) {
        return scores.get(position);
    }



    public interface ScoreMapClickListener {
        void scoreMapClicked(double lat, double lon);
    }



    public class ScoreViewHolder extends RecyclerView.ViewHolder {

        public MaterialTextView leaderboard_LBL_date;
        public MaterialTextView leaderboard_LBL_address;
        public MaterialTextView leaderboard_LBL_score;
        public MaterialButton leaderboard_BTN_map;

        public ScoreViewHolder(final View itemview) {
            super(itemview);
            this.leaderboard_LBL_date = itemview.findViewById(R.id.leaderboard_LBL_date);
            this.leaderboard_LBL_address = itemview.findViewById(R.id.leaderboard_LBL_address);
            this.leaderboard_LBL_score = itemview.findViewById(R.id.leaderboard_LBL_score);
            this.leaderboard_BTN_map = itemview.findViewById(R.id.leaderboard_BTN_map);

            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scoreMapClickListener.scoreMapClicked(getItem(getAdapterPosition()).getLat(),getItem(getAdapterPosition()).getLon());
                }
            });
        }



    }

}
