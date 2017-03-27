package com.kondee.testmodule;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by Kondee on 3/16/2017.
 */

public class NumericFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            if (Character.isDigit(Integer.parseInt(source.toString()))) {
                return source;
            }
            return "";
        } catch (NumberFormatException e) {
            return "";
        }
    }
}
