package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTime();

    }

    private void setTime() {
        Date currentTime = Calendar.getInstance().getTime();
        String formattedTime = DateFormat.getTimeInstance(TimeFormat.CLOCK_24H).format(currentTime);

        TextClock textClock = (TextClock) findViewById(R.id.textClock);
        textClock.setFormat12Hour(null);
        textClock.setFormat24Hour("hh:mm:ss a  EEE MMM d");
        Log.d("", "setTime: "+formattedTime);
    }

    public void onBroadcast(View view) {
        Intent intent=new Intent();
        intent.setAction("com.example.demo");
        sendBroadcast(intent);
        //sendBroadcast(intent);
        //Toast.makeText(this, "BroadCast. WIP", Toast.LENGTH_SHORT).show();
    }

    public void toService(View view) {

       // Toast.makeText(this, "BroadCast", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,ServiceActivity.class);
        startActivity(intent);
    }

    public void onContent(View view) {

        Toast.makeText(this, "Content to show Contacts. WIP", Toast.LENGTH_SHORT).show();
    }

    public void toBroadcast(View view) {
        Intent intent = new Intent(this, BroadCastActivity.class);
        startActivity(intent);
    }
}