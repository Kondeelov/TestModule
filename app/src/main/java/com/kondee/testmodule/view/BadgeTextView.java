package com.kondee.testmodule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.kondee.testmodule.utils.Utils;


public class BadgeTextView extends AppCompatTextView {
    private static final String TAG = "Kondee";

    private static final float SHADOW_RADIUS = 3.5f;
    private int mShadowRadius;
    private int basePadding;
    private Rect bound;
    private int diffWH;
    private int size;
    private int newWidthMeasureSpec;
    private int newHeightMeasureSpec;

    public BadgeTextView(Context context) {
        super(context);
        init();
    }

    public BadgeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0, 0);
    }

    public BadgeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public BadgeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//        initWithAttrs(attrs, defStyleAttr, defStyleRes);
//    }

    private void init() {

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
//        getPaint().setTextAlign(Paint.Align.CENTER);

//        mShadowRadius = (Utils.dp2px(getContext(), Utils.dp2px(getContext(), 2)));
//        basePadding = (mShadowRadius * 2);
//        float textHeight = getTextSize();
//        float textWidth = textHeight / 4;
//        diffWH = (int) (Math.abs(textHeight - textWidth) / 2);
//        int horizontalPadding = basePadding + diffWH;
//        setPadding(horizontalPadding, basePadding, horizontalPadding, basePadding);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (size != 0) {
            newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
            newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        }

        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);

//        Log.d(TAG, "onMeasure: " + getMeasuredWidth() + " " + getMeasuredHeight());
        size = Math.max(getMeasuredWidth(), getMeasuredHeight());

        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        resetBackgroundDrawable(w, h);
    }

    private void resetBackgroundDrawable(int width, int height) {

        if (width <= 0 || height <= 0) {
            return;
        }

        CharSequence text = getText();

        if (text == null) {
            return;
        }

        if (text.length() == 1) {
            bound = new Rect();
            Paint textPaint = getPaint();
            textPaint.getTextBounds(getText().toString(), 0, getText().length(), bound);

            int diameter = Math.max(width, height);
            RectF rectF = new RectF(bound.left, bound.top, bound.right, bound.bottom);

            Drawable drawable = new MyDrawable(rectF);

            setBackgroundDrawable(drawable);

//            Log.d(TAG, "resetBackgroundDrawable: " + getX() + " " + getY());
        }
    }

    private class MyDrawable extends ShapeDrawable {

        private RectF rectF;

        public MyDrawable(RectF rectF) {

            this.rectF = rectF;
        }

        @Override
        public void draw(Canvas canvas) {
//            super.draw(canvas);

//            Log.d(TAG, "draw: " + rectF.height() + " " + BadgeTextView.this.getTextSize());

            getPaint().setAntiAlias(true);

            getPaint().setColor(Color.RED);
            canvas.drawCircle(BadgeTextView.this.getWidth() / 2, BadgeTextView.this.getHeight() / 2, BadgeTextView.this.getTextSize() / 2, getPaint());
        }
    }
}
