package com.kondee.testmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ActivityExtraTestBinding;


public class ExtraTestActivity extends AppCompatActivity {

    private static final String TAG = "Kondee";
    ActivityExtraTestBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_extra_test);

        initInstance();
    }

    private void initInstance() {

    }
}
