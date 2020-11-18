//DEBAYAN MAJUMDER 2020
package com.venomous.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int max=600; //maximum time limit in seconds
    int timerValue=30;
    TextView timeView;
    Button myButton;
    SeekBar time;
    CountDownTimer myTimer;
    MediaPlayer myAudio;
    ImageView egg;
    boolean activeCounter=false;
    boolean isBeat=true;

    public void startTimer(View view)
    {
        egg = findViewById(R.id.eggImage);
        if(activeCounter) //reset code
        {
            activeCounter=false;
            time.setEnabled(true);
            time.getThumb().mutate().setAlpha(255);
            myButton.setText("Get Cooking");
            timeView.setText("0:30");
            time.setProgress(30);
            myTimer.cancel();
            myAudio.stop();
            egg.animate().scaleX(1f).scaleY(1f).setDuration(250);
            timeView.animate().scaleX(1f).scaleY(1f).setDuration(250);
            isBeat=true;

        } else { //running onClick button code
            activeCounter = true;
            time.setEnabled(false);
            time.getThumb().mutate().setAlpha(0);

            myButton = findViewById(R.id.button);
            timeView = findViewById(R.id.timerOutput);
            Log.i("Timer: ", Integer.toString(timerValue));

            //if(activeCounter)
            egg.animate().scaleX(0.8f).scaleY(0.8f).setDuration(500);
            timeView.animate().scaleX(1.2f).scaleY(1.2f).setDuration(500);

            myButton.setText("STOP!");

            myTimer = new CountDownTimer(timerValue * 1000 + 100, 1000) {
                @Override
                public void onTick(long millis) {
                    updateTimer((int) millis / 1000);
                    if(isBeat){
                        egg.animate().scaleX(0.8f).scaleY(0.8f).setDuration(500);
                        isBeat=false;}
                    else{
                        egg.animate().scaleX(1f).scaleY(1f).setDuration(500);
                        isBeat=true;}
                }

                @Override
                public void onFinish() {
                    Log.i("We are done", "No more countdown");
                    playAudio();
                }
            }.start();
        }
    }

    public void playAudio()
    {
        myAudio = MediaPlayer.create(this,R.raw.servicebell);
        myAudio.start();
        myAudio.setLooping(true);
    }

    public void updateTimer(int seconds)
    {
        int min=seconds/60;
        int sec=seconds-(min*60);
        String str = Integer.toString(sec);

        if(sec<10)
            str="0"+sec;

        timeView.setText(min+":"+str);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeView=findViewById(R.id.timerOutput);
        time = findViewById(R.id.timerSeekbar);
        myAudio = MediaPlayer.create(this,R.raw.servicebell);

        time.setMax(max);
        time.setProgress(30);

        time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Position: ", Integer.toString(progress));
                updateTimer(progress);
                timerValue=progress;
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