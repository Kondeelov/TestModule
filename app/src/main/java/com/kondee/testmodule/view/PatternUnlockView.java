package com.kondee.testmodule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kondee.testmodule.R;

import java.util.ArrayList;
import java.util.List;

public class PatternUnlockView extends View {
    private static final String TAG = "Kondee";
    private int viewHeight;
    private int viewWidth;
    private Paint patternPinPaint;
    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    PatternUnlockRect patternUnlockRect;
    private int rectWidth;
    private int rectHeight;
    private float pressZoneSize;
    private List<PatternUnlockRect> patternUnlockRects = new ArrayList<>();
    private float pinRadius;
    private Paint pressedPinPaint;
    private int pinColor;
    private int pressedColor;
    private Paint linePaint;
    private String pressedValue;
    List<String> connectionOrder = new ArrayList<>();
    private float pressPinRadius;
    private float lineWidth;

    public PatternUnlockView(Context context) {
        super(context);
        init();
    }

    public PatternUnlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0, 0);
    }

    public PatternUnlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @RequiresApi(21)
    public PatternUnlockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void init() {

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.PatternUnlockView, defStyleAttr, defStyleRes);
        try {
            pressZoneSize = a.getDimension(R.styleable.PatternUnlockView_pressZoneSize, dp2px(56));
            pinRadius = a.getDimension(R.styleable.PatternUnlockView_pinRadius, 15);
            pinColor = a.getColor(R.styleable.PatternUnlockView_pinColor, ContextCompat.getColor(getContext(), R.color.colorBlack));
            pressedColor = a.getColor(R.styleable.PatternUnlockView_pinColor, ContextCompat.getColor(getContext(), R.color.colorGrayTransparent));
            pressPinRadius = a.getDimension(R.styleable.PatternUnlockView_pressPinRadius, 35);
            lineWidth = a.getDimension(R.styleable.PatternUnlockView_lineWidth, 5);

        } finally {
            a.recycle();
        }

        preparePaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getMeasuredHeight() < getMeasuredWidth()) {
            viewWidth = getMeasuredHeight();
            viewHeight = getMeasuredHeight();
        } else {
            viewWidth = getMeasuredWidth();
            viewHeight = getMeasuredWidth();
        }

        rectWidth = viewWidth / 3;
        rectHeight = viewHeight / 3;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        addPinRect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawPatternPin(canvas);
        drawLinePattern(canvas);
        drawCirclePattern(canvas);
        drawLineToTouchPoint(canvas);
    }

    private void preparePaint() {
        patternPinPaint = new Paint();
        patternPinPaint.setColor(pinColor);
        patternPinPaint.setAntiAlias(true);
        patternPinPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        patternPinPaint.setAntiAlias(true);
        patternPinPaint.setDither(true);

        pressedPinPaint = new Paint();
        pressedPinPaint.setColor(pressedColor);
        pressedPinPaint.setAntiAlias(true);
        pressedPinPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pressedPinPaint.setAntiAlias(true);
        pressedPinPaint.setDither(true);

        linePaint = new Paint();
        linePaint.setColor(pressedColor);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineWidth);

    }

    private void addPinRect() {
        int x = (getMeasuredWidth() - viewWidth) / 2;
        int y = 0;
        for (int i = 1; i <= 9; i++) {

            Rect rect = new Rect(x, y, x + rectWidth, y + rectHeight);

            patternUnlockRect = new PatternUnlockRect(new Rect((int) (rect.centerX() - pressZoneSize / 2),
                    (int) (rect.centerY() - pressZoneSize / 2),
                    (int) (rect.centerX() + pressZoneSize / 2),
                    (int) (rect.centerY() + pressZoneSize / 2)),
                    String.valueOf(i),
                    false);
            x = x + rectWidth;
            if (i % 3 == 0) {
                x = (getMeasuredWidth() - viewWidth) / 2;
                y = y + rectHeight;
            }

            patternUnlockRects.add(patternUnlockRect);
        }
    }

    private void drawPatternPin(Canvas canvas) {
        for (PatternUnlockRect patternUnlockRect : patternUnlockRects) {
            canvas.drawCircle(patternUnlockRect.rect.exactCenterX(),
                    patternUnlockRect.rect.exactCenterY(),
                    pinRadius,
                    patternPinPaint);
        }
    }

    private void drawCirclePattern(Canvas canvas) {
        for (int i = 0; i < connectionOrder.size(); i++) {

            canvas.drawCircle(patternUnlockRects.get(Integer.valueOf(connectionOrder.get(i)) - 1).rect.exactCenterX(),
                    patternUnlockRects.get(Integer.valueOf(connectionOrder.get(i)) - 1).rect.exactCenterY(),
                    pressPinRadius,
                    pressedPinPaint);
        }
    }

    private void drawLinePattern(Canvas canvas) {

        for (int i = 0; i < connectionOrder.size() - 1; i++) {
            drawLine(canvas,
                    patternUnlockRects.get(Integer.valueOf(connectionOrder.get(i)) - 1).rect.exactCenterX(),
                    patternUnlockRects.get(Integer.valueOf(connectionOrder.get(i)) - 1).rect.exactCenterY(),
                    patternUnlockRects.get(Integer.valueOf(connectionOrder.get(i + 1)) - 1).rect.exactCenterX(),
                    patternUnlockRects.get(Integer.valueOf(connectionOrder.get(i + 1)) - 1).rect.exactCenterY()
            );
        }
    }

    private void drawLineToTouchPoint(Canvas canvas) {

        if (stopX > 0 && stopY > 0) {
            try {
                drawLine(canvas,
                        patternUnlockRects.get(Integer.valueOf(connectionOrder.get(connectionOrder.size() - 1)) - 1).rect.exactCenterX(),
                        patternUnlockRects.get(Integer.valueOf(connectionOrder.get(connectionOrder.size() - 1)) - 1).rect.exactCenterY(),
                        stopX,
                        stopY);
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.d(TAG, "drawLineToTouchPoint: ArrayIndexOutOfBounds");
            }
        }
    }

    private void drawLine(Canvas canvas, float startX, float startY, float stopX, float stopY) {
        canvas.drawLine(startX, startY, stopX, stopY, linePaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return patternTouchEvent(event);
    }

    private boolean patternTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                pressedValue = "";

                for (PatternUnlockRect patternUnlockRect : patternUnlockRects) {
                    if (patternUnlockRect.rect.contains((int) event.getX(), (int) event.getY()) && !patternUnlockRect.isSelected()) {
                        patternUnlockRect.setSelected(true);
                        startX = patternUnlockRect.rect.exactCenterX();
                        startY = patternUnlockRect.rect.exactCenterY();

                        if (!connectionOrder.contains(patternUnlockRect.getValue())) {
                            connectionOrder.add(patternUnlockRect.getValue());
                        }
                    }
                }

                invalidate();

                return true;
            case MotionEvent.ACTION_MOVE:

                stopX = event.getX();
                stopY = event.getY();

                for (PatternUnlockRect patternUnlockRect : patternUnlockRects) {
                    if (patternUnlockRect.rect.contains((int) event.getX(), (int) event.getY()) && !patternUnlockRect.isSelected()) {
                        patternUnlockRect.setSelected(true);
                        stopX = patternUnlockRect.rect.exactCenterX();
                        stopY = patternUnlockRect.rect.exactCenterY();

                        if (!connectionOrder.contains(patternUnlockRect.getValue())) {
                            connectionOrder.add(patternUnlockRect.getValue());
                        }
                    }
                }

                invalidate();

                return true;
            case MotionEvent.ACTION_UP:

                stopX = 0;
                stopY = 0;
                connectionOrder.clear();

                for (PatternUnlockRect patternUnlockRect : patternUnlockRects) {

                    patternUnlockRect.setSelected(false);
                }

                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    private float dp2px(float dp) {

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return dp * displayMetrics.density;
    }

    /***********
     * Listener
     ***********/

    OnPatternDetectListener listener;

    public interface OnPatternDetectListener {
        void onPatternDetected(List<String> pattern);
    }

    public void setOnPatternDetectListener(OnPatternDetectListener listener) {
        this.listener = listener;
    }
}
