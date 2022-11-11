package com.example.project_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ToastIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
            Toast.makeText(context, "Broadcast: " +
                    "\n"+intent.getExtras().getString("message")+
                    " @ "+currentTime, Toast.LENGTH_LONG).show();
        }
    }