package com.gjayz.multimedia.ui.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.gjayz.multimedia.R;
import com.gjayz.theme.view.ColorChooserDialog;

public class Test2Fragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private static final String KEY_CHOOSE_COLOR = "key_choose_color";

    public static Test2Fragment newInstance() {
        return new Test2Fragment();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefence_test);

        Preference key_choose_color = findPreference(KEY_CHOOSE_COLOR);
        key_choose_color.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        switch (key){
            case KEY_CHOOSE_COLOR:
                ColorChooserDialog build = new ColorChooserDialog.Builder()
                        .build();
                build.show(getFragmentManager(), KEY_CHOOSE_COLOR);
                break;
        }
        return false;
    }
}
