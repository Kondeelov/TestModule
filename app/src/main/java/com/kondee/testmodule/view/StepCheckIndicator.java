package com.kondee.testmodule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import com.kondee.testmodule.R;
import com.kondee.testmodule.utils.Utils;

/**
 * Created by Kondee on 6/30/2017.
 */

public class StepCheckIndicator extends View {

    private static final String TAG = "Kondee";
    private final int RADIUS = Utils.dp2px(getContext(), 6);

    private Paint strokePaint = new Paint();
    private Paint checkPaint = new Paint();
    private Paint filledPaint = new Paint();
    private int number = 4;
    private int position = 2;
    SparseArray<Point> pointList = new SparseArray<>();
    private int color = ContextCompat.getColor(getContext(), R.color.colorGreen);
    private int strokeWidth = Utils.dp2px(getContext(), 1);

    public StepCheckIndicator(Context context) {
        super(context);
        init();
    }

    public StepCheckIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0, 0);
    }


    public StepCheckIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StepCheckIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.position = this.position;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.position = savedState.position;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = RADIUS * 2 + strokeWidth * 2;

        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

        int min = Math.min(width, height);

        setPoint(width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCanvas(canvas);
    }

    private void init() {

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        preparePaint();
    }

    private void preparePaint() {
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(color);
        strokePaint.setAntiAlias(true);
        strokePaint.setStrokeWidth(strokeWidth);
        strokePaint.setDither(true);

        checkPaint.setStyle(Paint.Style.STROKE);
        checkPaint.setColor(color);
        checkPaint.setAntiAlias(true);
        checkPaint.setStrokeWidth(Utils.dp2px(getContext(), 2));
        checkPaint.setDither(true);

        filledPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        filledPaint.setColor(color);
        filledPaint.setStrokeWidth(strokeWidth);
        filledPaint.setDither(true);
    }

    private void setPoint(int width) {

        int absolutePosition = width - (RADIUS * 4);
        float start = RADIUS * 2;

        for (int i = 0; i < number; i++) {
            float positionX = start + (absolutePosition / 3 * i);
            float positionY = getMeasuredHeight() / 2;

            Point point = new Point();
            point.set(((int) positionX), ((int) positionY));

            pointList.put(i, point);
        }
    }

    private void drawCanvas(Canvas canvas) {
        for (int i = 0; i < pointList.size(); i++) {
            Point point = pointList.get(i);

            if (i < position) {
                canvas.drawLine(point.x - (RADIUS * 0.7f), point.y + RADIUS, point.x - RADIUS - (RADIUS * 0.3f), point.y + (RADIUS * 0.3f), checkPaint);
                canvas.drawLine(point.x - (RADIUS * 0.7f), point.y + RADIUS, point.x + RADIUS, point.y - RADIUS, checkPaint);
            } else if (i == position) {
                canvas.drawCircle(point.x, point.y, RADIUS, filledPaint);
            } else if (i > position) {
                canvas.drawCircle(point.x, point.y, RADIUS, strokePaint);
            }
        }
    }

    public void setPosition(int position) {
        this.position = position;

        invalidate();
    }


    /*************
     * SavedState
     *************/

    private static class SavedState extends BaseSavedState {

        int position;

        private SavedState(Parcel in) {
            super(in);
            position = in.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(position);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
