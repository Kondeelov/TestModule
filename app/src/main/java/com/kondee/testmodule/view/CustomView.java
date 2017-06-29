package com.kondee.testmodule.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.kondee.testmodule.R;
import com.kondee.testmodule.utils.Utils;

public class CustomView extends View {
    private RectF rectF;
    private Paint paint;
    private final float PADDING = Utils.dp2px(getContext(), 36);
    private Paint paintF;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @RequiresApi(21)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void init() {

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        preparePaint();
    }

    private void preparePaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGrayLightestTransparent));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(getContext(), 16));

        paintF = new Paint();
        paintF.setAntiAlias(true);
        paintF.setColor(ContextCompat.getColor(getContext(), R.color.colorGreenTranslucent));
        paintF.setStyle(Paint.Style.STROKE);
        paintF.setStrokeWidth(Utils.dp2px(getContext(), 16));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int min = Math.min(getMeasuredWidth(), getMeasuredHeight());
        rectF = new RectF(0 + PADDING, 0 + PADDING, min - PADDING, min - PADDING);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        canvas.drawArc(rectF, 135, 270, false, paint);

        canvas.drawArc(rectF, 135, getValueFromPercent(35), false, paintF);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.star);
        Bitmap scaledBitmap = bitmap.createScaledBitmap(bitmap, Utils.dp2px(getContext(), 16), Utils.dp2px(getContext(), 16), false);


    }

    float getValueFromPercent(float percent) {
        return percent / 100 * 270;
    }

}
