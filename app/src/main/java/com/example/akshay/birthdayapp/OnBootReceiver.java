package com.example.akshay.birthdayapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by akshay on 4/11/17.
 */

public class OnBootReceiver extends BroadcastReceiver {
    private static final int PERIOD=10000;  // 10sec

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager mgr=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(context, AlarmReceiver.class);
        PendingIntent pi= PendingIntent.getBroadcast(context, 0, i, 0);

        mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), PERIOD, pi);
    }
}
