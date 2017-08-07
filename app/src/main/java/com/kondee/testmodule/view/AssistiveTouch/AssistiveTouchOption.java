package com.kondee.testmodule.view.AssistiveTouch;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;

import com.kondee.testmodule.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kondee on 8/7/2017.
 */

public class AssistiveTouchOption extends View {

    private SparseArrayCompat<Rect> rectSparseArrayCompat = new SparseArrayCompat<>(9);
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

    }

    private void init() {

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        TODO : AssistiveTouchOption
    }

    private void prepareRect() {
        int startX = 0;
        int startY = 0;
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                startX = 0;
                startY += HEIGHT;
            }
            rectSparseArrayCompat.put(i, new Rect(startX, startY, startX + WIDTH, startY + HEIGHT));
            startX += WIDTH;
        }
    }
}
