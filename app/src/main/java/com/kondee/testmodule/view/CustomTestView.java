package com.kondee.testmodule.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ListImageItemBinding;

public class CustomTestView extends View {

    Canvas canvas;
    Rect rect;
    Paint paint;

    public CustomTestView(Context context) {
        super(context);
        init();
    }

    public CustomTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs,0,0);
    }

    public CustomTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs,defStyleAttr,0);
    }

    @RequiresApi(21)
    public CustomTestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs,defStyleAttr,defStyleRes);
    }

    private void init() {

        rect = new Rect(20,20,80,80);
        canvas = new Canvas();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent,null));
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(rect,paint);
    }
}
