package com.gjayz.multimedia.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.swipeback.SwipeBackHelper;
import com.gjayz.multimedia.utils.StatusBarUtils;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true)
                .setSwipeRelateOffset(300);
        setContentView(layoutId());
        initActivity();
        adjustStatusBar();
    }

    public abstract int layoutId();

    public abstract void initActivity();

    public void adjustStatusBar(){
//        StatusBarUtils.setTranslucentStatus(this, R.color.transparent);
//        StatusBarUtils.needStatusBarPadding(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }
}
