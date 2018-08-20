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

public class ToolBarActivity extends BaseActivity {

    private static final String KEY_TITLE = "key_title";
    private static final String KEY_TYPE = "key_type";

    public static final int TYPE_ABOUT = 0;
    public static final int TYPE_DEVICEINFO = 1;
    public static final int TYPE_NET = 2;
    public static final int TYPE_SETTINGS = 3;
    public static final int TYPE_TEST = 4;

    private int mType;

    @Override
    public int layoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initActivity() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(KEY_TITLE);
        mType = intent.getIntExtra(KEY_TYPE, TYPE_ABOUT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        switch (mType) {
            case TYPE_SETTINGS:
                SettingsFragment settingsFragment = SettingsFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.about_framelayout, settingsFragment)
                        .commit();
                break;
            case TYPE_ABOUT:
                AboutFragment aboutFragment = AboutFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.about_framelayout, aboutFragment)
                        .commit();
                break;
            case TYPE_DEVICEINFO:
                DeviceInfoFragment deviceInfoFragment = DeviceInfoFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.about_framelayout, deviceInfoFragment)
                        .commit();
                break;
            case TYPE_NET:
                NetFragment netFragment = NetFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.about_framelayout, netFragment)
                        .commit();
                break;
            case TYPE_TEST:
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

    public static Intent newIntent(Context context, String title, int tpye) {
        Intent intent = new Intent(context, ToolBarActivity.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_TYPE, tpye);
        return intent;
    }
}