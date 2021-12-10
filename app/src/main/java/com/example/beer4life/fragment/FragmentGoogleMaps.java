package com.example.beer4life.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beer4life.R;
import com.example.beer4life.callbacks.CallBack_Map;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentGoogleMaps extends Fragment {

    private AppCompatActivity activity;
    private CallBack_Map callBack_map;

    private double lat;
    private double lon;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallBackMap(CallBack_Map callBack_map) {
        this.callBack_map = callBack_map;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_google_maps, container, false);

        return view;
    }


    public FragmentGoogleMaps setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public FragmentGoogleMaps setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(lat, lon);
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(latLng).title("You are here"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 5000, null);
    }

    /*

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng latLng = new LatLng(-34, 151);
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng).title("You are here"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 5000, null);
        }
    };

    @Nullable
    @Override



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    */

}
