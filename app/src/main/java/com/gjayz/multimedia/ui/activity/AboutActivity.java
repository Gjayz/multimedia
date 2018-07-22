package com.gjayz.multimedia.ui.activity;

import android.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.ui.fragment.AboutFragment;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.about_framelayout)
    FrameLayout mFrameLayout;

    @Override
    public int layoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.strAbout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initFragment();
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

    private void initFragment(){
        AboutFragment aboutFragment = AboutFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.about_framelayout, aboutFragment)
                .commit();
    }
}