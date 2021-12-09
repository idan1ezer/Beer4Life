package com.example.beer4life.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.beer4life.R;
import com.example.beer4life.callbacks.CallBack_Settings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;


public class FragmentSettings extends Fragment {

    MaterialTextView settings_LBL_difficulty;
    MaterialTextView settings_LBL_sensors;
    RadioGroup settings_RADIO_difficulty;
    RadioButton settings_RADIO_selected;
    SwitchMaterial settings_SW_sensors;
    MaterialButton settings_BTN_save;

    private AppCompatActivity activity;
    private CallBack_Settings callBack_settings;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallBackSettings(CallBack_Settings callBack_settings) {
        this.callBack_settings = callBack_settings;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        findViews(view);
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        settings_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack_settings != null) {
                    int dif = getDifficulty(view);
                    boolean sen = getSensors();
                    callBack_settings.setSettings(dif, sen);
                }

            }
        });
    }

    private int getDifficulty(View view) {
        int checked = settings_RADIO_difficulty.getCheckedRadioButtonId();
        settings_RADIO_selected = view.findViewById(checked);
        if (settings_RADIO_selected.getText().toString().equals("Easy"))
            return 0;
        else
            return 1;
    }

    private boolean getSensors() {
        return settings_SW_sensors.isChecked();
    }

    private void findViews(View view) {
        settings_LBL_difficulty = view.findViewById(R.id.settings_LBL_difficulty);
        settings_RADIO_difficulty = view.findViewById(R.id.settings_RADIO_difficulty);
        settings_LBL_sensors = view.findViewById(R.id.settings_LBL_sensors);
        settings_SW_sensors = view.findViewById(R.id.settings_SW_sensors);
        settings_BTN_save = view.findViewById(R.id.settings_BTN_save);
    }
}