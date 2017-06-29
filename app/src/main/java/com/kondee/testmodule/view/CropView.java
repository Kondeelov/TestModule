package com.kondee.testmodule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.kondee.testmodule.R;
import com.kondee.testmodule.utils.Utils;

/**
 * Created by Kondee on 6/19/2017.
 */

public class CropView extends android.support.v7.widget.AppCompatImageView {

    private Paint overlayPaint;
    private Paint framePaint;
    Path path = new Path();
    RectF rectF = new RectF();

    private int circleRadius;
    GestureDetector gestureDetector;
    private int min;
    private float circleCenterX;
    private float circleCenterY;

    public CropView(Context context) {
        super(context);
        init();
    }

    public CropView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0);
    }

    public CropView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr);
    }

    private void init() {
        gestureDetector = new GestureDetector(getContext(), gestureListener);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr) {

        preparePaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        min = Math.min(getMeasuredWidth(), getMeasuredHeight());
        circleCenterX = min / 2;
        circleCenterY = min / 2;

        circleRadius = min / 2;

        rectF.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        currentRectF.set(0, 0, min, min);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawOverlay(canvas);
        drawFrame(canvas);
    }

    private void preparePaint() {
        overlayPaint = new Paint();
        overlayPaint.setAntiAlias(true);
        overlayPaint.setFilterBitmap(true);
        overlayPaint.setStyle(Paint.Style.FILL);
        overlayPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorGrayLightestTransparent));

        framePaint = new Paint();
        framePaint.setAntiAlias(true);
        framePaint.setFilterBitmap(true);
        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setStrokeWidth(Utils.dp2px(getContext(), 4));
        framePaint.setColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
    }

    private void drawOverlay(Canvas canvas) {
        path.reset();

        path.addRect(rectF, Path.Direction.CW);

        path.addCircle(currentRectF.centerX(), currentRectF.centerY(), circleRadius, Path.Direction.CCW);

        canvas.drawPath(path, overlayPaint);
    }

    private void drawFrame(Canvas canvas) {
        canvas.drawRect(currentRectF, framePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private RectF currentRectF = new RectF();
    private final GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {

        float rectRight;
        float rectLeft;
        float rectBottom;
        float rectTop;

        @Override
        public boolean onDown(MotionEvent e) {

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (currentRectF.top - distanceY <= 0) {
                rectTop = 0;
                rectBottom = currentRectF.height();
            } else if (currentRectF.bottom - distanceY >= getMeasuredHeight()) {
                rectBottom = getMeasuredHeight();
                rectTop = getMeasuredHeight() - currentRectF.height();
            } else {
                rectTop = currentRectF.top - distanceY;
                rectBottom = currentRectF.bottom - distanceY;
            }

            if (currentRectF.left - distanceX <= 0) {
                rectLeft = 0;
                rectRight = currentRectF.width();
            } else if (currentRectF.right - distanceX >= getMeasuredWidth()) {
                rectRight = getMeasuredWidth();
                rectLeft = getMeasuredWidth() - currentRectF.width();
            } else {
                rectLeft = currentRectF.left - distanceX;
                rectRight = currentRectF.right - distanceX;
            }

            currentRectF.set(rectLeft, rectTop, rectRight, rectBottom);

            invalidate();

            return true;
        }
    };
}
