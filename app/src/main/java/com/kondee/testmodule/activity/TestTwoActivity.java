package com.kondee.testmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ActivityTestTwoBinding;

public class TestTwoActivity extends AppCompatActivity {

    ActivityTestTwoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_two);

        initInstance();
    }

    private void initInstance() {


    }
}
