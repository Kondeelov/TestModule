package com.kondee.testmodule.view.SwipeViewLayout;

import android.content.Context;
import android.content.res.TypedArray;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.kondee.testmodule.R;

public class SwipeViewLayout extends FrameLayout {
    private static final String TAG = "Kondee";
    private ViewDragHelper viewDragHelper;
    private View mainView;
    private View secondaryView;
    private swipeMode mode;
    private Rect mainViewRect = new Rect();
    private Rect secondaryViewRect = new Rect();
    private int horizontalDragRange;
    private ViewConfiguration viewConfiguration;
    private boolean isOpenBeforeInit = false;
    private int openLeftPosition;

    public void abort() {
        viewDragHelper.abort();
    }

    private enum swipeMode {
        behind, beside;

        public static swipeMode getMode(int position) {
            swipeMode[] values = swipeMode.values();
            return values[position];
        }
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

        if (getChildCount() > 1) {
            mainView = getChildAt(0);
            secondaryView = getChildAt(1);
        }

        if (getChildCount() == 1) {
            mainView = getChildAt(0);
        }
    }

    public void close(boolean animation) {
        setOpenBeforeInit(false);

        if (animation) {
            viewDragHelper.smoothSlideViewTo(mainView, mainViewRect.left, mainViewRect.top);

            if (stateChangeListener != null) {
                stateChangeListener.onStageChange(SwipeViewState.STATE_CLOSE);
            }
        } else {
            viewDragHelper.abort();

            mainView.layout(mainViewRect.left,
                    mainViewRect.top,
                    mainViewRect.right,
                    mainViewRect.bottom);

            secondaryView.layout(secondaryViewRect.left,
                    secondaryViewRect.top,
                    secondaryViewRect.right,
                    secondaryViewRect.bottom);
        }
        ViewCompat.postInvalidateOnAnimation(SwipeViewLayout.this);
    }

    public void open(boolean animation) {
        setOpenBeforeInit(true);

        if (animation) {
            viewDragHelper.smoothSlideViewTo(mainView, -secondaryView.getWidth(), mainViewRect.top);

            if (stateChangeListener != null) {
                stateChangeListener.onStageChange(SwipeViewState.STATE_OPEN);
            }
        } else {
            viewDragHelper.abort();

            mainView.layout(mainViewRect.left - secondaryView.getWidth(),
                    mainViewRect.top,
                    mainViewRect.right - secondaryView.getWidth(),
                    mainViewRect.bottom);

            secondaryView.layout(mainViewRect.right - secondaryView.getWidth(),
                    secondaryViewRect.top,
                    mainViewRect.right,
                    secondaryViewRect.bottom);
        }
        ViewCompat.postInvalidateOnAnimation(SwipeViewLayout.this);
    }


    private void setOpenBeforeInit(boolean b) {
        isOpenBeforeInit = b;
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

        horizontalDragRange = mainView.getWidth() - secondaryView.getWidth();

        setRect();

        if (isOpenBeforeInit) {
            open(false);
        } else {
            close(false);
        }

        openLeftPosition = -secondaryView.getWidth();
    }

    private void setRect() {
        mainViewRect.set(mainView.getLeft(),
                mainView.getTop(),
                mainView.getRight(),
                mainView.getBottom());

        if (mode == swipeMode.behind) {
            mainView.bringToFront();
        } else if (mode == swipeMode.beside) {
            secondaryView.offsetLeftAndRight(mainView.getWidth());
        }

        secondaryViewRect.set(secondaryView.getLeft(),
                secondaryView.getTop(),
                secondaryView.getRight(),
                secondaryView.getBottom());

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(SwipeViewLayout.this);
        }

    }

    private void init() {

        viewConfiguration = ViewConfiguration.get(getContext());

        viewDragHelper = ViewDragHelper.create(this, 1.0f, viewDragHelperCallback);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.SwipeViewLayout,
                defStyleAttr,
                defStyleRes);

        try {
            mode = swipeMode.getMode(a.getInt(R.styleable.SwipeViewLayout_swipeMode, 0));
        } finally {
            a.recycle();
        }
    }

    ViewDragHelper.Callback viewDragHelperCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            viewDragHelper.captureChildView(mainView, pointerId);

            return false;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            int max = Math.max(left, -secondaryViewRect.width());
            if (Math.abs(left) > viewConfiguration.getScaledTouchSlop()) {
                requestDisallowInterceptTouchEvent(true);
            }

            return Math.min(max, 0);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return horizontalDragRange;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            if (xvel < 0) {
//                viewDragHelper.settleCapturedViewAt(mainViewRect.left - secondaryViewRect.width(), mainViewRect.top);
                open(true);
            } else if (xvel > 0) {
//                viewDragHelper.settleCapturedViewAt(mainViewRect.left, mainViewRect.top);
                close(true);
            } else {
                if (Math.abs(releasedChild.getX()) >= secondaryView.getWidth() / 2) {
//                    viewDragHelper.settleCapturedViewAt(mainViewRect.left - secondaryViewRect.width(), mainViewRect.top);
                    open(true);
                } else {
//                    viewDragHelper.settleCapturedViewAt(mainViewRect.left, mainViewRect.top);
                    close(true);
                }
            }

            invalidate();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);

            switch (state) {
                case 0:
                    if (mainView.getLeft() == openLeftPosition) {
//                        open
//                        setOpenBeforeInit(true);
                    } else {
//                        close
//                        setOpenBeforeInit(false);
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            if (mode == swipeMode.beside) {
                secondaryView.offsetLeftAndRight(dx);
            }

            ViewCompat.postInvalidateOnAnimation(SwipeViewLayout.this);
        }
    };

    /***********
     * Listener
     ***********/
    onStateChangeListener stateChangeListener;

    public interface onStateChangeListener {
        void onStageChange(SwipeViewState state);

    }

    public void setOnStateChangeListener(onStateChangeListener stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }
}
