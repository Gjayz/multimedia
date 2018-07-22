package com.gjayz.multimedia.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gjayz.multimedia.R;

public class SettingsActivity extends BaseActivity {

    @Override
    public int layoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public void initActivity() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.strSettings);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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