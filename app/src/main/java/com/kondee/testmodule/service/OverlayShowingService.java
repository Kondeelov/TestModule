package com.kondee.testmodule.service;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.kondee.testmodule.R;
import com.kondee.testmodule.utils.Utils;

/**
 * Created by Kondee on 7/18/2017.
 */

public class OverlayShowingService extends Service {

    private static final String TAG = "Kondee";
    private WindowManager windowManager;
    private TextView textView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initFloatingItem();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textView != null) {
            windowManager.removeView(textView);
            textView = null;
        }
    }

    private void initFloatingItem() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        textView = new TextView(this);
        textView.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_button_transparent_pressed));
        textView.setText("Hola!");
        textView.setPadding(Utils.dp2px(this, 4), Utils.dp2px(this, 4), Utils.dp2px(this, 4), Utils.dp2px(this, 4));
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PRIORITY_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        layoutParams.gravity = Gravity.START | Gravity.CENTER;

        textView.setOnTouchListener(new View.OnTouchListener() {

            private ValueAnimator translationX;
            int initialX = 0;
            int initialY = 0;
            float initialTouchX = 0;
            float initialTouchY = 0;
            Point outSize = new Point();

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                translationX = new ValueAnimator();
                translationX.setDuration(450);
                translationX.setInterpolator(new DecelerateInterpolator());

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = layoutParams.x;
                        initialY = layoutParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        return true;
                    case MotionEvent.ACTION_MOVE:
                        layoutParams.x = (int) (initialX + (event.getRawX() - initialTouchX));
                        layoutParams.y = (int) (initialY + (event.getRawY() - initialTouchY));
                        windowManager.updateViewLayout(textView, layoutParams);
                        return true;
                    case MotionEvent.ACTION_UP:

                        windowManager.getDefaultDisplay().getSize(outSize);

                        if (layoutParams.x <= outSize.x / 2) {
                            translationX.setFloatValues(layoutParams.x, 0);

                        } else {
                            translationX.setFloatValues(layoutParams.x, outSize.x);
                        }

                        translationX.start();

                        translationX.addUpdateListener(animation -> {
                            float animatedValue = (float) animation.getAnimatedValue();
                            layoutParams.x = (int) animatedValue;
                            windowManager.updateViewLayout(textView, layoutParams);
                        });

                        return true;
                }
                return false;
            }
        });

//        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        windowManager.addView(textView, layoutParams);
    }
}
