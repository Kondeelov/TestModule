package com.kondee.testmodule.service;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.kondee.testmodule.R;
import com.kondee.testmodule.view.AssistiveTouch.AssistiveTouchView;

/**
 * Created by Kondee on 7/18/2017.
 */

public class OverlayShowingService extends Service {

    private static final String TAG = "Kondee";
    private WindowManager windowManager;
    private AssistiveTouchView assisistiveTouchView;
    private GestureDetector gestureDetector;
    private WindowManager.LayoutParams layoutParams;
    private int jumpTapTimeout;
    private ViewConfiguration viewConfiguration;
    private FrameLayout contentContainer;
    private DevicePolicyManager mDPM;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        gestureDetector = new GestureDetector(this, simpleOnGestureListener);
        viewConfiguration = ViewConfiguration.get(this);
        jumpTapTimeout = ViewConfiguration.getJumpTapTimeout();
        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (assisistiveTouchView == null) {
            initFloatingItem();
        } else {
            stopSelf();
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (assisistiveTouchView != null) {
            windowManager.removeView(contentContainer);
            contentContainer = null;
        }
    }

    private void initFloatingItem() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        assisistiveTouchView = new AssistiveTouchView(this);
        assisistiveTouchView.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_button_transparent_pressed));
        assisistiveTouchView.setColor(ContextCompat.getColor(this, R.color.colorWhite));

        contentContainer = new FrameLayout(this);

        layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PRIORITY_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        layoutParams.gravity = Gravity.START | Gravity.CENTER;

        assisistiveTouchView.setOnTouchListener(new View.OnTouchListener() {

            private int action;
            private ValueAnimator translationX;
            private int initialX;
            private int initialY;
            float initialTouchX;
            float initialTouchY;
            Point outSize = new Point();

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                boolean a = false;
//                boolean b = gestureDetector.onTouchEvent(event);

                translationX = new ValueAnimator();
                translationX.setDuration(350);
                translationX.setInterpolator(new DecelerateInterpolator());

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = layoutParams.x;
                        initialY = layoutParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        action = event.getAction();

                        a = true;
////                        return true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        layoutParams.x = (int) (initialX + (event.getRawX() - initialTouchX));
                        layoutParams.y = (int) (initialY + (event.getRawY() - initialTouchY));
                        windowManager.updateViewLayout(contentContainer, layoutParams);

                        action = event.getAction();

                        a = true;
//                        return true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (action == MotionEvent.ACTION_DOWN && (event.getEventTime() - event.getDownTime()) <= jumpTapTimeout) {
//                            TransitionManager.beginDelayedTransition(contentContainer);

//                            assisistiveTouchView.setVisibility(View.GONE);
//                            contentContainer.removeAllViews();

//                            hideAssistiveTouchView();

                            /***Home Key Action***/
//                            Intent intent = new Intent(Intent.ACTION_MAIN);
//                            intent.addCategory(Intent.CATEGORY_HOME);
//                            startActivity(intent);

                            mDPM.lockNow();

                        } else {

                            windowManager.getDefaultDisplay().getSize(outSize);

                            if (layoutParams.x <= outSize.x / 2) {
                                translationX.setFloatValues(layoutParams.x, 0);

                            } else {
                                translationX.setFloatValues(layoutParams.x, (outSize.x - contentContainer.getWidth()));
                            }

                            translationX.start();

                            translationX.addUpdateListener(animation -> {
                                float animatedValue = (float) animation.getAnimatedValue();
                                layoutParams.x = (int) animatedValue;
                                windowManager.updateViewLayout(contentContainer, layoutParams);
                            });
                        }

                        a = true;
//                        return true;
                        break;
                }
                return a;
            }
        });

//        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        contentContainer.addView(assisistiveTouchView);

        windowManager.addView(contentContainer, layoutParams);
    }

    private void hideAssistiveTouchView() {
        assisistiveTouchView.animate().scaleX(0).scaleY(0).alpha(0.2f).setInterpolator(new AccelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                assisistiveTouchView.setVisibility(View.GONE);
            }
        }).start();
    }

    /***********
     * Listener
     ***********/

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        int initialX;
        int initialY;

        @Override
        public boolean onDown(MotionEvent e) {
            initialX = layoutParams.x;
            initialY = layoutParams.y;
            return true;
        }

//        @Override
//        public boolean onSingleTapUp(MotionEvent e) {
//            Log.d(TAG, "onSingleTapUp: ");
//            return true;
//        }

//        @Override
//        public boolean onSingleTapConfirmed(MotionEvent e) {
//            Log.d(TAG, "onSingleTapConfirmed: ");
//            return true;
//        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            float diffX = e2.getRawX() - e1.getRawX();
//            float diffY = e2.getRawY() - e1.getRawY();
//            layoutParams.x = (int) (initialX + diffX);
//            layoutParams.y = (int) (initialY + diffY);
//            windowManager.updateViewLayout(contentContainer, layoutParams);
            return true;
        }
    };
}
