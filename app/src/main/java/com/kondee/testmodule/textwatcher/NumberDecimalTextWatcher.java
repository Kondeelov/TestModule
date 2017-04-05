package com.kondee.testmodule.textwatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.kondee.testmodule.utils.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by Kondee on 4/3/2017.
 */

public abstract class NumberDecimalTextWatcher implements TextWatcher {

    private static final String TAG = "Kondee";
    private EditText editText;
    private DecimalFormat df;

    public NumberDecimalTextWatcher(EditText editText) {
        this.editText = editText;
        df = new DecimalFormat();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        editText.removeTextChangedListener(this);
        try {

            String rawString = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");

            editText.setText(DecimalFormat.getNumberInstance().format(Integer.parseInt(rawString)));

            editText.setSelection(editText.length());

        } catch (NumberFormatException e) {
            Log.d(TAG, "afterTextChanged: " + e.toString());
        }
        editText.addTextChangedListener(this);

    }
}
