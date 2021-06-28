package com.example.profitchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.Locale;

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

        setAppLocale("NL");

        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //oof
        if(languageSwitch(this)) {
            setAppLocale("NL");
        } else {
            setAppLocale("EN");
        }

        // updates current activity with new language
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);
    }

    // reading settings data, using context for this current activity
    public static boolean languageSwitch(Context context) {
        Log.d(LOG_TAG, "languageSwitch activated");
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(OPT_LANG, OPT_LANG_DEFAULT);
    }

    private void setAppLocale(String localeCode) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(localeCode.toLowerCase()));
        res.updateConfiguration(conf, dm);
    }
}