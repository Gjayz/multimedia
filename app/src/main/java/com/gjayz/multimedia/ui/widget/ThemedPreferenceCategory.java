package com.gjayz.multimedia.ui.widget;

import android.content.Context;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.gjayz.multimedia.R;

public class ThemedPreferenceCategory extends PreferenceCategory {

    public ThemedPreferenceCategory(Context context) {
        super(context);
    }

    public ThemedPreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public ThemedPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
//        TextView textView = view.findViewById(android.R.id.title);
    }
}
