package com.example.soundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public final static String LOG_TAG = "SBD_Log";
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonOne(View view) {
        Log.d(MainActivity.LOG_TAG, "Button 1");
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.sound01);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }

        mediaPlayer.start();
    }

    public void buttonTwo(View view) {
        Log.d(MainActivity.LOG_TAG, "Button 2");
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.sound02);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }

        mediaPlayer.start();
    }

    private void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}