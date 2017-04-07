package com.kondee.testmodule.view;

import android.content.Context;
import android.graphics.BitmapShader;
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

import static android.R.attr.width;
import static com.kondee.testmodule.R.attr.height;


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

//        getPaint().setTextAlign(Paint.Align.RIGHT);

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

        Rect bound = getBound();

        size = (int) Math.max(bound.right - bound.left, getTextSize());

        if (size != 0) {
            newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
            newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        }

        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);

        setMeasuredDimension(size, size);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    private Rect getBound() {
        CharSequence text = getText();
        bound = new Rect();
        Paint textPaint = getPaint();
        textPaint.getTextBounds(text.toString(), 0, getText().length(), bound);

        return bound;
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

            Drawable drawable = new MyDrawable();

            setBackgroundDrawable(drawable);
        }
    }

    private class MyDrawable extends ShapeDrawable {

        @Override
        public void draw(Canvas canvas) {

            getPaint().setAntiAlias(true);
            getPaint().setDither(true);

            getPaint().setColor(Color.RED);
            canvas.drawCircle(BadgeTextView.this.getWidth() / 2, BadgeTextView.this.getTextSize() / 2, BadgeTextView.this.getTextSize() / 2, getPaint());
        }
    }
}
