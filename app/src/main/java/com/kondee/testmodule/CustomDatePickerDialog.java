package com.kondee.testmodule;

import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondee.testmodule.databinding.CustomDialogDatePickerBinding;

public class CustomDatePickerDialog extends DialogFragment {

    CustomDialogDatePickerBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.custom_dialog_date_picker,container,false);
        initInstance();

        return binding.getRoot();
    }

    private void initInstance() {

    }
}
