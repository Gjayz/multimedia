package com.gjayz.multimedia.ui.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.gjayz.multimedia.R;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefence_settings);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }
}