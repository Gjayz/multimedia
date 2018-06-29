package com.gjayz.multimedia.ui;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.utils.StatusBarUtils;

public class MusicPlayActivity extends BaseActivity {

    @Override
    public int layoutId() {
        return R.layout.activity_music_play;
    }

    @Override
    public void initActivity() {
    }

    public void adjustStatusBar(){
        StatusBarUtils.setTranslucentStatus(this, R.color.transparent);
        StatusBarUtils.needStatusBarPadding(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setStatusBatTextColor(this, true);
    }
}