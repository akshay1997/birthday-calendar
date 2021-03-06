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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.pavelsikun.vintagechroma.OnColorSelectedListener;


public class ExtendedPreferencesFragment extends PreferenceFragmentCompat implements OnColorSelectedListener {

    BaseActivity mActivity;
    private AccountHelper mAccountHelper;
    private ReminderPreferenceCompat reminderPreferenceCompat;
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // save prefs here
        getPreferenceManager().setSharedPreferencesName(Constants.PREFS_NAME);
        addPreferencesFromResource(R.xml.pref_preferences);

        mActivity = (BaseActivity) getActivity();


        mAccountHelper = new AccountHelper(mActivity, mActivity.mBackgroundStatusHandler);
        reminderPreferenceCompat=new ReminderPreferenceCompat(getContext());
        /*Preference forceSync = findPreference(getString(R.string.pref_force_sync_key));
        forceSync.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                mAccountHelper.manualSync();

                return false;
            }
        });*/

        Preference reminderTime0 = findPreference(getString(R.string.pref_reminder_time_key0));
        Preference reminderTime1 = findPreference(getString(R.string.pref_reminder_time_key1));
        Preference reminderTime2 = findPreference(getString(R.string.pref_reminder_time_key2));
        
        reminderTime0.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);



                return false;
            }
        });
        reminderTime1.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);



                return false;
            }
        });
        reminderTime2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);



                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(
                mActivity.mySharedPreferenceChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
                mActivity.mySharedPreferenceChangeListener);
    }

    @Override
    public void onColorSelected(@ColorInt int color) {

    }
}
