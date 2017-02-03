package com.kondee.testmodule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kondee.testmodule.R;
import com.kondee.testmodule.listener.HideSoftInputOnFocusChangeListener;
import com.kondee.testmodule.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class LottoDigitsEditText extends LinearLayout {

    private static final String TAG = "Kondee";

    private int digitsAmount;
    private int fontSize;
    private List<EditText> editTextList = new ArrayList<>();
    private EditText fillEditText;
    private int newHeightMeasureSpec;

    public LottoDigitsEditText(Context context) {
        super(context);
        init();
    }

    public LottoDigitsEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, android.R.attr.editTextStyle, 0);
    }

    public LottoDigitsEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, android.R.attr.editTextStyle, 0);
    }

    @RequiresApi(21)
    public LottoDigitsEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs, android.R.attr.editTextStyle, defStyleRes);
    }

    private void init() {

//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        binding = LottoDigitsEdittextBinding.inflate(inflater, null, false);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setOrientation(HORIZONTAL);
        setLayoutParams(linearLayoutParams);

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.PatternUnlockView,
                defStyleAttr,
                defStyleRes);

        try {
            digitsAmount = a.getInt(R.styleable.LottoDigitsEditText_digits, 5);
            fontSize = a.getInt(R.styleable.LottoDigitsEditText_fontSize, 36);
        } finally {
            a.recycle();
        }

        executeEditText(attrs, defStyleAttr);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Utils.dp2px(getContext(), fontSize), MeasureSpec.EXACTLY);
//
//        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
//
//        setMeasuredDimension(widthMeasureSpec, newHeightMeasureSpec);
//    }

    private void executeEditText(AttributeSet attrs, int defStyleAttr) {

//        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_unselect));

        for (int i = 0; i < digitsAmount; i++) {

            final EditText editText = new EditText(getContext(), attrs, defStyleAttr);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            params.gravity = Gravity.BOTTOM;
            editText.setLayoutParams(params);

//            editText.setGravity(Gravity.CENTER);
            setFocusable(false);
            editText.setFocusableInTouchMode(false);
            editText.setHint("0");
            editText.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorGrayLightestTransparent));
            editText.setTextSize(fontSize);
            editText.setBackgroundColor(Color.TRANSPARENT);

            int viewId = View.generateViewId();
            editText.setId(viewId);

            editText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int j = 0; j < editTextList.size(); j++) {

                        editTextList.get(j).setVisibility(GONE);
                    }

                    fillEditText.setText("");
                    fillEditText.setVisibility(VISIBLE);
                    fillEditText.requestFocus();
                }
            });

            editTextList.add(editText);

            addView(editText);
        }

        fillEditText = new EditText(getContext(), attrs, defStyleAttr);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        fillEditText.setLayoutParams(params);

        fillEditText.setFocusableInTouchMode(true);
        fillEditText.setGravity(Gravity.END | Gravity.CENTER);
        fillEditText.setPadding(Utils.dp2px(getContext(), 8), 0, Utils.dp2px(getContext(), 8), 0);
        fillEditText.setId(R.id.editText);
        fillEditText.setTextSize(fontSize);
        fillEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        fillEditText.setKeyListener(DigitsKeyListener.getInstance("1234567890"));

        fillEditText.setBackgroundColor(Color.TRANSPARENT);
        fillEditText.setVisibility(GONE);
        fillEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        InputFilter[] inputFilters = new InputFilter[1];
        inputFilters[0] = new InputFilter.LengthFilter(digitsAmount);
        fillEditText.setFilters(inputFilters);

        fillEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocus();
                    return true;
                }
                return false;
            }
        });

        fillEditText.setOnFocusChangeListener(new HideSoftInputOnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                super.onFocusChange(v, hasFocus);

                if (!hasFocus) {

                    setShowDigits();

                    v.setVisibility(GONE);
                }

                if (focusChangeListener != null) {
                    focusChangeListener.onFocusChange(v, hasFocus);
                }
            }
        });

        addView(fillEditText);
    }

    private void setShowDigits() {
        char[] chars = fillEditText.getText().toString().toCharArray();

        for (int j = editTextList.size() - 1; j >= 0; j--) {

            editTextList.get(j).setVisibility(VISIBLE);

            try {
                editTextList.get(j).setText(chars, (chars.length - (editTextList.size() - j)), 1);
            } catch (IndexOutOfBoundsException e) {

                editTextList.get(j).setText("");
                editTextList.get(j).setHint("0");
            }
        }
    }

    public String getText() {
        return fillEditText.getText().toString();
    }

    public void setText(String text) {
        fillEditText.setText(text);
        setShowDigits();
    }


    /***********
     * Listener
     ***********/

    private onFocusChangeListener focusChangeListener;

    public interface onFocusChangeListener {
        void onFocusChange(View v, boolean hasFocus);
    }

    public void setOnFocusChangeListener(onFocusChangeListener focusChangeListener) {
        this.focusChangeListener = focusChangeListener;
    }
}