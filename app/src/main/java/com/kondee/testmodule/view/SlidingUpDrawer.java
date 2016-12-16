package com.kondee.testmodule.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.kondee.testmodule.R;


public class SlidingUpDrawer extends FrameLayout {
    private static final String TAG = "Kondee";
    private float peekHeight;
    private int screenHeight;
    private float y;
    private float eventX;
    private float eventY;
    private float locationY;
    private ViewGroup slidingPanel;
    private boolean isExpended = false;
    private ObjectAnimator animator;
    private VelocityTracker velocityTracker;

    public SlidingUpDrawer(Context context) {
        super(context);
        init();
    }

    public SlidingUpDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0, 0);
    }

    public SlidingUpDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public SlidingUpDrawer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingUpDrawer,
                defStyleAttr,
                defStyleRes);
        try {
            peekHeight = a.getDimension(R.styleable.SlidingUpDrawer_peekHeight, dp2px(48));
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        y = getY();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

//        for (int i = 0; i < getChildCount(); i++) {
//            View childView = getChildAt(i);
//        }
        slidingPanel = (ViewGroup) getChildAt(getChildCount() - 1);

        slidingPanel.setPadding(0, 0, 0, (int) (getPaddingBottom() + dp2px(24)));

        slidingPanel.setY(getMeasuredHeight() - slidingPanel.getChildAt(0).getMeasuredHeight());
        slidingPanel.setX((getMeasuredWidth() - slidingPanel.getMeasuredWidth()) / 2);

        slidingPanel.getChildAt(0).setOnTouchListener(onTouchListener);

//        if (y == 0)
//            this.setY(getY() + (getMeasuredHeight() - getChildAt(0).getMeasuredHeight()));

//        this.setY(getRootView().getHeight() - (getMeasuredHeight() - getChildAt(0).getMeasuredHeight()));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        initVelocityTracker(ev);

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        return super.onTouchEvent(event);
        return true;
    }

    private void initVelocityTracker(MotionEvent ev) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(ev);
    }

    private void removeVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.clear();
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    private float dp2px(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return dp * displayMetrics.scaledDensity;
    }

    /***********
     * Listener
     ***********/

    private float oldEventY;
    private long oldEventTime;

    OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, final MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    oldEventY = 0;
                    oldEventTime = 0;

                    eventX = event.getRawX();
                    eventY = event.getRawY();
                    locationY = slidingPanel.getY();
//                    Log.d(TAG, "onTouchEvent: Down" + slidingPanel.getY());
//                    requestDisallowInterceptTouchEvent(true);
                    oldEventY = eventY;
                    oldEventTime = event.getDownTime();
                    return true;
                case MotionEvent.ACTION_MOVE:
//                TODO...:
//                    slidingPanel.setY(locationY - (eventY - event.getRawY()));
                    Log.d(TAG, "onTouch: " + (slidingPanel.getY() + (event.getRawY() - eventY) + " " + (getMeasuredHeight() - slidingPanel.getChildAt(0).getY())));


                    if (slidingPanel.getY() - (eventY - event.getRawY()) <= getMeasuredHeight() - slidingPanel.getMeasuredHeight()) {
                        slidingPanel.setY(getMeasuredHeight() - slidingPanel.getMeasuredHeight());
                    } else if (slidingPanel.getY() + (event.getRawY() - eventY) >= getMeasuredHeight() - slidingPanel.getChildAt(0).getMeasuredHeight()) {
                        slidingPanel.setY(getMeasuredHeight() - slidingPanel.getChildAt(0).getMeasuredHeight());
                    } else {
                        slidingPanel.setY(locationY - (eventY - event.getRawY()));
                    }

                    return true;

                case MotionEvent.ACTION_UP:

//                    oldEventY = 0;
//                    Log.d(TAG, "onTouchEvent: Up");

                    if (event.getEventTime() - event.getDownTime() <= 100) {
                        if (isExpended) {
                            animator = ObjectAnimator.ofFloat(slidingPanel, View.TRANSLATION_Y, getMeasuredHeight() - slidingPanel.getChildAt(0).getMeasuredHeight());
                            isExpended = false;
                        } else {
                            animator = ObjectAnimator.ofFloat(slidingPanel, View.TRANSLATION_Y, getMeasuredHeight() - slidingPanel.getMeasuredHeight());
                            isExpended = true;
                        }
                        animator.setDuration(250);
                        animator.setInterpolator(new DecelerateInterpolator(1.0f));
                        animator.start();
                    }

//                    Log.d(TAG, "isExpended: " + isExpended);

                    return true;
            }

            return false;
        }
    };
}
