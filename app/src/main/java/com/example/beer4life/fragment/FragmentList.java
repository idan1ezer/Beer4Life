package com.example.beer4life.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beer4life.R;
import com.example.beer4life.callbacks.CallBack_List;
import com.example.beer4life.callbacks.CallBack_Settings;
import com.example.beer4life.generalObjects.Score;

import java.util.ArrayList;


public class FragmentList extends Fragment {

    private ArrayList<Score> scores;

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
        //findViews(view);
        //initViews(view);

        return view;
    }
}