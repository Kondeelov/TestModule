package com.kondee.testmodule;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.NumberPicker;

import com.kondee.testmodule.databinding.CustomDialogDatePickerBinding;

import java.util.Calendar;

public class CustomDatePickerDialog extends AlertDialog.Builder {

    private static final String TAG = "Kondee";
    private CustomDialogDatePickerBinding binding;
    private Calendar calendar;
//    private String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private Calendar maxCalendar;
    private Calendar minCalendar;
    int monthMinValue = 1;
    int monthMaxValue = 12;

    public CustomDatePickerDialog(@NonNull Context context, onDatePickerListener listener) {
        this(context, listener, Calendar.getInstance());
    }

    public CustomDatePickerDialog(@NonNull Context context, onDatePickerListener listener, Calendar calendar) {
        super(context);

        init(context, calendar);

        this.listener = listener;
    }

    private void init(Context context, Calendar calendar) {
        this.calendar = calendar;

        if (maxCalendar == null) {
            maxCalendar = (Calendar) calendar.clone();
        }
        if (minCalendar == null) {
            minCalendar = (Calendar) calendar.clone();
            minCalendar.roll(Calendar.YEAR, -100);
        }

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = CustomDialogDatePickerBinding.inflate(inflater);

        setView(binding.getRoot());

        setPositiveButton(getContext().getString(R.string.label_ok), onPositiveButtonClickListener);
        setNegativeButton(getContext().getString(R.string.label_cancel), onNegativeButtonClickListener);

        binding.dayPicker.setMinValue(1);
        binding.dayPicker.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        binding.monthPicker.setMinValue(monthMinValue);
        binding.monthPicker.setMaxValue(monthMaxValue);
//        binding.monthPicker.setDisplayedValues(null);

        binding.yearPicker.setMinValue(minCalendar.get(Calendar.YEAR));
        binding.yearPicker.setMaxValue(maxCalendar.get(Calendar.YEAR));
//        binding.yearPicker.setMinValue(calendar.get(Calendar.YEAR) - 100);
//        binding.yearPicker.setMaxValue(calendar.get(Calendar.YEAR));

        binding.dayPicker.setOnValueChangedListener(onValueChangedListener);
        binding.monthPicker.setOnValueChangedListener(onValueChangedListener);
        binding.yearPicker.setOnValueChangedListener(onValueChangedListener);

        binding.yearPicker.setWrapSelectorWheel(false);

        updateDate();
    }

    private void updateDate() {
//        binding.dayPicker.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        binding.yearPicker.setValue(calendar.get(Calendar.YEAR));

        binding.monthPicker.setValue(calendar.get(Calendar.MONTH) + 1);

        binding.dayPicker.setValue(calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void updateMaxMinValue(int id, int oldVal, int newVal) {
        calendar.set(binding.yearPicker.getValue(), binding.monthPicker.getValue() - 1, 1);

        if (binding.dayPicker.getValue() > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            binding.dayPicker.setValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        binding.dayPicker.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, binding.dayPicker.getValue());

        binding.monthPicker.setWrapSelectorWheel(true);

        if (binding.yearPicker.getValue() == ((int) minCalendar.get(Calendar.YEAR))) {
            binding.monthPicker.setMinValue(minCalendar.get(Calendar.MONTH));
            binding.monthPicker.setWrapSelectorWheel(true);
            if (binding.monthPicker.getValue() == ((int) minCalendar.get(Calendar.MONTH))) {
                binding.monthPicker.setWrapSelectorWheel(false);
                binding.dayPicker.setMinValue(minCalendar.get(Calendar.DAY_OF_MONTH));
            } else {
                binding.dayPicker.setMinValue(1);
                binding.monthPicker.setWrapSelectorWheel(true);
            }
        } else if (binding.yearPicker.getValue() == ((int) maxCalendar.get(Calendar.YEAR))) {
            binding.monthPicker.setMaxValue(maxCalendar.get(Calendar.MONTH));
            binding.monthPicker.setWrapSelectorWheel(true);
            if (binding.monthPicker.getValue() == ((int) maxCalendar.get(Calendar.MONTH))) {
                binding.monthPicker.setWrapSelectorWheel(false);
                binding.dayPicker.setMaxValue(maxCalendar.get(Calendar.DAY_OF_MONTH));
            } else {
                binding.dayPicker.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                binding.monthPicker.setWrapSelectorWheel(true);
            }
        } else {
            binding.monthPicker.setMinValue(1);
            binding.monthPicker.setMaxValue(12);
        }
    }

    public void setMaxCalendar(Calendar calendar) {
        setMaxCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void setMaxCalendar(int year, int month, int date) {
        maxCalendar.set(Calendar.YEAR, year);
        maxCalendar.set(Calendar.MONTH, month);
        maxCalendar.set(Calendar.DAY_OF_MONTH, date);
    }

    public void setMinCalendar(Calendar calendar) {
        setMinCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void setMinCalendar(int year, int month, int date) {
        minCalendar.set(Calendar.YEAR, year);
        minCalendar.set(Calendar.MONTH, month);
        minCalendar.set(Calendar.DAY_OF_MONTH, date);
    }

    /***********
     * Listener
     ***********/
    private onDatePickerListener listener;

    private NumberPicker.OnValueChangeListener onValueChangedListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            updateMaxMinValue(picker.getId(), oldVal, newVal);
        }
    };


    private DialogInterface.OnClickListener onPositiveButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (listener != null) {
                listener.onDatePick(calendar);
            }
            dialog.dismiss();
        }
    };

    private DialogInterface.OnClickListener onNegativeButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (listener != null) {
                listener.onCancel();
            }
            dialog.dismiss();
        }
    };

    public interface onDatePickerListener {
        void onDatePick(Calendar calendar);

        void onCancel();
    }

    public void setOnDatePickerListener(onDatePickerListener listener) {
        this.listener = listener;
    }

}
