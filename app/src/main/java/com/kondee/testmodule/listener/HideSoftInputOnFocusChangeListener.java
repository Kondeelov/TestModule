package com.kondee.testmodule.listener;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.kondee.testmodule.manager.Contextor;

public class HideSoftInputOnFocusChangeListener implements View.OnFocusChangeListener {

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        InputMethodManager inputMethodManager = (InputMethodManager) Contextor.getInstance().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (hasFocus) {
            inputMethodManager.showSoftInput(v, 0);
        } else {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            v.clearFocus();
        }
    }
}
