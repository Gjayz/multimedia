package com.gjayz.multimedia.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class BaseSwipeLayout extends FrameLayout {

    private View mDragView;
    private ViewDragHelper mViewDragHelper;

    private Point mAutoBackOrignalPoint = new Point();
    private Point mCurArrivePoint = new Point();

    private int mCurEdgeFlag = ViewDragHelper.EDGE_LEFT;
    private int mSwipeEdge = ViewDragHelper.EDGE_LEFT;

    private OnFinishScroll mFinishScroll;

    public interface OnFinishScroll {
        void complete();
    }

    public BaseSwipeLayout(Context context) {
        this(context, null);
    }

    public BaseSwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaseSwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnFinishScroll(OnFinishScroll onFinishScroll) {
        mFinishScroll = onFinishScroll;
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return false;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                mCurArrivePoint.x = left;
                //允许左右触发滑动，否则return 0
                if (mCurEdgeFlag != ViewDragHelper.EDGE_BOTTOM) {
                    return left;
                } else {
                    return 0;
                }
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                mCurArrivePoint.y = top;
                //允许底部触发滑动，否则return 0
                if (mCurEdgeFlag == ViewDragHelper.EDGE_BOTTOM) {
                    return top;
                } else {
                    return 0;
                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                switch (mCurEdgeFlag) {
                    case ViewDragHelper.EDGE_LEFT:
                        //水平滑动超过一半，触发结束
                        if (mCurArrivePoint.x > getWidth() / 2) {
                            mViewDragHelper.settleCapturedViewAt(getWidth(), mAutoBackOrignalPoint.y);
                        } else {
                            mViewDragHelper.settleCapturedViewAt(mAutoBackOrignalPoint.x, mAutoBackOrignalPoint.y);
                        }
                        break;
                    case ViewDragHelper.EDGE_RIGHT:
                        //水平滑动超过一半，触发结束
                        if (mCurArrivePoint.x < -getWidth() / 2) {
                            mViewDragHelper.settleCapturedViewAt(-getWidth(), mAutoBackOrignalPoint.y);
                        } else {
                            mViewDragHelper.settleCapturedViewAt(mAutoBackOrignalPoint.x, mAutoBackOrignalPoint.y);
                        }
                        break;
                    case ViewDragHelper.EDGE_BOTTOM:
                        //垂直滑动超过一半，触发结束
                        if (mCurArrivePoint.y < -getHeight() / 2) {
                            mViewDragHelper.settleCapturedViewAt(mAutoBackOrignalPoint.x, -getHeight());
                        } else {
                            mViewDragHelper.settleCapturedViewAt(mAutoBackOrignalPoint.x, mAutoBackOrignalPoint.y);
                        }
                        break;
                }
                mCurArrivePoint.x = 0;
                mCurArrivePoint.y = 0;
                invalidate();
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                switch (mCurEdgeFlag) {
                    case ViewDragHelper.EDGE_LEFT:
                        if (left >= getWidth()) {
                            if (mFinishScroll != null) {
                                mFinishScroll.complete();
                            }
                        }
                        break;
                    case ViewDragHelper.EDGE_RIGHT:
                        if (left <= -getWidth()) {
                            if (mFinishScroll != null) {
                                mFinishScroll.complete();
                            }
                        }
                        break;
                    case ViewDragHelper.EDGE_BOTTOM:
                        if (top <= -getHeight()) {
                            if (mFinishScroll != null) {
                                mFinishScroll.complete();
                            }
                        }
                        break;
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                mCurEdgeFlag = edgeFlags;
                if (mDragView == null)
                    mDragView = getChildAt(0);
                mViewDragHelper.captureChildView(mDragView, pointerId);
            }
        });

        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    public void setSwipeEdge(int swipeEdge) {
        mSwipeEdge = swipeEdge;
    }
}