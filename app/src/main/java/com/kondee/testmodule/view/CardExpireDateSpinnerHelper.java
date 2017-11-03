package com.kondee.testmodule.view;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.AlertDialogCardExpireDateBinding;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CardExpireDateSpinnerHelper {

    private static final String TAG = "Kondee";
    private final int maxMonth = 11;
    private List<String> monthList = new ArrayList<>();
    @NonNull
    private Context context;
    @Nullable
    private onDateSetListener listener;
    private int year;
    private int monthOfYear;
    private AlertDialogCardExpireDateBinding mBinding;
    private final Calendar calendar;
    private final String[] months;

    public CardExpireDateSpinnerHelper(@NonNull Context context, @Nullable onDateSetListener listener, int year, int monthOfYear) {
        this.context = context;
        this.listener = listener;
        this.year = year;
        this.monthOfYear = monthOfYear;
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.alert_dialog_card_expire_date, null, false);
        calendar = Calendar.getInstance(Locale.US);
        months = DateFormatSymbols.getInstance(Locale.getDefault()).getMonths();
        monthList = Arrays.asList(months);

        init();
    }

    private void init() {
        setUpPickerValue();

        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        builder.setView(mBinding.getRoot())
                .setPositiveButton(context.getString(R.string.label_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onDateSet(mBinding.year.getValue(), mBinding.month.getValue());
                        }
                    }
                })
                .setNegativeButton(context.getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setUpPickerValue() {
        mBinding.year.setMinValue(calendar.get(Calendar.YEAR));
        mBinding.year.setMaxValue(2100);
        mBinding.year.setWrapSelectorWheel(false);

        mBinding.month.setDisplayedValues(months);
        mBinding.month.setDisplayedValues(monthList.subList(calendar.get(Calendar.MONTH), maxMonth + 1).toArray(new String[0]));
        mBinding.month.setMinValue(calendar.get(Calendar.MONTH));
        mBinding.month.setMaxValue(maxMonth);
        mBinding.month.setWrapSelectorWheel(false);

        mBinding.month.setOnValueChangedListener((picker, oldVal, newVal) -> {

            if (Math.abs(newVal - oldVal) != 1) {
                if (isDown(oldVal, newVal)) {
                    if (mBinding.year.getValue() - 1 >= mBinding.year.getMinValue()) {
                        mBinding.year.setValue(mBinding.year.getValue() - 1);
                    }
                } else {
                    if (mBinding.year.getValue() + 1 <= mBinding.year.getMaxValue()) {
                        mBinding.year.setValue(mBinding.year.getValue() + 1);
                    }
                }
            }

            if (mBinding.year.getValue() == calendar.get(Calendar.YEAR)) {
                if (isDown(oldVal, newVal)) {
                    if (newVal == calendar.get(Calendar.MONTH)) {
                        mBinding.month.setDisplayedValues(months);
                        mBinding.month.setMinValue(calendar.get(Calendar.MONTH));
                        mBinding.month.setWrapSelectorWheel(false);
                        mBinding.month.setDisplayedValues(monthList.subList(calendar.get(Calendar.MONTH), maxMonth + 1).toArray(new String[0]));
                    } else {
                        mBinding.month.setDisplayedValues(months);
                        mBinding.month.setMinValue(0);
                        mBinding.month.setWrapSelectorWheel(true);
                    }
                } else {
                    mBinding.month.setDisplayedValues(months);
                    mBinding.month.setMinValue(0);
                    mBinding.month.setWrapSelectorWheel(true);
                }
            } else {
                mBinding.month.setDisplayedValues(months);
                mBinding.month.setMinValue(0);
                mBinding.month.setWrapSelectorWheel(true);
            }
        });

        mBinding.year.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (newVal == mBinding.year.getMinValue()) {
                mBinding.month.setDisplayedValues(months);
                mBinding.month.setMinValue(calendar.get(Calendar.MONTH));
                mBinding.month.setWrapSelectorWheel(false);
                mBinding.month.setDisplayedValues(monthList.subList(calendar.get(Calendar.MONTH), maxMonth + 1).toArray(new String[0]));
                mBinding.month.setValue(calendar.get(Calendar.MONTH));
            }
        });
    }

    private boolean isDown(int oldVal, int newVal) {
        if (Math.abs(newVal - oldVal) != 1) {
            if (newVal - oldVal > 0) {
                return true;
            }
            return false;
        }
        return oldVal > newVal;
    }

    private boolean isUp(int oldVal, int newVal) {
        if (Math.abs(newVal - oldVal) != 1) {
            if (newVal - oldVal < 0) {
                return true;
            }
            return false;
        } else return newVal > oldVal;
    }

    /***********
     * Listener
     ***********/

    public interface onDateSetListener {
        void onDateSet(int year, int month);
    }
}
