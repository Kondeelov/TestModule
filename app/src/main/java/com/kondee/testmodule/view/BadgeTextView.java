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
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.kondee.testmodule.R;
import com.kondee.testmodule.utils.Utils;

import static android.R.attr.width;
import static com.kondee.testmodule.R.attr.height;


public class BadgeTextView extends AppCompatTextView {
    private static final String TAG = "Kondee";

    private static final float SHADOW_RADIUS = 3.5f;
    private int min;
    private int mShadowRadius;
    private int basePadding;
    private Rect bound;
    private int diffWH;
    private int size;
    private int newWidthMeasureSpec;
    private int newHeightMeasureSpec;
    private Paint circlePaint;
    private int backgroundColor;

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

        setGravity(Gravity.CENTER);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        preparePaint();
    }

    private void preparePaint() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setDither(true);
        circlePaint.setColor(backgroundColor);
        circlePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void setGravity(int gravity) {
        gravity = Gravity.CENTER;
        super.setGravity(gravity);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        bound = getBound();

        size = (int) Math.max(bound.width(), getTextSize());
//        Log.d(TAG, "onMeasure: " + bound.width() + " " + bound.height());

        if (size != 0) {
            newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
            newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        }

        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);

        setMeasuredDimension(size, size);

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setBackground(Drawable background) {
//        super.setBackground(background);
        if (background instanceof ColorDrawable) {
            backgroundColor = ((ColorDrawable) background).getColor();
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
//        super.setBackgroundDrawable(background);
        if (background instanceof ColorDrawable) {
            backgroundColor = ((ColorDrawable) background).getColor();
        }
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
//        super.setBackgroundColor(color);
        backgroundColor = color;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        setPadding(0, -(bound.height() / 4), 0, bound.height() / 4);
    }

    private Rect getBound() {
        CharSequence text = getText();
        bound = new Rect();
        Paint textPaint = getPaint();
        textPaint.getTextBounds(text.toString(), 0, text.length(), bound);

        return bound;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        min = Math.min(getMeasuredWidth() / 2, getMeasuredHeight() / 2);

        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, min, circlePaint);

        super.onDraw(canvas);
    }
}
