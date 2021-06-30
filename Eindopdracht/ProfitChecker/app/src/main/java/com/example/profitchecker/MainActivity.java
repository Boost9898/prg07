package com.example.profitchecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // const with log tag for debugging, const uri which is filled with an API
    private final static String LOG_TAG = "ProfitChecker";
    private final static String URI = "https://hiveon.net/api/v1/stats/pool";
    private final static int REQ_CODE_LAST_KNOWN_LOCATION = 1234567890;
    private FusedLocationProviderClient fusedLocationClient;

    // costs with exchange rate from USD to other currency (i know this isn't the best way to do it but it works)
    private final static double EXCHANGE_USD_EUR = 0.840667;
    private final static double EXCHANGE_USD_GBP = 0.722478;
    private final static double EXCHANGE_USD_JPY = 110.471513;
    private final static double EXCHANGE_USD_INR = 74.379222;

    // variables to save data from api
    public double exchangeRatesUsd = 0;
    public double expectedReward24H = 0;
    public double expectedProfitabilityUSD = 0;

    // private boolean, used to prevent a refresh loop
    private boolean isRefreshed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn_settings);
        button.setOnClickListener(this);

        getProfitability();

        // get latest known location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        showCurrentLocation();
    }


    @Override
    public void onClick(View view) {
        // set boolean to true to prevent a loop later
        isRefreshed = true;

        // opens settings activity
        Log.d(LOG_TAG, "Click");
        Intent i = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        // reads current status of languageSwitch (settings.xml) and logs it
        // calls getProfitability again when coming back to this screen to update to the latest information
        Log.d(LOG_TAG, "Language switch: " + SettingsActivity.languageSwitch(this));
        super.onResume();
        getProfitability();
        showCurrentLocation();
    }


    private void getProfitability() {
        // instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        // request a string response from the provided URL
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URI, null,
                new Response.Listener<JSONObject>() {
                    @Override // send log on response
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, response.toString());
                        showProfitability(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, "[TASK FAILED SUCCESSFULLY] " + error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }


    @SuppressLint("SetTextI18n")
    private void showProfitability(JSONObject data) {
        try { // get request on api 'stats' and dive deeper in child objects
            JSONObject stats = (JSONObject) data.get("stats");

            // get current value of 1 ethereum and store it in a global variable
            JSONObject eth = (JSONObject) stats.get("ETH");
            JSONObject exchangeRates = (JSONObject) eth.get("exchangeRates");
            exchangeRatesUsd = (double) exchangeRates.get("USD");

            // get current expected ethereum reward per 100 MH/s per 24 hours and store it in a global variable
            expectedReward24H = (double) eth.get("expectedReward24H");

            // multiple exchangeRatesUsd by expectedReward24H to calculate the current profitability per 100 MH/s in USD and store it in a global variable
            expectedProfitabilityUSD = exchangeRatesUsd * expectedReward24H;

        } catch (JSONException e) {
            Log.e(LOG_TAG, "[TASK FAILED SUCCESSFULLY] JSON exception");
        }

        // logging data api results
        Log.d(LOG_TAG, String.valueOf(exchangeRatesUsd));
        Log.d(LOG_TAG, String.valueOf(expectedReward24H));
        Log.d(LOG_TAG, String.valueOf(expectedProfitabilityUSD));

        // update current eth price to textview and add dollar sign in front of it
        TextView tv_exchangeRatesUsd = findViewById(R.id.txt_exchangeRatesUsd);
        tv_exchangeRatesUsd.setText("$ " + exchangeRatesUsd);

        // update current usd profit per 100 mhs to textview and add dollar sign in front of it
        // added math.round 100 to round it to 2 decimals
        TextView tv_expectedProfitabilityUSD = findViewById(R.id.txt_profitValue100usd);
        tv_expectedProfitabilityUSD.setText("$ " + Math.round(expectedProfitabilityUSD * 100) / 100.0);

        // update current eth profit per 10000 mhs to textview and add ether sign in front of it
        // added math.round 100000 to round it to 5 decimals
        TextView tv_expectedReward24H = findViewById(R.id.txt_profitValue100ether);
        tv_expectedReward24H.setText("Ξ " + Math.round(expectedReward24H * 100000) / 100000.0);
    }

    private void showCurrentLocation() {
        Log.d(LOG_TAG, "showCurrentLocation() is aangeroepen");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_CODE_LAST_KNOWN_LOCATION);
            // here to request the missing permissions, and then overriding

            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d(LOG_TAG, "showCurrentLocation() is mislukt");
                        if (location != null) {
                            Log.d(LOG_TAG, "Locatie is opgegaald");
                        } else {
                            Log.d(LOG_TAG, "No last know location available :(");
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQ_CODE_LAST_KNOWN_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(LOG_TAG, "Permission is granted. Continue the action or workflow in your app");
                    showCurrentLocation();
                } else {
                    Log.d(LOG_TAG, "User denied permissions");
                    showCurrentLocation();
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    private void easterEgg() {
//        // maybe nice to use later, a small easter egg
//        // creating a context which is requiered for the toast message below
//        Context context = getApplicationContext();
//        CharSequence text = "Je hebt een easter egg gevonden!";
//        int duration = Toast.LENGTH_SHORT;
//
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();
//    }

}