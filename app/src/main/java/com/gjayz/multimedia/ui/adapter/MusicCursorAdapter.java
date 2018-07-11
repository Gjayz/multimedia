package com.gjayz.multimedia.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;

public class MusicCursorAdapter extends ResourceCursorAdapter {

    public MusicCursorAdapter(Context context, int layout, Cursor c) {
        super(context, layout, c);
    }

    public MusicCursorAdapter(Context context, int layout, Cursor c, boolean autoRequery) {
        super(context, layout, c, autoRequery);
    }

    public MusicCursorAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}