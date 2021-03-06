package com.gjayz.multimedia.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.gjayz.multimedia.BuildConfig;
import com.gjayz.multimedia.R;
import com.gjayz.multimedia.ui.activity.ToolBarActivity;
import com.gjayz.multimedia.ui.utils.IntentUtil;

public class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private static final String KEY_ABOUT_AUTHOR = "key_about_author";
    private static final String KEY_SOFT_VERSION = "key_soft_version";
    private static final String KEY_DEVICE_INFO = "key_device_info";
    private static final String KEY_NETWORK = "key_network";
    private static final String KEY_TEST = "key_test";
    private static final String KEY_TEST2 = "key_test2";
    private static final String MY_GITHUB = "https://github.com/Gjayz";

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefence_about);

        Preference authorPrefence = findPreference(KEY_ABOUT_AUTHOR);
        Preference softPrefence = findPreference(KEY_SOFT_VERSION);
        Preference devicePrefence = findPreference(KEY_DEVICE_INFO);
        Preference netPrefence = findPreference(KEY_NETWORK);
        Preference testPrefence = findPreference(KEY_TEST);
        Preference test2Prefence = findPreference(KEY_TEST2);

        authorPrefence.setOnPreferenceClickListener(this);
        softPrefence.setOnPreferenceClickListener(this);
        devicePrefence.setOnPreferenceClickListener(this);
        netPrefence.setOnPreferenceClickListener(this);
        testPrefence.setOnPreferenceClickListener(this);
        test2Prefence.setOnPreferenceClickListener(this);

        authorPrefence.setSummary(MY_GITHUB);
        softPrefence.setSummary("当前版本：" + BuildConfig.VERSION_NAME);

        if (!BuildConfig.DEBUG) {
            getPreferenceScreen().removePreference(testPrefence);
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case KEY_ABOUT_AUTHOR:
                statrtGithub();
                break;
            case KEY_SOFT_VERSION:
                break;
            case KEY_DEVICE_INFO:
                IntentUtil.startDeviceInfosActivity(getActivity());
                break;
            case KEY_NETWORK:
                IntentUtil.startNetActivity(getActivity());
                break;
            case KEY_TEST:
                IntentUtil.startTestActivity(getActivity());
                break;
            case KEY_TEST2:
                IntentUtil.startTest2Activity(getActivity());
                break;
        }
        return false;
    }

    public void statrtGithub() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(MY_GITHUB));
        startActivity(intent);
    }
}