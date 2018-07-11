package com.gjayz.multimedia.utils;

import android.content.Context;
import android.util.TypedValue;

public class PixelUtil {
    public static float dipToPixel(Context context, float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }
}
