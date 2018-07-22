package com.gjayz.multimedia.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.gjayz.multimedia.BuildConfig;
import com.gjayz.multimedia.R;

public class AboutFragment extends PreferenceFragment {

    private static final String KEY_SOFT_VERSION = "key_soft_version";


    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefence_about);

        initData();
    }

    private void initData() {
        findPreference(KEY_SOFT_VERSION).setSummary("当前版本：" + BuildConfig.VERSION_NAME);
    }
}