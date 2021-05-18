package com.example.week3opdr4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    Random random = new Random();
    Integer randomNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        generateRandomNumber();
        Log.d("log", "Created");
    }

    public void generateRandomNumber() {
        randomNumber = random.nextInt(3 - 1 + 1) + 1;
        Log.d("log", "" + randomNumber);
    }

    public void buttonOne(View view) {
        generateRandomNumber();
        Context context = getApplicationContext();
        CharSequence text = "";

        if (randomNumber.equals(1)) {
            text = "Lekker bezig";
        } else {
            text = "Helaas";
        }

        Log.d("log", "Knoppie 1");

        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void buttonTwo(View view) {
        generateRandomNumber();
        Context context = getApplicationContext();
        CharSequence text = "";

        if (randomNumber.equals(2)) {
            text = "Lekker bezig";
        } else {
            text = "Helaas";
        }

        Log.d("log", "Knoppie 2");

        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void buttonThree(View view) {
        generateRandomNumber();
        Context context = getApplicationContext();
        CharSequence text = "";

        if (randomNumber.equals(3)) {
            text = "Lekker bezig";
        } else {
            text = "Helaas";
        }

        Log.d("log", "Knoppie 3");

        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}