package com.kondee.testmodule.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.AlertCroppedImageBinding;
import com.kondee.testmodule.utils.Utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Kondee on 6/19/2017.
 */

public class CropView extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = "Kondee";

    @IntDef({TouchArea.CONTENT, TouchArea.LEFT_TOP, TouchArea.LEFT_BOTTOM, TouchArea.RIGHT_TOP, TouchArea.RIGHT_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    @interface TouchArea {
        int NOTHING = 0;
        int CONTENT = 1;
        int LEFT_TOP = 2;
        int LEFT_BOTTOM = 3;
        int RIGHT_TOP = 4;
        int RIGHT_BOTTOM = 5;
    }

    private final int strokeWidth = Utils.dp2px(getContext(), 1.5f);
    private Paint overlayPaint;
    private Paint framePaint;
    Path path = new Path();
    RectF rectF = new RectF();

    GestureDetector gestureDetector;
    private int touchArea;

    public CropView(Context context) {
        super(context);
        init();
    }

    public CropView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0);
    }

    public CropView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr);
    }

    private void init() {
        gestureDetector = new GestureDetector(getContext(), gestureListener);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr) {

        preparePaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int min = Math.min(getMeasuredWidth(), getMeasuredHeight());

        rectF.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        currentRectF.set(0, 0, min, min);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawOverlay(canvas);
        drawFrame(canvas);
    }

    private void preparePaint() {
        overlayPaint = new Paint();
        overlayPaint.setAntiAlias(true);
        overlayPaint.setFilterBitmap(true);
        overlayPaint.setStyle(Paint.Style.FILL);
        overlayPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorGrayLightestTransparent));

        framePaint = new Paint();
        framePaint.setAntiAlias(true);
        framePaint.setFilterBitmap(true);
        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setStrokeWidth(strokeWidth);
        framePaint.setColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
    }

    private void drawOverlay(Canvas canvas) {
        path.reset();

        path.addRect(rectF, Path.Direction.CW);

        path.addCircle(currentRectF.centerX(), currentRectF.centerY(), currentRectF.width() / 2, Path.Direction.CCW);

        canvas.drawPath(path, overlayPaint);
    }

    private void drawFrame(Canvas canvas) {

        RectF frameRect = this.currentRectF;
        canvas.drawRect(frameRect, framePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private RectF currentRectF = new RectF();

    private final GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {

        float rectRight;
        float rectLeft;
        float rectBottom;
        float rectTop;

        @Override
        public boolean onDown(MotionEvent e) {
            Rect leftTopRect = new Rect(((int) currentRectF.left), ((int) currentRectF.top), ((int) currentRectF.left) + Utils.dp2px(getContext(), 16), ((int) currentRectF.top) + Utils.dp2px(getContext(), 16));
            Rect leftBottomRect = new Rect(((int) currentRectF.left), ((int) currentRectF.bottom) - Utils.dp2px(getContext(), 16), ((int) currentRectF.left) + Utils.dp2px(getContext(), 16), ((int) currentRectF.bottom));
            Rect rightTopRect = new Rect(((int) currentRectF.right) - Utils.dp2px(getContext(), 16), ((int) currentRectF.top), ((int) currentRectF.right), ((int) currentRectF.top) + Utils.dp2px(getContext(), 16));
            Rect rightBottomRect = new Rect(((int) currentRectF.right) - Utils.dp2px(getContext(), 16), ((int) currentRectF.bottom) - Utils.dp2px(getContext(), 16), ((int) currentRectF.right), ((int) currentRectF.bottom));

            int eventX = (int) e.getX();
            int eventY = (int) e.getY();
            if (leftTopRect.contains(eventX, eventY)) {
                touchArea = TouchArea.LEFT_TOP;
            } else if (leftBottomRect.contains(eventX, eventY)) {
                touchArea = TouchArea.LEFT_BOTTOM;
            } else if (rightTopRect.contains(eventX, eventY)) {
                touchArea = TouchArea.RIGHT_TOP;
            } else if (rightBottomRect.contains(eventX, eventY)) {
                touchArea = TouchArea.RIGHT_BOTTOM;
            } else if (currentRectF.contains(eventX, eventY)) {
                touchArea = TouchArea.CONTENT;
            } else {
                touchArea = TouchArea.NOTHING;
            }
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (touchArea == TouchArea.CONTENT) {

                AlertCroppedImageBinding binding = AlertCroppedImageBinding.inflate(LayoutInflater.from(getContext()), null, false);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Cropped Picture.")
                        .setView(binding.getRoot())
                        .setPositiveButton("OK", (dialog, which) -> {

                        });

                AlertDialog dialog = builder.create();
                dialog.show();

                binding.imageViewCroppedImage.setImageBitmap(getCroppedImage());

                return true;
            }

            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            float max = Math.max(distanceX, distanceY);
            float min = Math.min(distanceX, distanceY);
            float maxAbs = Math.max(Math.abs(min), Math.abs(max));

            if (touchArea == TouchArea.LEFT_TOP) {

                float distance;
                if (distanceX < 0 && distanceY < 0) {
                    distance = min;
                } else {
                    distance = max;
                }

                if (currentRectF.top - distance <= 0 || currentRectF.left - distance <= 0) {
                    distance = 0;
                }

                rectTop = currentRectF.top - distance;
                rectLeft = currentRectF.left - distance;

            } else if (touchArea == TouchArea.LEFT_BOTTOM) {

                float distance;
                if (distanceX > 0 && distanceY < 0) {
                    distance = max;
                } else if (distanceX < 0 && distanceY > 0) {
                    distance = min;
                } else {
                    if (maxAbs == min) {
                        distance = min;
                    } else {
                        distance = max;
                    }
                }

                if (currentRectF.bottom + distance >= getMeasuredHeight() || currentRectF.left - distance <= 0) {
                    distance = 0;
                }

                rectBottom = currentRectF.bottom + distance;
                rectLeft = currentRectF.left - distance;

            } else if (touchArea == TouchArea.CONTENT) {

                rectTop = currentRectF.top - distanceY;
                rectBottom = currentRectF.bottom - distanceY;

                rectLeft = currentRectF.left - distanceX;
                rectRight = currentRectF.right - distanceX;

                if (rectTop <= 0) {
                    rectTop = 0;
                    rectBottom = currentRectF.height();
                } else if (rectBottom >= getMeasuredHeight()) {
                    rectBottom = getMeasuredHeight();
                    rectTop = getMeasuredHeight() - currentRectF.height();
                }
                if (rectLeft - distanceX <= 0) {
                    rectLeft = 0;
                    rectRight = currentRectF.width();
                } else if (rectRight >= getMeasuredWidth()) {
                    rectRight = getMeasuredWidth();
                    rectLeft = getMeasuredWidth() - currentRectF.width();
                }
            }

            currentRectF.set(rectLeft, rectTop, rectRight, rectBottom);

            if (touchArea != TouchArea.NOTHING) {
                invalidate();
            }

            return true;
        }

    };

    private Bitmap getCroppedImage() {
        Bitmap bitmap = null;

        Drawable drawable = getDrawable();
        if (drawable == null) {
            return bitmap;
        }

        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }
        }

        DisplayMetrics metrics = new DisplayMetrics();
        int density = metrics.densityDpi;
        int scaledWidth = getMeasuredWidth() / bitmap.getScaledWidth(density);
        int scaledHeight = scaledWidth;

        Log.d(TAG, "getCroppedImage: " + bitmap.getWidth() + " " + scaledHeight);

        bitmap = Bitmap.createBitmap(bitmap, ((int) currentRectF.left / scaledWidth), ((int) currentRectF.top / scaledHeight), ((int) currentRectF.width() / scaledWidth), ((int) currentRectF.height() / scaledHeight));

        return bitmap;
    }
}
