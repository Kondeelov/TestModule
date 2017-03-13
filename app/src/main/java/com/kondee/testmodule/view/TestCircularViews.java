package com.kondee.testmodule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kondee.testmodule.R;
import com.kondee.testmodule.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class TestCircularViews extends View {

    private static final String TAG = "Kondee";
    private int radius;
    private int selected = -1;

    private static class Circle {

        float x;
        float y;

        Circle(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }

    List<Circle> circleList = new ArrayList<>();
    private int startX;
    private int startY;
    private Paint paint;

    public TestCircularViews(Context context) {
        super(context);
        init();
    }

    public TestCircularViews(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0, 0);
    }

    public TestCircularViews(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TestCircularViews(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void init() {

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        preparePaint();
    }

    private void preparePaint() {

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(getContext(), 3));
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        startX = getMeasuredWidth() / 4;
        startY = getMeasuredHeight() / 2;

        radius = (startX - 10) / 2;
    }

    private void addCircle() {
        for (int i = 0; i < 3; i++) {
            circleList.add(new Circle(startX + (startX * i), startY));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        addCircle();

        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {

        for (int i = 0; i < 3; i++) {
            if (selected == i) {
                paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGreenTranslucent));
                canvas.drawCircle(circleList.get(i).getX(), circleList.get(i).getY(), radius, paint);
            } else {
                paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
                canvas.drawCircle(circleList.get(i).getX(), circleList.get(i).getY(), radius, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX;
        float eventY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                eventX = event.getX();
                eventY = event.getY();

                return getActionResult(eventX, eventY);
            case MotionEvent.ACTION_MOVE:
//                eventX = event.getX();
//                eventY = event.getY();
//
//                return getActionResult(eventX, eventY);
                return true;
            case MotionEvent.ACTION_CANCEL:
                selected = -1;
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                selected = -1;
                invalidate();
                return true;
        }

        return super.onTouchEvent(event);
    }

    private boolean getActionResult(float eventX, float eventY) {
        for (int i = 0; i < 3; i++) {
            if (eventY == circleList.get(i).getY()) {
                if (Math.abs(eventX - circleList.get(i).getY()) <= radius) {
                    if (listener != null) {
                        listener.onClick(i);
                        selected = i;
                        invalidate();
                        return true;
                    }
                }
            } else {
                double powX = Math.pow(Math.abs(eventX - circleList.get(i).getX()), 2);
                double powY = Math.pow(Math.abs(eventY - circleList.get(i).getY()), 2);
                double circleArea = Math.sqrt(powX + powY);
                if (circleArea <= radius) {
                    if (listener != null) {
                        listener.onClick(i);
                        selected = i;
                        invalidate();
                        return true;
                    }
                }
            }
        }

        selected = -1;
        invalidate();

        return false;
    }

    /***********
     * Listener
     ***********/

    private onCircleClickListener listener;

    public interface onCircleClickListener {
        void onClick(int position);

    }

    public void setOnCircleClickListener(onCircleClickListener listener) {
        this.listener = listener;
    }

}
