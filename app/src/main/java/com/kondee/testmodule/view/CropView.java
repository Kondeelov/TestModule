package com.kondee.testmodule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.kondee.testmodule.R;
import com.kondee.testmodule.utils.Utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Kondee on 6/19/2017.
 */

public class CropView extends android.support.v7.widget.AppCompatImageView {

    @IntDef({TouchArea.CONTENT, TouchArea.LEFT_TOP, TouchArea.LEFT_BOTTOM, TouchArea.RIGHT_TOP, TouchArea.RIGHT_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    @interface TouchArea {
        int CONTENT = 0;
        int LEFT_TOP = 1;
        int LEFT_BOTTOM = 2;
        int RIGHT_TOP = 3;
        int RIGHT_BOTTOM = 4;
    }

    private final int strokeWidth = Utils.dp2px(getContext(), 1.5f);
    private Paint overlayPaint;
    private Paint framePaint;
    Path path = new Path();
    RectF rectF = new RectF();

    GestureDetector gestureDetector;
    private int touchArea;

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
        int min = Math.min(getMeasuredWidth(), getMeasuredHeight());

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
        framePaint.setStrokeWidth(strokeWidth);
        framePaint.setColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
    }

    private void drawOverlay(Canvas canvas) {
        path.reset();

        path.addRect(rectF, Path.Direction.CW);

        path.addCircle(currentRectF.centerX(), currentRectF.centerY(), currentRectF.width() / 2, Path.Direction.CCW);

        canvas.drawPath(path, overlayPaint);
    }

    private void drawFrame(Canvas canvas) {

        RectF frameRect = this.currentRectF;
//        frameRect.inset(strokeWidth / 2, strokeWidth / 2);
        canvas.drawRect(frameRect, framePaint);
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
            Rect rect = new Rect(((int) currentRectF.left), ((int) currentRectF.top), ((int) currentRectF.left) + 20, ((int) currentRectF.top) + 20);
            if (rect.contains(((int) e.getX()), ((int) e.getY()))) {
                touchArea = TouchArea.LEFT_TOP;
            } else {
                touchArea = TouchArea.CONTENT;
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (touchArea == TouchArea.LEFT_TOP) {

                float distance;
                if (distanceX < 0 && distanceY < 0) {
                    distance = Math.min(distanceX, distanceY);
                } else if (distanceX > 0 && distanceY > 0) {
                    distance = Math.max(distanceX, distanceY);
                } else {
                    distance = Math.max(distanceX, distanceY);
                }

                rectLeft = currentRectF.left - distance;
                rectTop = currentRectF.top - distance;
            } else {

                rectTop = currentRectF.top - distanceY;
                rectBottom = currentRectF.bottom - distanceY;

                rectLeft = currentRectF.left - distanceX;
                rectRight = currentRectF.right - distanceX;

                if (rectTop <= 0) {
                    rectTop = 0;
                    rectBottom = currentRectF.height();
                } else if (rectBottom >= getMeasuredHeight()) {
                    rectBottom = getMeasuredHeight();
                    rectTop = getMeasuredHeight() - currentRectF.height();
                }
                if (rectLeft - distanceX <= 0) {
                    rectLeft = 0;
                    rectRight = currentRectF.width();
                } else if (rectRight >= getMeasuredWidth()) {
                    rectRight = getMeasuredWidth();
                    rectLeft = getMeasuredWidth() - currentRectF.width();
                }
            }

            currentRectF.set(rectLeft, rectTop, rectRight, rectBottom);

            invalidate();

            return true;
        }
    };
}
