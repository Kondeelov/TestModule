package com.kondee.testmodule.view.AssistiveTouch;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.kondee.testmodule.R;
import com.kondee.testmodule.utils.Utils;

/**
 * Created by Kondee on 8/3/2017.
 */

public class AssistiveTouchView extends View {

    private final int SIZE = Utils.dp2px(getContext(), 48);
    private final int RADIUS = (SIZE - Utils.dp2px(getContext(), 8)) / 2;
    private Paint circlePaint = new Paint();
    private int color;

    public AssistiveTouchView(Context context) {
        super(context);
        init();
    }

    public AssistiveTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0, 0);
    }

    public AssistiveTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AssistiveTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(SIZE, MeasureSpec.EXACTLY);
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(SIZE, MeasureSpec.EXACTLY);
        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);

        setMeasuredDimension(SIZE, SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);
    }

    private void init() {
//        setBackgroundColor(Color.TRANSPARENT);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        preparePaint();
    }

    private void preparePaint() {
        circlePaint.setAntiAlias(true);
        circlePaint.setDither(true);
        circlePaint.setStyle(Paint.Style.FILL);
        setColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, RADIUS, circlePaint);
    }

    public void setColor(int color) {
        this.color = color;
        circlePaint.setColor(color);
    }
}
