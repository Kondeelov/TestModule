package com.kondee.testmodule.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by Kondee on 4/7/2017.
 */

public class CircularImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint paint;

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
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr) {


    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        drawCircleBitmap(canvas);
    }

    private void drawCircleBitmap(Canvas canvas) {

        Drawable drawable = getDrawable();

        Bitmap bitmap;
//        if (drawable instanceof BitmapDrawable) {
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, getMeasuredWidth(), getMeasuredHeight(), false);
//        } else {
//            bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMaxHeight(), Bitmap.Config.ARGB_8888);
//        }

        BitmapShader shader = new BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        OvalShape ovalShape = new OvalShape();
        ovalShape.resize(getMeasuredHeight(), getMeasuredHeight());
        ovalShape.draw(canvas, paint);
    }
}
