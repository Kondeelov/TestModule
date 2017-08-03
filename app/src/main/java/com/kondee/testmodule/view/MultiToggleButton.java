package com.kondee.testmodule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
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

import java.util.HashMap;
import java.util.Map;

public class MultiToggleButton extends LinearLayout {
    private static final String TAG = "Kondee";
    private CharSequence[] values;
    HashMap<Button, Boolean> buttonStateMap = new HashMap<>();
    private int primaryColor;
    private int secondaryColor;
    private onStateChangeListener listener;
    private int position = -1;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        } finally {
            a.recycle();
        }

        setupView();

        setPadding(dp2px(getContext(), 2), dp2px(getContext(), 2), dp2px(getContext(), 2), dp2px(getContext(), 2));

        setGravity(Gravity.CENTER);

        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_multi_toggle_button));
    }

    private void setupView() {
        if (values == null) {
            return;
        }

        for (int i = 0; i < values.length; i++) {
            Button button = new Button(getContext());
            LayoutParams params = new TableLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            if (i != values.length - 1) {
                params.setMarginEnd(dp2px(getContext(), 1.0f));
            }
            button.setLayoutParams(params);

            button.setMinHeight(dp2px(getContext(), 18));
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            button.setText(values[i]);
            button.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
            button.setId(i);

            if (i == 0) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.background_multi_toggle_button_left);
                button.setBackground(drawable);
            } else if (i == values.length - 1) {
                button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_multi_toggle_button_right));
            } else {
                button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_multi_toggle_button_center));
            }

            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDefault();
                    buttonStateMap.replace(button, true);

                    setStateDisplayed();

                    if (listener != null) {
                        listener.onStateChanged(v, button.getId(), button.getText().toString());
                    }
                }
            });

            if (position == i) {
                buttonStateMap.put(button, true);
            } else {
                buttonStateMap.put(button, false);
            }

            setStateDisplayed();

            addView(button);
        }
    }

    private void setDefault() {
        for (Button button : buttonStateMap.keySet()) {
            buttonStateMap.replace(button, false);
        }
    }

    private void setStateDisplayed() {
        for (Map.Entry<Button, Boolean> buttonMap : buttonStateMap.entrySet()) {
            Button button = buttonMap.getKey();
            if (buttonStateMap.get(button)) {
                button.getBackground().setColorFilter(primaryColor, PorterDuff.Mode.MULTIPLY);
//                button.setBackgroundColor(primaryColor);
                button.setTextColor(secondaryColor);
            } else {
                button.getBackground().setColorFilter(secondaryColor, PorterDuff.Mode.SRC_IN);
//                button.setBackgroundColor(secondaryColor);
                button.setTextColor(primaryColor);
            }
        }
    }

    public void setSelected(int position) {
        this.position = position;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
//        Log.d(TAG, "onMeasure: " + getMeasuredWidth());
    }

    public static int dp2px(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.scaledDensity);
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

}
