package com.example.akshay.birthdayapp; /**
 * Created by akshay on 31/10/17.
 */

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;



import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity implements BackgroundStatusHandler.StatusChangeListener {

    public BackgroundStatusHandler mBackgroundStatusHandler = new BackgroundStatusHandler(this);

    public MySharedPreferenceChangeListener mySharedPreferenceChangeListener;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progress_spinner);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mySharedPreferenceChangeListener = new MySharedPreferenceChangeListener(this,
                mBackgroundStatusHandler);

        /*
         * Show workaround dialog for Android bug http://code.google.com/p/android/issues/detail?id=34880
         * Bug exists on Android 4.1 (SDK 16) and on some phones like Galaxy S4
         */

    }

    public void setIndeterminateProgress(boolean visible) {
        progressBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BasePreferenceFragment(), getString(R.string.tab_main));

        adapter.addFragment(new ExtendedPreferencesFragment(), getString(R.string.tab_preferences));
        adapter.addFragment(new AccountListFragment(), getString(R.string.tab_accounts));
        adapter.addFragment(new AboutFragment(), getString(R.string.tab_about));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onStatusChange(boolean progress) {
        setIndeterminateProgress(progress);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public boolean isPackageInstalled(String targetPackage) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

}