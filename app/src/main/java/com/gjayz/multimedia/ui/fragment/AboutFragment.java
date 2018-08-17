package com.gjayz.multimedia.ui.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.gjayz.multimedia.BuildConfig;
import com.gjayz.multimedia.R;
import com.gjayz.multimedia.ui.activity.ToolBarActivity;

public class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private static final String KEY_ABOUT_AUTHOR = "key_about_author";
    private static final String KEY_SOFT_VERSION = "key_soft_version";
    private static final String KEY_DEVICE_INFO = "key_device_info";
    private static final String KEY_NETWORK = "key_network";

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefence_about);

        initData();

        Preference devicePrefence = findPreference(KEY_DEVICE_INFO);
        devicePrefence.setOnPreferenceClickListener(this);
    }

    private void initData() {
        findPreference(KEY_SOFT_VERSION).setSummary("当前版本：" + BuildConfig.VERSION_NAME);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()){
            case KEY_ABOUT_AUTHOR:
                break;
            case KEY_SOFT_VERSION:
                break;
            case KEY_DEVICE_INFO:
                startActivity(ToolBarActivity.newIntent(getActivity(), getString(R.string.strDeviceInfo),ToolBarActivity.TYPE_DEVICEINFO));
                break;
            case KEY_NETWORK:
                break;
        }
        return false;
    }
}