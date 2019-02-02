package com.example.week3lab;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;

    Button dialerScreenViewBtn;
    TextView clockDisplayTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialerScreenViewBtn = findViewById(R.id.dialerScreenBtn);
        clockDisplayTxt = findViewById(R.id.clockTextView);

        handler = new Handler(){
            public void handleMessage (Message msg){
                int hour = msg.getData().getInt("hours");
                int minute = msg.getData().getInt("minutes");
                boolean tick = msg.getData().getBoolean("ticks");
                String display;
                if (tick){
                     display = hour + ":" + minute;
                }else{
                    display = hour + " " + minute;
                }
                clockDisplayTxt.setText(display);
            }
        };

        runnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
            }
        };

        //start timer thread
        handler.postDelayed(runnable,1000);
    }

    private void updateTime() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        boolean tick;
        if (seconds % 2 == 0){
            tick = true;
        }else{
            tick = false;
        }

        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("hours",hours);
        bundle.putInt("minutes",minutes);
        bundle.putBoolean("ticks",tick);

        message.setData(bundle);
        handler.sendMessage(message);
        handler.postDelayed(runnable,1000);
    }
}
