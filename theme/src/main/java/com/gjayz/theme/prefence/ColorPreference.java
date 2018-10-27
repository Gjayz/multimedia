package com.gjayz.theme.prefence;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;

import com.gjayz.theme.R;
import com.gjayz.theme.view.CricleColorView;

public class ColorPreference extends Preference {

    public ColorPreference(Context context) {
        this(context, null);
    }

    public ColorPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setLayoutResource(R.layout.preference);
        setWidgetLayoutResource(R.layout.preferennce_color);
        setPersistent(false);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        CricleColorView cricleColorView = view.findViewById(R.id.color_view);
    }
}