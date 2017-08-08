package com.kondee.testmodule.view.AssistiveTouch;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.util.AttributeSet;
import android.view.View;

import com.kondee.testmodule.utils.Utils;

/**
 * Created by Kondee on 8/7/2017.
 */

public class AssistiveTouchOption extends View {

    private final Paint paint = new Paint();
    private SparseArrayCompat<Rect> rectArray = new SparseArrayCompat<>(9);
    private final int SIZE = Utils.dp2px(getContext(), 224);
    private final int WIDTH = Utils.dp2px(getContext(), 224);
    private final int HEIGHT = Utils.dp2px(getContext(), 224);

    public AssistiveTouchOption(Context context) {
        super(context);
    }

    public AssistiveTouchOption(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AssistiveTouchOption(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AssistiveTouchOption(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

        prepareRect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawRect(canvas);
    }

    private void drawRect(Canvas canvas) {
        for (int i = 0; i < rectArray.size(); i++) {
            if (i % 2 == 0) {
                Rect rect = rectArray.get(i);
                RectF rectF = new RectF(rect);
                canvas.drawRoundRect(rectF, Utils.dp2px(getContext(), 2), Utils.dp2px(getContext(), 2), paint);
            }
        }
    }

    private void init() {

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        TODO : AssistiveTouchOption

        preparePaint();
    }

    private void preparePaint() {
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
    }

    private void prepareRect() {
        int startX = 0;
        int startY = 0;
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                startX = 0;
                startY += HEIGHT;
            }
            rectArray.put(i, new Rect(startX, startY, startX + WIDTH, startY + HEIGHT));
            startX += WIDTH;
        }
    }
}
