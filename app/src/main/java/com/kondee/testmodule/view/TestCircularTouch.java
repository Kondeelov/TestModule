package com.kondee.testmodule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kondee.testmodule.utils.Utils;

public class TestCircularTouch extends View {

    private static final String TAG = "Kondee";
    private Paint paint;
    private RectF rect;
    float radius;
    private int outerRadius = 20;
    private int centerX;
    private int centerY;
    private static final int PRESS_RADIUS = 20;
    private RectF pressedRect;

    public TestCircularTouch(Context context) {
        super(context);
        init();
    }

    public TestCircularTouch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWIthAttrs(attrs, 0, 0);
    }

    public TestCircularTouch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWIthAttrs(attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TestCircularTouch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWIthAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(getContext(), 2));

        radius = Utils.dp2px(getContext(), 50);
    }

    private void initWIthAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        rect = new RectF(getMeasuredWidth() / 4, getMeasuredWidth() / 4, getMeasuredWidth() - getMeasuredWidth() / 4, getMeasuredWidth() - getMeasuredWidth() / 4);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        outerRadius = Math.min(w, h) / 2;
        pressedRect = new RectF(PRESS_RADIUS, PRESS_RADIUS, (centerY - PRESS_RADIUS / 2) * 2, (centerX - PRESS_RADIUS / 2) * 2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawCircle(rect.exactCenterX(), rect.exactCenterY(), radius, paint);
//        canvas.drawCircle(rect.centerX(), rect.centerY(), outerRadius, paint);
        canvas.drawArc(pressedRect, 0, 360, false, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x > PRESS_RADIUS && x < centerX * 2 - PRESS_RADIUS) {

                    float upY = (float) Math.sqrt(Math.pow((outerRadius - PRESS_RADIUS / 2), 2) - Math.pow((outerRadius - x), 2));
                    if (y > centerY - upY && y < centerY) {
                        Log.d(TAG, "onTouchEvent: true");
                    } else if (y > centerY && y < centerY + upY) {
                        Log.d(TAG, "onTouchEvent: true");
                    }
                }
                return true;
            case MotionEvent.ACTION_MOVE:

                return true;
            case MotionEvent.ACTION_UP:

                return true;
        }

        return super.onTouchEvent(event);
    }

    private boolean inCircle(float x, float y, float circleCenterX, float circleCenterY, float circleRadius) {
        double dx = Math.pow(x - circleCenterX, 2);
        double dy = Math.pow(y - circleCenterY, 2);

        if ((dx + dy) < Math.pow(circleRadius, 2)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIsTouchInCircle(float xTouch, float yTouch, float x, float y, float radius) {
        return (xTouch - (x + radius)) * (xTouch - (x + radius)) + (yTouch - (y + radius)) * (yTouch - (y + radius)) <= radius * radius;
    }
}
