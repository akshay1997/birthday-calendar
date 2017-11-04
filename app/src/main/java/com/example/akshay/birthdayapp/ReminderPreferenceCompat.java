/*
 * Copyright (C) 2012-2016 Dominik Schürmann <dominik@dominikschuermann.de>
 *
 * This file is part of Birthday Adapter.
 *
 * Birthday Adapter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Birthday Adapter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Birthday Adapter.  If not, see <http://www.gnu.com.example.snigdha.birthdaycalendarapp.org/licenses/>.
 *
 */

package com.example.akshay.birthdayapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;


public class ReminderPreferenceCompat extends Preference {
    String TAG="ABC";
    private int lastMinutes = 0;
    private TimePicker picker = null;
    private Spinner spinner = null;
    private Context context;
    private static final int ONE_DAY_MINUTES = 24 * 60;
    private static final int[] DAY_BASE_MINUTES = {-ONE_DAY_MINUTES, 0, ONE_DAY_MINUTES,
            2 * ONE_DAY_MINUTES, 4 * ONE_DAY_MINUTES, 6 * ONE_DAY_MINUTES, 9 * ONE_DAY_MINUTES,
            13 * ONE_DAY_MINUTES};


    public ReminderPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
    }

    public ReminderPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    public ReminderPreferenceCompat(Context context, AttributeSet attrs) {
        super(context, attrs);this.context=context;
    }

    public ReminderPreferenceCompat(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onClick() {
        super.onClick();
        click();

    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return (a.getString(index));
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        // convert default to Integer
        Integer defaultInt = null;
        if (defaultValue == null) {
            defaultInt = 0;
        } else if (defaultValue instanceof Number) {
            defaultInt = (Integer) defaultValue;
        } else {
            defaultInt = Integer.valueOf(defaultValue.toString());
        }

        Integer time = null;
        if (restoreValue) {
            time = getPersistedInt(defaultInt);
        } else {
            time = defaultInt;
        }

        lastMinutes = time;
    }

    private void click() {

        //@SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.pref_reminder, null);


        spinner = (Spinner) view.findViewById(R.id.pref_reminder_spinner);
        picker = (TimePicker) view.findViewById(R.id.pref_reminder_timepicker);
        Toast.makeText(getContext(),"Hello0",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"asdfg");
        // populate spinner with entries
        /*ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.pref_reminder_time_drop_down, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);*/

        // set 24h format of date picker based on Android's preference
        if (DateFormat.is24HourFormat(getContext())) {
            picker.setIs24HourView(true);
        }
        Toast.makeText(context,"Hello1",Toast.LENGTH_SHORT);
        Log.d(TAG,"hello1");
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Calendar cal = Calendar.getInstance();
                //cal.set(picker.getCurrentHour(),picker.getCurrentMinute(),00);
                Log.d(TAG, "hello2");
                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                cal.set(Calendar.HOUR_OF_DAY, picker.getHour());
                cal.set(Calendar.MINUTE, picker.getMinute());
                setAlarm(cal);
                Log.d(TAG, "hello3");
                //save(true);
            }
        });
        //bind();

        //alert.create().show();
    }

    private void bind() {
        // select day of reminder based on DAY_BASE_MINUTES
        /*int daySelection = 0;
        for (int i = 0; i < DAY_BASE_MINUTES.length; i++) {
            if (lastMinutes >= DAY_BASE_MINUTES[i]) {
                daySelection = i;
            }
        }
        spinner.setSelection(daySelection);
        int dayMinutes = ONE_DAY_MINUTES - (lastMinutes - DAY_BASE_MINUTES[daySelection]);
        Log.d(Constants.TAG, "dayMinutes: " + dayMinutes);

        // select day minutes for this specific day
        int hour = dayMinutes / 60;
        int minute = dayMinutes % 60;
        picker.setCurrentHour(hour);
        picker.setCurrentMinute(minute);*/
    }
    private void setAlarm(Calendar targetCal)
    {

        Log.d(TAG,"a1");
        Intent alarmintent = new Intent(ReminderPreferenceCompat.this,AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(ReminderPreferenceCompat.this, 0, alarmintent, 0);
        Log.d(TAG, "a4");
        AlarmManager alarmManager = (AlarmManager) ReminderPreferenceCompat.this.getSystemService(ALARM_SERVICE);
        Log.d(TAG,"a2");
        alarmManager.set(AlarmManager.RTC, targetCal.getTimeInMillis(), sender);
        Log.d(TAG,"a3");

    }
    private void save(boolean positiveResult) {
        if (positiveResult) {
            int dayBase = DAY_BASE_MINUTES[spinner.getSelectedItemPosition()];
            int dayTime = picker.getCurrentMinute() + picker.getCurrentHour() * 60;

            lastMinutes = dayBase + ONE_DAY_MINUTES - dayTime;

            if (callChangeListener(lastMinutes)) {
                Log.d(Constants.TAG, "persist time: " + lastMinutes);

                persistInt(lastMinutes);
            }
        }
    }

}
