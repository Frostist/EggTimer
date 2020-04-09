package com.example.eggtimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBarTime;
    Button buttonStart;
    TextView textViewStart;
    CountDownTimer countDownTimer;
    boolean counterIsActive = false;
    String TAG = "Project";


    public void timerReset() {
        //defines what happens whe the timer is true
        textViewStart.setText("01:00");
        seekBarTime.setProgress(60);
        seekBarTime.setEnabled(true);
        countDownTimer.cancel();
        buttonStart.setText("START");
        counterIsActive = false;

    }

    public void timerStart(View view) {
        Log.w(TAG, "Button pushed");
        seekBarTime.setEnabled(false);
        buttonStart.setText("STOP");

        if (counterIsActive) {

            timerReset();
        }else {
            counterIsActive = true;
            //the plus 100 is to combate latancy
            countDownTimer = new CountDownTimer(seekBarTime.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    //the (int) is there to pass the time over as an int
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    Log.w(TAG, "Timer Finished");
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mPlayer.start();
                    timerReset();
                }
            }.start();
        }
    }



    //int secondsLeft passes whatever info it gets into an int and renames it that...
    public void updateTimer(int secondsLeft) {

        //defing minutes
        int minutes = secondsLeft / 60;
        //getings seconds and defining it
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);
        //Makes sure that the seconds have a double zero when it reaches a single zero
        if (seconds <= 9) {
            secondString = "0" + secondString;
        }
        //printing text to the text view
        textViewStart.setText(Integer.toString(minutes) + ":" + secondString);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBarTime = findViewById(R.id.seekBarTime);
        textViewStart = findViewById(R.id.textViewTimer);
        buttonStart = findViewById(R.id.buttonStart);

        //Sets the max value for the seek bar
        seekBarTime.setMax(600); //10 minutes
        //sets the current progress when the app starts
        seekBarTime.setProgress(60); //30 seconds
        //sets text
        textViewStart.setText("01:00");

        //Opens the seek bar listener methods
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //starting update timer an passing it the int progress
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
