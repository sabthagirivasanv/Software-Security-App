package com.example.project_1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class DateService extends Service {
    public static final String TIMER_UPDATE = "TIMER_UPDATE";
    private static final String TAG = "DataService";
    private final IBinder mBinder = new LocalBinder();
    private boolean isRunning  = false;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    public class LocalBinder extends Binder {
        DateService getService() {
            // Return this instance of LocalService so clients can call public
//            methods
            return DateService.this;
        }
    }
    public DateService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning=true;
        mHandlerThread = new HandlerThread("LocalServiceThread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                int i=10;
                while (i > 0 && ServiceActivity.isRunning) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent();
                            intent.setAction(TIMER_UPDATE);
                            sendBroadcast(intent);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        i--;
                    }
                }
            }
        })
        ;
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        try {
            mHandlerThread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Timer Service Stopped", Toast.LENGTH_LONG).show();
        Log.i(TAG, " service stopped...");
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

}