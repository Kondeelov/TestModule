package com.kondee.testmodule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.NumberPicker;

import com.kondee.testmodule.databinding.CustomDialogDatePickerBinding;

import java.util.Calendar;

public class CustomDatePickerDialog extends AlertDialog {

    private static final String TAG = "Kondee";
    private CustomDialogDatePickerBinding binding;
    private Calendar calendar;
    private String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    public CustomDatePickerDialog(@NonNull Context context) {
        this(context, 0);
    }

    public CustomDatePickerDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context, Calendar.getInstance());
    }

    private void init(Context context, Calendar calendar) {
        this.calendar = calendar;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = CustomDialogDatePickerBinding.inflate(inflater);

        setView(binding.getRoot());

        binding.dayPicker.setMinValue(1);
        binding.dayPicker.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        binding.dayPicker.setOnValueChangedListener(onValueChangedListener);

        binding.monthPicker.setMinValue(0);
        binding.monthPicker.setMaxValue(11);
        binding.monthPicker.setDisplayedValues(months);
        binding.monthPicker.setOnValueChangedListener(onValueChangedListener);

        binding.yearPicker.setMinValue(calendar.get(Calendar.YEAR) - 100);
        binding.yearPicker.setMaxValue(calendar.get(Calendar.YEAR));
        binding.yearPicker.setOnValueChangedListener(onValueChangedListener);

        binding.yearPicker.setWrapSelectorWheel(false);
        binding.monthPicker.setWrapSelectorWheel(false);
        binding.dayPicker.setWrapSelectorWheel(false);

        updateDate();
    }

    private void updateDate() {
        binding.dayPicker.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        binding.dayPicker.setValue(calendar.get(Calendar.DAY_OF_MONTH));

        binding.monthPicker.setValue(calendar.get(Calendar.MONTH));

        binding.monthPicker.setValue(calendar.get(Calendar.YEAR));
    }


    private void updateMaxMinValue() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(binding.yearPicker.getValue(),binding.monthPicker.getValue(),binding.dayPicker.getValue());
        binding.dayPicker.setMaxValue(calendar1.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    /***********
     * Listener
     ***********/

    private NumberPicker.OnValueChangeListener onValueChangedListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            Log.d(TAG, "onValueChange: ");
            updateMaxMinValue();
        }
    };

}
