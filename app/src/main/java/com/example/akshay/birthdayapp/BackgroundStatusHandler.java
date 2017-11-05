package com.example.akshay.birthdayapp;

/**
 * Created by akshay on 31/10/17.
 */

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

public class BackgroundStatusHandler extends Handler {

    public interface StatusChangeListener {
        public void onStatusChange(boolean progress);
    }

    public static final int BACKGROUND_STATUS_HANDLER_DISABLE = 0;
    public static final int BACKGROUND_STATUS_HANDLER_ENABLE = 1;

    private WeakReference<StatusChangeListener> mListener;

    public BackgroundStatusHandler(StatusChangeListener activity) {
        mListener = new WeakReference<>(activity);
        noOfRunningBackgroundThreads = 0;
    }

    private int noOfRunningBackgroundThreads;

    @Override
    public void handleMessage(Message msg) {
        StatusChangeListener listener = mListener.get();
        final int what = msg.what;

        switch (what) {
            case BACKGROUND_STATUS_HANDLER_ENABLE:
                noOfRunningBackgroundThreads++;

                if (listener != null) {
                    listener.onStatusChange(true);
                }
                break;

            case BACKGROUND_STATUS_HANDLER_DISABLE:
                noOfRunningBackgroundThreads--;

                if (noOfRunningBackgroundThreads <= 0) {
                    if (listener != null) {
                        listener.onStatusChange(false);
                    }
                }

                break;

            default:
                break;
        }
    }

}
