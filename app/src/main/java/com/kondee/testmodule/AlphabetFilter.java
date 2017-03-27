package com.kondee.testmodule;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by Kondee on 3/16/2017.
 */

public class AlphabetFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (source.toString().matches("[a-zA-Z ]+")) {
            return source;
        }
        return null;
    }
}
