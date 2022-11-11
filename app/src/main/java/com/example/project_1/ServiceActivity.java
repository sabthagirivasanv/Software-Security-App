package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ServiceActivity extends AppCompatActivity {

    static boolean isRunning = false;

    private final BroadcastReceiver serviceMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String currentTime = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(new Date());
            tvServiceMessage.setText("Current Time: "+currentTime);
        }
    };

    TextView tvServiceMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        tvServiceMessage = findViewById(R.id.tvServiceMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(serviceMessageReceiver);
        super.onStop();
    }

    public void onStartService(View view) {
        isRunning = true;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DateService.TIMER_UPDATE);
        registerReceiver(serviceMessageReceiver, intentFilter);
        //Start our own service
        Intent intent = new Intent(ServiceActivity.this, DateService.class);
        startService(intent);
        super.onStart();

        startService(intent);
    }

    public void onStopService(View view) {
        Intent intent = new Intent(this, DateService.class);
        isRunning = false;
        stopService(intent);

    }
}