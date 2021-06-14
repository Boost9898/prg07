package com.example.profitchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

public class SettingsActivity extends PreferenceActivity {

    // const with log tag for debugging
    private final static String LOG_TAG = "ProfitChecker";

    // const, name from settings.xml
    public final static String OPT_LANG = "switch_language";
    public final static Boolean OPT_LANG_DEFAULT = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // link settings.xml to settingsActivity (this)
        addPreferencesFromResource(R.xml.settings);
    }

    // reading settings data, using context for this current activity
    public static boolean languageSwitch(Context context) {
        Log.d(LOG_TAG, "languageSwitch activated");
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(OPT_LANG, OPT_LANG_DEFAULT);
    }
}