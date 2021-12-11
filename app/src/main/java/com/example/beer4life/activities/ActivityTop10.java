package com.example.beer4life.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.beer4life.R;
import com.example.beer4life.callbacks.CallBack_List;
import com.example.beer4life.callbacks.CallBack_Map;
import com.example.beer4life.fragment.FragmentGoogleMaps;
import com.example.beer4life.fragment.FragmentList;
import com.example.beer4life.generalObjects.MyDB;
import com.example.beer4life.generalObjects.Score;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import android.os.Build;
import android.os.Bundle;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ActivityTop10 extends AppCompatActivity {

    private FragmentList fragmentList;
    private FragmentGoogleMaps fragmentGoogleMaps;

    private double lat;
    private double lon;
    private int score;

    private MyDB myDB;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lat = extras.getDouble("lat");
            lon = extras.getDouble("lon");
            score = extras.getInt("score");
            myDB = new Gson().fromJson(extras.getString("db"), MyDB.class);
        }

        initFragmentMap();
        initFragmentList();
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
        public ArrayList<Score> getScores() {
            return myDB.getScores();
        }

        @Override
        public void getScoreLocation(double lat, double lon) {
            fragmentGoogleMaps.setLat(lat).setLon(lon);
            callBack_map.getLocation(lat,lon);
        }
    };

    CallBack_Map callBack_map = (lat, lon) -> {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {
            LatLng latLng = new LatLng(lat, lon);
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Played Here!"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 5000, null);
        });
    };
}