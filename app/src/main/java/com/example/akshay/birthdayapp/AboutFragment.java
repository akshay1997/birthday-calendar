package com.example.akshay.birthdayapp;

/**
 * Created by akshay on 31/10/17.
 */

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);

//        TextView versionText = (TextView) view.findViewById(R.id.about_version);
  //      versionText.setText(getString(R.string.about_version) + " " + getVersion());

        TextView aboutTextView = (TextView) view.findViewById(R.id.about_text);
        aboutTextView.setText(R.string.about_text);
        aboutTextView.setTextColor(getResources().getColor(android.R.color.black));

        return view;
    }

    /**
     * Get the current package version.
     *
     * @return The current version.
     */
    /*private String getVersion() {
        String result = "";
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);

            result = String.format("%s (%s)", info.versionName, info.versionCode);
        } catch (NameNotFoundException e) {
            Log.w(SyncStateContract.Constants.TAG, "Unable to get application version", e);
            result = "Unable to get application version.";
        }

        return result;
    }*/

}