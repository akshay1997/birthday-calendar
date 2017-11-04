package com.example.akshay.birthdayapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by akshay on 4/11/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    String TAG="ALARM";
    @Override
    public void onReceive(Context context, Intent intent) {





        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent(context, ExtendedPreferencesFragment.class);

        Log.d(TAG,"Alarm");
        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(context).setContentTitle("Birthday Alarm").setSmallIcon(R.drawable.play_icon)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Wish your friend happy birthday!"))
                .setContentText("Birthday Today");
        TaskStackBuilder stackBuilder = TaskStackBuilder.
                create(context);
        stackBuilder.addParentStack(ExtendedPreferencesFragment.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        alamNotificationBuilder.setContentIntent(resultPendingIntent);
        manager.notify(1, alamNotificationBuilder.build());
        Toast.makeText(context, "Alarm Ringing...!!!", Toast.LENGTH_LONG).show();
    }
}