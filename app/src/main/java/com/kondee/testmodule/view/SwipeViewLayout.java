package com.kondee.testmodule.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


public class SwipeViewLayout extends FrameLayout {
    private static final String TAG = "Kondee";
    private ViewDragHelper viewDragHelper;
    private View mainView;
    private View secondaryView;
    private swipeMode mode = swipeMode.behind;
    private Rect mainViewRect = new Rect();
    private Rect secondaryViewRect = new Rect();
    private int horizontalDragRange;


    private enum swipeMode {
        behind, beside
    }


    public SwipeViewLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public SwipeViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0, 0);
    }

    public SwipeViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SwipeViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate: ");

        if (getChildCount() > 1) {
            mainView = getChildAt(0);
            secondaryView = getChildAt(1);
        }

        if (mode == swipeMode.behind)
            mainView.bringToFront();
        else if (mode == swipeMode.beside) {
            secondaryView.setX(mainView.getWidth());
        }

        mainView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "onClick: "+mainViewRect.left);
//                viewDragHelper.smoothSlideViewTo(mainView, 0, mainViewRect.top);
//                ViewCompat.postInvalidateOnAnimation(SwipeViewLayout.this);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        viewDragHelper.processTouchEvent(event);

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mainViewRect.set(mainView.getLeft(),
                mainView.getTop(),
                mainView.getRight(),
                mainView.getBottom());

        secondaryViewRect.set(secondaryView.getLeft(),
                secondaryView.getTop(),
                secondaryView.getRight(),
                secondaryView.getBottom());

        horizontalDragRange = mainView.getWidth() - secondaryView.getWidth();
    }

//    @Override
//    public void computeScroll() {
//        super.computeScroll();
//        viewDragHelper.continueSettling(true);
//        ViewCompat.postInvalidateOnAnimation(mainView);
//    }

    private void init() {

        viewDragHelper = ViewDragHelper.create(this, 1.0f, viewDragHelperCallback);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

    }

    ViewDragHelper.Callback viewDragHelperCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            viewDragHelper.captureChildView(mainView, pointerId);
            return false;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
//            Log.d(TAG, "clampViewPositionHorizontal: " + left);

            int max = Math.max(left, secondaryViewRect.left - mainViewRect.right);
            Log.d(TAG, "clampViewPositionHorizontal: " + max);

            return Math.min(max, mainViewRect.right - secondaryViewRect.right);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return horizontalDragRange;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

        }
    };
}
