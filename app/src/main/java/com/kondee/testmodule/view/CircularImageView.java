package com.kondee.testmodule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;

import com.kondee.testmodule.R;
import com.kondee.testmodule.utils.Utils;

/**
 * Created by Kondee on 4/7/2017.
 */

public class CircularImageView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = "Kondee";
    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;
    private Paint paint = new Paint();
    private Paint circlePaint = new Paint();
    private int min;
    private int max;
    private float borderWidth;
    private int borderColor;
    private int backgroundColor;

    public CircularImageView(Context context) {
        super(context);
        init();
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0);
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr);
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.CircularImageView,
                defStyleAttr,
                0);

        try {
            borderWidth = a.getDimension(R.styleable.CircularImageView_borderWidth, 0);
            borderColor = a.getColor(R.styleable.CircularImageView_borderColor, Color.BLACK);
        } finally {
            a.recycle();
        }

        preparePaint();
    }

    private void preparePaint() {
        paint.setAntiAlias(true);
        paint.setDither(true);

        circlePaint.setDither(true);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(borderColor);
        circlePaint.setStrokeWidth(borderWidth);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType == SCALE_TYPE) {
            super.setScaleType(scaleType);
        } else {
            throw new IllegalStateException("You can only set scale type to CenterCrop");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        min = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        max = Math.max(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(min, MeasureSpec.EXACTLY);
        int newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(min, MeasureSpec.EXACTLY);
        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);

        setMeasuredDimension(min, min);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircleBitmap(canvas);
    }

    @Override
    public void setBackground(Drawable background) {
        if (background instanceof ColorDrawable) {
            int color = ((ColorDrawable) background).getColor();

            int red = (color >> 16) & 0xFF;
            int green = (color >> 8) & 0xFF;
            int blue = (color) & 0xFF;

            backgroundColor = Color.rgb(red, green, blue);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {

    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {

    }

    @Override
    public void setPadding(@Px int left, @Px int top, @Px int right, @Px int bottom) {
        throw new IllegalStateException("Don't set padding. Because you will get unexpected result");
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        backgroundColor = color;
    }

    private void drawCircleBitmap(Canvas canvas) {

        Bitmap bitmap;
        bitmap = getBitmapFromDrawable(getDrawable());

        float halfMin = ((float) min) / 2;

        if (bitmap != null) {
            int densityDpi = new DisplayMetrics().densityDpi;
            int scaledHeight = bitmap.getScaledHeight(densityDpi);
            int scaledWidth = bitmap.getScaledWidth(densityDpi);

            int minBitmap = Math.min(scaledHeight, scaledWidth);
            int maxBitmap = Math.max(scaledHeight, scaledWidth);

            int bitmapWidth;
            int bitmapHeight;
            if (scaledHeight > scaledWidth) {
                bitmapWidth = minBitmap;
                bitmapHeight = maxBitmap;
            } else {
                bitmapWidth = maxBitmap;
                bitmapHeight = minBitmap;
            }

            float ratio = ((float) minBitmap) / ((float) min);
            if (ratio > 0) {
                bitmapWidth = (int) (bitmapWidth / ratio);
                bitmapHeight = (int) (bitmapHeight / ratio);
            } else {
                bitmapWidth = (int) (bitmapWidth * ratio);
                bitmapHeight = (int) (bitmapHeight * ratio);
            }

            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmapWidth - (borderWidth * 2)), (int) (bitmapHeight - (borderWidth * 2)), false);

            BitmapShader shader = new BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Matrix shaderMatrix = new Matrix();

            shaderMatrix.postTranslate(((getMeasuredWidth() - bitmapWidth) / 2) + (borderWidth),
                    ((getMeasuredHeight() - bitmapHeight) / 2) + (borderWidth));

            shader.setLocalMatrix(shaderMatrix);
            paint.setShader(shader);

            canvas.drawCircle(halfMin, halfMin, halfMin - borderWidth, paint);
        }

        if (borderWidth != 0) {
            canvas.drawCircle(halfMin, halfMin, halfMin - (borderWidth / 2), circlePaint);
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        Bitmap bitmap;

        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        } else {
            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }
        }

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(backgroundColor);

//        canvas.drawBitmap(bitmap, 0, 0, null);

        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }
}
