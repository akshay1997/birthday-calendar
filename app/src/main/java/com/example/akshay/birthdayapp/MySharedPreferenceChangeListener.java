package com.example.akshay.birthdayapp;

/**
 * Created by akshay on 31/10/17.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Handler;
import android.os.Messenger;

public class MySharedPreferenceChangeListener implements OnSharedPreferenceChangeListener {
    private Context context;
    private Handler handler;

    public MySharedPreferenceChangeListener(Context context, Handler handler) {
        super();
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (context.getString(R.string.pref_color_key).equals(key)) {
            // set new color
            startServiceAction(MainIntentService.ACTION_CHANGE_COLOR);
        }
        else {
            // resync all events
            startServiceAction(MainIntentService.ACTION_MANUAL_COMPLETE_SYNC);
        }
    }

    /**
     * Start service with action, while executing, show progress
     */
    public void startServiceAction(String action) {
        // Send all information needed to service to do in other thread
        Intent intent = new Intent(context, MainIntentService.class);

        // Create a new Messenger for the communication back
        Messenger messenger = new Messenger(handler);
        intent.putExtra(MainIntentService.EXTRA_MESSENGER, messenger);

        intent.setAction(action);

        // start service with intent
        context.startService(intent);
    }

}