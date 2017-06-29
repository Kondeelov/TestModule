package com.kondee.testmodule.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class ZoomageView extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = "Kondee";
    private float scaleFactor = 1.0f;
    private float lastCenterX;
    private float lastCenterY;
    private ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            if (scaleFactor < 0.8f) {
                scaleFactor = 0.8f;
            }
            if (scaleFactor > 1.2f) {
                scaleFactor = 1.2f;
            }
            lastCenterX = detector.getFocusX();
            lastCenterY = detector.getFocusY();
            Log.d(TAG, "onScale: " + lastCenterX + " " + lastCenterY);
            currentMatrix.setScale(scaleFactor, scaleFactor, lastCenterX, lastCenterY);
            setImageMatrix(currentMatrix);
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    });

    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

        private float startY;
        private float startX;

        @Override
        public boolean onDown(MotionEvent e) {
            float[] f = new float[9];
            currentMatrix.getValues(f);
            Log.d(TAG, "onDown: " + f[Matrix.MTRANS_X] + " " + f[Matrix.MTRANS_Y]);
            startX = e.getX();
            startY = e.getY();
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            ValueAnimator animator = ValueAnimator.ofFloat(scaleFactor, 1.0f);
            animator.setDuration(500)
                    .setInterpolator(new DecelerateInterpolator());
            animator.start();
            animator.addUpdateListener(animation -> {
                scaleFactor = (float) animation.getAnimatedValue();

                currentMatrix.setScale(scaleFactor, scaleFactor, lastCenterX, lastCenterY);
                setImageMatrix(currentMatrix);
            });

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            currentMatrix.setTranslate(e2.getX() - startX, e2.getY() - startY);
            currentMatrix.postScale(1, 1);
            setImageMatrix(currentMatrix);

            return true;
        }
    });

    private Matrix originalMatrix = new Matrix();
    private Matrix currentMatrix = new Matrix();

    public ZoomageView(Context context) {
        super(context);
    }

    public ZoomageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr);
    }

    private void init() {

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        originalMatrix.set(getImageMatrix());

        currentMatrix.set(originalMatrix);

        setScaleType(ScaleType.MATRIX);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return gestureDetector.onTouchEvent(event) || scaleGestureDetector.onTouchEvent(event);
    }
}
