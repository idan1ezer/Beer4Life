package com.example.beer4life.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beer4life.generalObjects.Drink;
import com.example.beer4life.generalObjects.Heart;
import com.example.beer4life.generalObjects.MyService;
import com.example.beer4life.R;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ActivityGame extends AppCompatActivity implements LocationListener{
    private final int COL = 5;
    private final int ROW = 7;
    private final int MAX_LIVES = 3;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    private int delay = 1000;

    private ImageButton panel_BTN_button1;
    private ImageButton panel_BTN_button2;
    private ImageView[] panel_IMG_player;
    private Drink[][] panel_Drink;
    private Heart[] panel_IMG_hearts;
    private TextView panel_LBL_score;
    private int[] waterBeerChances = {1,1,0,1,1,0,1,0,1,1};
    private int lives = MAX_LIVES;
    private int score = 0;
    private int addBeerIndex = 0;

    private SensorManager sensorManager;
    private Sensor accSensor;
    private boolean sens = false;

    private LocationManager locationManager;
    private double lat;
    private double lon;


    final Handler handler = new Handler();
    private Runnable r = new Runnable() {
        public void run() {
            checkDisqualification();
            updateBeersPos();
            updateScore();
            addRandomBeer();
            handler.postDelayed(r, delay);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        startService(new Intent(this, MyService.class));
        findViews();
        initBTNs();
        checkLocationPermission();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            setGameSpeed(extras.getInt("dif"));
            setGameType(extras.getBoolean("sen"));
        }

    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(ActivityGame.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ActivityGame.this,new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION }, 100);
        }

        getLocation();
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, (LocationListener) ActivityGame.this);

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lat = location.getLatitude();
            lon = location.getLongitude();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(ActivityGame.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            lat = location.getLatitude();
            lon = location.getLongitude();



        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private void initBTNs() {
        panel_BTN_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(-1);
            }
        });
        panel_BTN_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(1);
            }
        });
    }

    private void setGameSpeed(int dif) {
        if (dif == 0)
            delay = 1000;
        else
            delay = 600;
    }

    private void setGameType(boolean sen) {
        if (sen) {
            sens = true;
            initSensors();
        }
        else
            sens = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTicker();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(this, MyService.class));
        stopTicker();
    }

    private void startTicker() {
        handler.postDelayed(r, delay);
    }

    private void stopTicker() {
        handler.removeCallbacks(r);
    }

    private void move(int direction) {
        int cur_pos = getCurPos();
        int new_pos = cur_pos + direction;

        if ((cur_pos != -999) && ((new_pos) >= 0) && (new_pos <= COL-1)){
            panel_IMG_player[cur_pos].setVisibility(View.INVISIBLE);
            panel_IMG_player[new_pos].setVisibility(View.VISIBLE);
        }
    }

    private int getCurPos() {
        for(int i = 0; i < panel_IMG_player.length; i++) {
            if(panel_IMG_player[i].getVisibility() == View.VISIBLE)
                return i;
        }
        return -999;
    }

    private void updateBeersPos() {
        for (int c=0; c < COL; c++) {
            for (int r = 0; r < ROW; r++) {
                if (panel_Drink[r][c].getImg().getVisibility() == View.VISIBLE){
                    panel_Drink[r][c].getImg().setVisibility(View.INVISIBLE);
                    r++;
                    if (r < ROW) {
                        if(panel_Drink[r-1][c].isBeer()) {
                            panel_Drink[r][c].getImg().setImageResource(R.drawable.ic_beer);
                            panel_Drink[r][c].setBeer(true);
                        }
                        else {
                            panel_Drink[r][c].getImg().setImageResource(R.drawable.ic_water_bottle);
                            panel_Drink[r][c].setBeer(false);
                        }
                        panel_Drink[r][c].getImg().setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    private void checkDisqualification() {
        int cur_pos = getCurPos();
        if ((panel_Drink[ROW-1][cur_pos].getImg().getVisibility() == View.VISIBLE) && (panel_Drink[ROW-1][cur_pos].isBeer() == true)) {
            panel_IMG_hearts[lives-1].getImg().setImageResource(R.drawable.ic_empty_heart);
            panel_IMG_hearts[lives-1].setFull(false);
            lives--;

            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(300);
            }
        }

        if (lives == 0)
            gameOver();
    }

    private void gameOver() {
        Toast.makeText(this, "Game Over!", Toast.LENGTH_LONG).show();
        SystemClock.sleep(1000);

        finish();
        Intent intent = new Intent(this, ActivityGameOver.class);
        intent.putExtra("score", score);
        intent.putExtra("lat", lat);
        intent.putExtra("lon", lon);
        startActivity(intent);
    }

    private void addRandomBeer() {
        Random r = new Random();

        if(addBeerIndex % 2 == 1) {
            int index = r.nextInt(COL);
            int num = r.nextInt(waterBeerChances.length);
            if (waterBeerChances[num] == 1) {
                panel_Drink[0][index].getImg().setImageResource(R.drawable.ic_beer);
                panel_Drink[0][index].getImg().setVisibility(View.VISIBLE);
                panel_Drink[0][index].setBeer(true);
            }
            else {
                panel_Drink[0][index].getImg().setImageResource(R.drawable.ic_water_bottle);
                panel_Drink[0][index].getImg().setVisibility(View.VISIBLE);
                panel_Drink[0][index].setBeer(false);
            }
        }
        addBeerIndex++;
    }

    private void updateScore() {
        for (int column=0; column<COL-1; column++){
            if ((panel_Drink[ROW-1][column].getImg().getVisibility() == View.VISIBLE) && (panel_IMG_player[column].getVisibility() == View.INVISIBLE) && (panel_Drink[ROW-1][column].isBeer() == true))
                score+=50;
            if ((panel_Drink[ROW-1][column].getImg().getVisibility() == View.VISIBLE) && (panel_IMG_player[column].getVisibility() == View.VISIBLE) && (panel_Drink[ROW-1][column].isBeer() == false))
                score+=200;
        }
        panel_LBL_score.setText("" + score);
    }

    private void initSensors() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        panel_BTN_button1.setVisibility(View.INVISIBLE);
        panel_BTN_button2.setVisibility(View.INVISIBLE);
    }

    private final SensorEventListener accSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            DecimalFormat df = new DecimalFormat("##.##");
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            if( x > 1.5)
                move(-1);
            if(x < -1.5)
                move(1);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(sens)
            sensorManager.registerListener(accSensorEventListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sens)
            sensorManager.unregisterListener(accSensorEventListener);
    }




    @SuppressLint("WrongViewCast")
    private void findViews() {
        panel_BTN_button1 = findViewById(R.id.panel_BTN_button1);
        panel_BTN_button2 = findViewById(R.id.panel_BTN_button2);

        panel_IMG_player = new ImageView[]{
                findViewById(R.id.panel_IMG_player1),
                findViewById(R.id.panel_IMG_player2),
                findViewById(R.id.panel_IMG_player3),
                findViewById(R.id.panel_IMG_player4),
                findViewById(R.id.panel_IMG_player5)
        };

        panel_Drink = new Drink[][] {
                {
                        new Drink().setImg(findViewById(R.id.panel_IMG_row1_beer1)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row1_beer2)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row1_beer3)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row1_beer4)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row1_beer5)).setBeer(true)
                },
                {
                        new Drink().setImg(findViewById(R.id.panel_IMG_row2_beer1)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row2_beer2)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row2_beer3)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row2_beer4)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row2_beer5)).setBeer(true)
                },
                {
                        new Drink().setImg(findViewById(R.id.panel_IMG_row3_beer1)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row3_beer2)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row3_beer3)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row3_beer4)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row3_beer5)).setBeer(true)
                },
                {
                        new Drink().setImg(findViewById(R.id.panel_IMG_row4_beer1)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row4_beer2)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row4_beer3)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row4_beer4)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row4_beer5)).setBeer(true)
                },
                {
                        new Drink().setImg(findViewById(R.id.panel_IMG_row5_beer1)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row5_beer2)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row5_beer3)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row5_beer4)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row5_beer5)).setBeer(true)
                },
                {
                        new Drink().setImg(findViewById(R.id.panel_IMG_row6_beer1)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row6_beer2)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row6_beer3)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row6_beer4)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row6_beer5)).setBeer(true)
                },
                {
                        new Drink().setImg(findViewById(R.id.panel_IMG_row7_beer1)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row7_beer2)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row7_beer3)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row7_beer4)).setBeer(true),
                        new Drink().setImg(findViewById(R.id.panel_IMG_row7_beer5)).setBeer(true)
                }
        };

        panel_LBL_score = findViewById(R.id.panel_LBL_score);

        panel_IMG_hearts = new Heart[]{
                new Heart().setImg(findViewById(R.id.panel_IMG_heart1)).setFull(true),
                new Heart().setImg(findViewById(R.id.panel_IMG_heart2)).setFull(true),
                new Heart().setImg(findViewById(R.id.panel_IMG_heart3)).setFull(true)
        };

    }
}