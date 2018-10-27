package com.gjayz.theme.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.gjayz.theme.R;

public class CricleColorView extends FrameLayout {

    private static final float DEFAULT_STROKE_WIDTH = 6.0f;
    private static final int DEFAULT_STROKE_COLOR = Color.BLACK;
    private static final int DEFAULT_FILL_COLOR = Color.RED;
    private Paint strokePaint;
    private Paint fillPaint;
    private int strokeColor = Color.BLACK;
    private int fillColor = Color.RED;
    private float strokeWidth = DEFAULT_STROKE_WIDTH;

    public CricleColorView(Context context) {
        this(context, null);
    }

    public CricleColorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CricleColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setWillNotDraw(false);

        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CricleColorView, 0, 0);
            strokeWidth = typedArray.getDimension(R.styleable.CricleColorView_strokeWidth, DEFAULT_STROKE_WIDTH);
            strokeColor = typedArray.getColor(R.styleable.CricleColorView_strokeWidth, DEFAULT_STROKE_COLOR);
            fillColor = typedArray.getColor(R.styleable.CricleColorView_strokeWidth, DEFAULT_FILL_COLOR);
            typedArray.recycle();
        }

        strokePaint = new Paint();
        strokePaint.setStrokeWidth(strokeWidth);
        strokePaint.setColor(strokeColor);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);

        fillPaint = new Paint();
        fillPaint.setColor(fillColor);
        fillPaint.setAntiAlias(true);
        fillPaint.setStyle(Paint.Style.FILL);
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        float radius = Math.min(measuredWidth, measuredHeight) / 2f - strokeWidth;
        canvas.drawCircle(measuredWidth / 2f, measuredHeight / 2f, radius, fillPaint);
        canvas.drawCircle(measuredWidth / 2f, measuredHeight / 2f, radius, strokePaint);
    }
}