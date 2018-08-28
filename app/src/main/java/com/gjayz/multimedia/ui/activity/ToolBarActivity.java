package com.gjayz.multimedia.ui.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.ui.fragment.AboutFragment;
import com.gjayz.multimedia.ui.fragment.DeviceInfoFragment;
import com.gjayz.multimedia.ui.fragment.NetFragment;
import com.gjayz.multimedia.ui.fragment.SettingsFragment;
import com.gjayz.multimedia.ui.fragment.TestFragment;
import com.gjayz.multimedia.ui.utils.IntentUtil;

public class ToolBarActivity extends BaseActivity {



    private int mType;

    @Override
    public int layoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initActivity() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(IntentUtil.KEY_TITLE);
        mType = intent.getIntExtra(IntentUtil.KEY_TYPE, IntentUtil.TYPE_ABOUT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        switch (mType) {
            case IntentUtil.TYPE_SETTINGS:
                SettingsFragment settingsFragment = SettingsFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.about_framelayout, settingsFragment)
                        .commit();
                break;
            case IntentUtil.TYPE_ABOUT:
                AboutFragment aboutFragment = AboutFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.about_framelayout, aboutFragment)
                        .commit();
                break;
            case IntentUtil.TYPE_DEVICEINFO:
                DeviceInfoFragment deviceInfoFragment = DeviceInfoFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.about_framelayout, deviceInfoFragment)
                        .commit();
                break;
            case IntentUtil.TYPE_NET:
                NetFragment netFragment = NetFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.about_framelayout, netFragment)
                        .commit();
                break;
            case IntentUtil.TYPE_TEST:
                TestFragment testFragment = TestFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.about_framelayout, testFragment)
                        .commit();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}