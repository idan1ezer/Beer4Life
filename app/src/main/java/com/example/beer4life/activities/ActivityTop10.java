package com.example.beer4life.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.beer4life.R;
import com.example.beer4life.callbacks.CallBack_List;
import com.example.beer4life.callbacks.CallBack_Map;
import com.example.beer4life.fragment.FragmentGoogleMaps;
import com.example.beer4life.fragment.FragmentList;
import com.example.beer4life.fragment.FragmentSettings;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;

public class ActivityTop10 extends AppCompatActivity {

    private FragmentList fragmentList;
    private FragmentGoogleMaps fragmentGoogleMaps;

    private double lat;
    private double lon;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);

        initFragmentMap();
        initFragmentList();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lat = extras.getDouble("lat");
            lon = extras.getDouble("lon");
            score = extras.getInt("score");
        }
    }

    private void initFragmentList() {
        fragmentList = new FragmentList();
        fragmentList.setActivity(this);
        fragmentList.setCallBackList(callBack_list);
        callBack_list.getScoreLocation(lat,lon);
        getSupportFragmentManager().beginTransaction().add(R.id.top10_frame1, fragmentList).commit();
    }

    private void initFragmentMap() {
        fragmentGoogleMaps = new FragmentGoogleMaps();
        fragmentGoogleMaps.setActivity(this);
        fragmentGoogleMaps.setCallBackMap(callBack_map);
        fragmentGoogleMaps.setLat(lat).setLon(lon);
        callBack_map.getLocation(lat,lon);
        getSupportFragmentManager().beginTransaction().add(R.id.top10_frame2, fragmentGoogleMaps).commit();
    }

    CallBack_List callBack_list = new CallBack_List() {
        @Override
        public void getScoreLocation(double lat, double lon) {
            fragmentGoogleMaps.setLat(lat).setLon(lon);
            callBack_map.getLocation(lat,lon);
        }
    };





    CallBack_Map callBack_map = (lat, lon) -> {
        //Zoom to place
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {
            LatLng latLng = new LatLng(lat, lon);
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Played Here!"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 5000, null);
        });
    };



}