package com.example.profitchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    // const with log tag for debugging, const uri which is filled with an API
    private final static String LOG_TAG = "ProfitChecker";
    private final static String URI = "https://hiveon.net/api/v1/stats/pool";

    // variables to save data from api
    public double exchangeRatesUsd = 0;
    public double expectedReward24H = 0;
    public double expectedProfitabilityUSD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getProfitability();
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

    private void showProfitability(JSONObject data) {
        try { // get request on api 'stats' and dive deeper in child objects
            JSONObject stats = (JSONObject)data.get("stats");

            // get current value of 1 ethereum and store it in a global variable
            JSONObject eth = (JSONObject)stats.get("ETH");
            JSONObject exchangeRates = (JSONObject)eth.get("exchangeRates");
            exchangeRatesUsd = (double)exchangeRates.get("USD");

            // get current expected ethereum reward per 100 MH/s per 24 hours and store it in a global variable
            expectedReward24H = (double)eth.get("expectedReward24H");

            // multiple exchangeRatesUsd by expectedReward24H to calculate the current profitability per 100 MH/s in USD and store it in a global variable
            expectedProfitabilityUSD = exchangeRatesUsd * expectedReward24H;

        } catch (JSONException e) {
            Log.e(LOG_TAG, "[TASK FAILED SUCCESSFULLY] JSON exception");
        }

        // logging data api results
        Log.d(LOG_TAG, String.valueOf(exchangeRatesUsd));
        Log.d(LOG_TAG, String.valueOf(expectedReward24H));
        Log.d(LOG_TAG, String.valueOf(expectedProfitabilityUSD));

        // update to textview and add dollar sign in front of it
        TextView tv = findViewById(R.id.txt_exchangeRatesUsd);
        tv.setText("$ " + String.valueOf(exchangeRatesUsd));

    }

    // maybe nice to use later, a small easter egg
    private void easterEgg() {
        // creating a context which is requiered for the toast message below
        Context context = getApplicationContext();
        CharSequence text = "Je hebt een easter egg gevonden!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}