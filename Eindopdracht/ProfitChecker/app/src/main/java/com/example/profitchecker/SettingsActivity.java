package com.example.profitchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    // const with log tag for debugging, const uri which is filled with an API
    private final static String LOG_TAG = "ProfitChecker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.d(LOG_TAG, "SettingsActivity gestart");
    }

    public void finishSettingsActivity(View view) {
        Log.d(LOG_TAG, "SettingsActivity afgesloten");
        finish();
    }
}