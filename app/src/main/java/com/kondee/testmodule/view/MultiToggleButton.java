package com.kondee.testmodule.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.kondee.testmodule.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Kondee on 5/24/2017.
 */

public class MultiToggleButton extends LinearLayout {
    private static final String TAG = "Kondee";
    private CharSequence[] values;
    HashMap<Integer, Boolean> buttonStateMap = new HashMap<>();
    HashMap<Integer, Button> buttonList = new HashMap<>();
    private int primaryColor;
    private int secondaryColor;
    private onStateChangeListener listener;
    private int position = 0;
    private ViewPager viewPager;
    private int selectedTextColor;
    private int unselectedTextColor;
    private int backgroundColor;
    private int backgroundRadius;
    private ShapeDrawable backgroundDrawable;

    public MultiToggleButton(Context context) {
        super(context);
        init();
    }

    public MultiToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0, 0);
    }

    public MultiToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MultiToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setSelected(0);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs
                , R.styleable.multi_toggle_button,
                defStyleAttr,
                defStyleRes);

        try {
            values = a.getTextArray(R.styleable.multi_toggle_button_values);
            primaryColor = a.getColor(R.styleable.multi_toggle_button_primaryColor, Color.MAGENTA);
            secondaryColor = a.getColor(R.styleable.multi_toggle_button_secondaryColor, Color.CYAN);
            selectedTextColor = a.getColor(R.styleable.multi_toggle_button_selectedTextColor, secondaryColor);
            unselectedTextColor = a.getColor(R.styleable.multi_toggle_button_unselectedTextColor, primaryColor);
            backgroundColor = a.getColor(R.styleable.multi_toggle_button_backgroundColor, ContextCompat.getColor(getContext(), R.color.colorBlack));
            backgroundRadius = a.getDimensionPixelSize(R.styleable.multi_toggle_button_backgroundRadius, dp2px(getContext(), 8));
        } finally {
            a.recycle();
        }

        setPadding(dp2px(getContext(), 1), dp2px(getContext(), 1), dp2px(getContext(), 1), dp2px(getContext(), 1));

        setGravity(Gravity.CENTER);
    }

    @Override
    public void setBackground(Drawable background) {
        if (background != backgroundDrawable) {
            background = backgroundDrawable;
        }
        super.setBackground(background);
    }

    private void setupView() {
        if (values == null) {
            return;
        }

        buttonList.clear();
        removeAllViews();

        backgroundDrawable = new ShapeDrawable(new RoundRectShape(new float[]{backgroundRadius, backgroundRadius, backgroundRadius, backgroundRadius, backgroundRadius, backgroundRadius, backgroundRadius, backgroundRadius}, null, null));
        backgroundDrawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        backgroundDrawable.getPaint().setColor(backgroundColor);
        setBackground(backgroundDrawable);

        for (int i = 0; i < values.length; i++) {
            final Button button = new Button(getContext());
            LayoutParams params = new TableLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            if (i != values.length - 1) {
                params.setMarginEnd(dp2px(getContext(), 1.0f));
            }
            button.setLayoutParams(params);

            button.setMinHeight(dp2px(getContext(), 24));
            button.setGravity(Gravity.CENTER);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            button.setAllCaps(false);
            button.setText(values[i]);
            button.setTextColor(unselectedTextColor);
            button.setId(i);
            if (!isInEditMode()) {
                Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), CalligraphyConfig.get().getFontPath());
                button.setTypeface(typeface, Typeface.NORMAL);
            }

            int finalI = i;
            button.setOnClickListener(v -> setSelected(finalI));

            if (position == i) {
                buttonStateMap.put(i, true);
            } else {
                buttonStateMap.put(i, false);
            }

            buttonList.put(i, button);

            setStateDisplayed();

            addView(button);
        }
    }

    private void setDefault() {
        for (int i : buttonStateMap.keySet()) {
            buttonStateMap.put(i, false);
        }
    }

    public void setupWithViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setStateDisplayed() {
        if (buttonList.size() == 0) {
            return;
        }

        ShapeDrawable drawable;

        int radius;

        if (backgroundRadius >= dp2px(getContext(), 1)) {
            radius = backgroundRadius - dp2px(getContext(), 1);
        } else {
            radius = 0;
        }

        ShapeDrawable leftDrawable = new ShapeDrawable(new RoundRectShape(new float[]{radius, radius, 0, 0, 0, 0, radius, radius}, null, null));
        leftDrawable.getPaint().setStyle(Paint.Style.FILL);

        ShapeDrawable rightDrawable = new ShapeDrawable(new RoundRectShape(new float[]{0, 0, radius, radius, radius, radius, 0, 0}, null, null));
        rightDrawable.getPaint().setStyle(Paint.Style.FILL);

        ShapeDrawable normalDrawable = new ShapeDrawable(new RectShape());
        rightDrawable.getPaint().setStyle(Paint.Style.FILL);

        for (int key : buttonStateMap.keySet()) {
            Button button = buttonList.get(key);

            int textColor;
            if (buttonStateMap.get(key)) {

                if (listener != null) {
                    listener.onStateChanged(button, key, button.getText().toString());
                }
                if (viewPager != null) {
                    viewPager.post(() -> viewPager.setCurrentItem(position, true));
                }

                textColor = selectedTextColor;
                if (key == 0) {
                    leftDrawable.getPaint().setColor(primaryColor);
                    drawable = leftDrawable;
                } else if (key == buttonStateMap.size() - 1) {
                    rightDrawable.getPaint().setColor(primaryColor);
                    drawable = rightDrawable;
                } else {
                    normalDrawable.getPaint().setColor(primaryColor);
                    drawable = normalDrawable;
                }
            } else {
                textColor = unselectedTextColor;

                if (key == 0) {
                    leftDrawable.getPaint().setColor(secondaryColor);
                    drawable = leftDrawable;
                } else if (key == buttonStateMap.size() - 1) {
                    rightDrawable.getPaint().setColor(secondaryColor);
                    drawable = rightDrawable;
                } else {
                    normalDrawable.getPaint().setColor(secondaryColor);
                    drawable = normalDrawable;
                }
            }
            button.setBackground(drawable);
            button.setTextColor(textColor);
        }
    }

    public void setSelected(int position) {
        this.position = position;
        setDefault();
        buttonStateMap.put(position, true);
        setStateDisplayed();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        setupView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.position = this.position;
        savedState.map = this.buttonStateMap;
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
        this.buttonStateMap = savedState.map;

        setStateDisplayed();
    }

    public static int dp2px(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.scaledDensity);
    }

    public MultiToggleButton setValues(CharSequence[] values) {
        this.values = values;
        return this;
    }

    /***********
     * Listener
     ***********/

    public interface onStateChangeListener {
        void onStateChanged(View v, int position, String value);
    }

    public void setOnStateChangeListener(onStateChangeListener listener) {
        this.listener = listener;
    }

    /**************
     * SaveState
     **************/

    private static class SavedState extends BaseSavedState {

        private int position;
        private HashMap<Integer, Boolean> map;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            position = in.readInt();
            map = in.readHashMap(Boolean.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(position);
            out.writeMap(map);
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
