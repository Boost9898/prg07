package com.example.profitchecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // const with log tag for debugging, const uri which is filled with an API, request code and location client
    private final static String LOG_TAG = "ProfitChecker";
    private final static String URI = "https://hiveon.net/api/v1/stats/pool";
    private final static int REQ_CODE_LAST_KNOWN_LOCATION = 1234567890;
    private FusedLocationProviderClient fusedLocationClient;

    // const with exchange rate from USD to other currency (i know this isn't the best way to do it but it works)
    private final static double EXCHANGE_USD_USD = 1.00;
    private final static double EXCHANGE_USD_EUR = 0.840667;
    private final static double EXCHANGE_USD_GBP = 0.722478;
    private final static double EXCHANGE_USD_JPY = 110.471513;

    // variable with default currency
    public static String currency = "$";
    public static String countryName = "";

    // variables to store GPS coordinates
    public double latitude = 0;
    public double longitude = 0;

    // variables to store data from api
    public double exchangeRatesUsd = 0;
    public double expectedReward24H = 0;
    public double expectedProfitabilityUSD = 0;

    // location request to request the current location of smartphone
    LocationRequest locationRequest;


    @Override // default start function
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn_settings);
        button.setOnClickListener(this);

        // get latest known location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        showCurrentLocation();
        getProfitability();
    }


    @Override // opens settings activity
    public void onClick(View view) {
        // create intent and link it to the new activity and start it
        Intent i = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(i);
    }


    @Override // when a user comes back to the app, it executes this function
    protected void onResume() {
        // reads current status of languageSwitch (settings.xml) and logs it
        // calls getProfitability/showCurrentLocation again to update to the latest information
        Log.d(LOG_TAG, "Language switch: " + SettingsActivity.languageSwitch(this));
        super.onResume();
        getProfitability();
        showCurrentLocation();
    }


    private void getProfitability() {
        // instantiate the RequestQueue (internet)
        // using Volley to handle the internet connection
        RequestQueue queue = Volley.newRequestQueue(this);

        // request a string response from the provided URL
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URI, null,
                new Response.Listener<JSONObject>() {
                    @Override // send log on response
                    public void onResponse(JSONObject response) {
//                        Log.d(LOG_TAG, response.toString());
                        showProfitability(response);
                    }
                }, new Response.ErrorListener() {
            @Override // logs funny error
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, "[TASK FAILED SUCCESSFULLY] " + error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }


    @SuppressLint("SetTextI18n") // complex method which gets data out of api + converts GPS coordinates to country + updates textview
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

            // uses Geocoder to convert lat/long coordinates to country
            Context context = getApplicationContext();
            Geocoder gcd = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses.size() > 0)
            {
                countryName = addresses.get(0).getCountryName();
                Log.d(LOG_TAG,  "Current country: " + countryName);

            } else {
                Log.d(LOG_TAG, "No country found");
            }


            // update current eth profit per 10000 mhs to textview and add ether sign in front of it
            // added math.round 100000 to round it to 5 decimals
            TextView tv_expectedReward24H = findViewById(R.id.txt_profitValue100ether);
            tv_expectedReward24H.setText("Ξ " + Math.round(expectedReward24H * 100000) / 100000.0);

            // check which country you're in and update currency symbol + apply exchange rate + round decimals to 2 digits
            TextView tv_exchangeRatesUsd;
            TextView tv_expectedProfitabilityUSD;
            switch(countryName) {
                case "Germany":
                case "Netherlands":
                    Log.d(LOG_TAG, "Dit is Germany, Netherlands");
                    currency = "€";
                    // update current eth price to textview and add currency symbol in front of it
                    tv_exchangeRatesUsd = findViewById(R.id.txt_exchangeRatesUsd);
                    tv_exchangeRatesUsd.setText(currency + " " + (Math.round(exchangeRatesUsd * EXCHANGE_USD_EUR * 100) / 100.0));
                    // update current usd profit per 100 mhs to textview and add dollar sign in front of it
                    // added math.round 100 to round it to 2 decimals
                    tv_expectedProfitabilityUSD = findViewById(R.id.txt_profitValue100usd);
                    tv_expectedProfitabilityUSD.setText(currency + " " + Math.round(expectedProfitabilityUSD * 100 * EXCHANGE_USD_EUR) / 100.0);
                    break;
                case "United Kingdom":
                    Log.d(LOG_TAG, "Dit is United Kingdom");
                    currency = "£";
                    tv_exchangeRatesUsd = findViewById(R.id.txt_exchangeRatesUsd);
                    tv_exchangeRatesUsd.setText(currency + " " + (Math.round(exchangeRatesUsd * EXCHANGE_USD_GBP * 100) / 100.0));
                    tv_expectedProfitabilityUSD = findViewById(R.id.txt_profitValue100usd);
                    tv_expectedProfitabilityUSD.setText(currency + " " + Math.round(expectedProfitabilityUSD * 100 * EXCHANGE_USD_GBP) / 100.0);
                    break;
                case "Japan":
                    Log.d(LOG_TAG, "Dit is Japan");
                    currency = "¥";
                    tv_exchangeRatesUsd = findViewById(R.id.txt_exchangeRatesUsd);
                    tv_exchangeRatesUsd.setText(currency + " " + (Math.round(exchangeRatesUsd * EXCHANGE_USD_JPY * 100) / 100.0));
                    tv_expectedProfitabilityUSD = findViewById(R.id.txt_profitValue100usd);
                    tv_expectedProfitabilityUSD.setText(currency + " " + Math.round(expectedProfitabilityUSD * 100 * EXCHANGE_USD_JPY) / 100.0);
                    break;
                default:
                    Log.d(LOG_TAG, "Dit is default (Murica)");
                    currency = "$";
                    tv_exchangeRatesUsd = findViewById(R.id.txt_exchangeRatesUsd);
                    tv_exchangeRatesUsd.setText(currency + " " + (Math.round(exchangeRatesUsd * EXCHANGE_USD_USD * 100) / 100.0));
                    tv_expectedProfitabilityUSD = findViewById(R.id.txt_profitValue100usd);
                    tv_expectedProfitabilityUSD.setText(currency + " " + Math.round(expectedProfitabilityUSD * 100 * EXCHANGE_USD_USD) / 100.0);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "[TASK FAILED SUCCESSFULLY] JSON exception");
        }

        // logging data api results
        Log.d(LOG_TAG, String.valueOf(exchangeRatesUsd));
        Log.d(LOG_TAG, String.valueOf(expectedReward24H));
        Log.d(LOG_TAG, String.valueOf(expectedProfitabilityUSD));
    }


    private void showCurrentLocation() {
        Log.d(LOG_TAG, "showCurrentLocation() is aangeroepen");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_CODE_LAST_KNOWN_LOCATION);
            Log.d(LOG_TAG, "ShowCurrentLocation() if statement");
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d(LOG_TAG, "Locatie is opgegaald");
                            Log.d(LOG_TAG, "Last Location Latitude: " + location.getLatitude());
                            Log.d(LOG_TAG, "Last Location Longitude: " + location.getLongitude());

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            getProfitability();

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
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void easterEgg() {
        // maybe nice to use later, a small easter egg
        // creating a context which is requiered for the toast message below
        Context context = getApplicationContext();
        CharSequence text = "Je hebt een easter egg gevonden!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}