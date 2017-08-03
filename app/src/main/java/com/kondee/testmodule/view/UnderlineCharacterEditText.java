package com.kondee.testmodule.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;

import com.kondee.testmodule.utils.Utils;

public class UnderlineCharacterEditText extends AppCompatTextView {
    private static final String TAG = "Kondee";
    private int position = 0;
    private Rect bound = new Rect();
    private Paint paint = new Paint();
    int measuredHeight;
    float startX = 0;
    private float stopX;
    private float lastStartX = 0;
    private float lastStopX = 0;
    private float start;
    private float stop;


    public UnderlineCharacterEditText(Context context) {
        super(context);
        init();
    }

    public UnderlineCharacterEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0);
    }

    public UnderlineCharacterEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr);
    }

    private void init() {

        setText("And-I-will-always-love-you");
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr) {

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(getContext(), 2));
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measuredHeight = getMeasuredHeight() - Utils.dp2px(getContext(), 2);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        setPosition(position, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(start, measuredHeight, stop, measuredHeight, paint);

    }

    public void setPosition(int position, boolean animate) {
        if (getText().length() == 0) {
            return;
        }

        if (position >= getText().length()) {
            this.position = 0;
        } else {
            this.position = position;
        }

        float[] widths = new float[getText().length()];

        getPaint().getTextWidths(getText().toString().toCharArray(), 0, getText().length(), widths);

        Layout layout = this.getLayout();

        if (animate) {
            startX = layout.getPrimaryHorizontal(this.position);
            stopX = startX + widths[this.position];

            ValueAnimator startAnimator = ValueAnimator.ofFloat(lastStartX, startX);
            startAnimator.addUpdateListener(animation -> {
                start = (float) animation.getAnimatedValue();
            });

            ValueAnimator stopAnimator = ValueAnimator.ofFloat(lastStopX, stopX);
            stopAnimator.addUpdateListener(animation -> {
                stop = (float) animation.getAnimatedValue();
                invalidate();
            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(startAnimator, stopAnimator);
            animatorSet.setDuration(200);
            animatorSet.start();

            lastStartX = startX;
            lastStopX = stopX;
        } else {

            start = layout.getPrimaryHorizontal(this.position);
            stop = start + widths[this.position];

            lastStartX = start;
            lastStopX = stop;

            invalidate();
        }
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.position = savedState.position;

    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.position = this.position;

        return savedState;
    }

    /*************
     * SavedState
     *************/

    private static class SavedState extends BaseSavedState {

        private int position;

        private SavedState(Parcel in) {
            super(in);
            position = in.readInt();
        }

        SavedState(Parcelable superState) {
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
