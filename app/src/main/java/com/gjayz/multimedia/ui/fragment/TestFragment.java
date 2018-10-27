package com.gjayz.multimedia.ui.fragment;

import android.graphics.Color;

import com.gjayz.multimedia.R;
import com.gjayz.theme.view.CricleColorView;

import butterknife.BindView;

public class TestFragment extends BaseFragment {

    @BindView(R.id.cricle_color_view)
    CricleColorView mCricleColorView;

    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    public void init() {
        mCricleColorView.setFillColor(Color.GREEN);
    }
}