package com.kondee.testmodule.view;

import android.graphics.Rect;

public class PatternUnlockRect {

    Rect rect;
    private String value;
    private boolean isSelected;

    public PatternUnlockRect(Rect rect, String value, boolean isSelected) {
        this.rect = rect;
        this.value = value;
        this.isSelected = isSelected;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
