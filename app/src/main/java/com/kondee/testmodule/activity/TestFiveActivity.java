package com.kondee.testmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ActivityTestFiveBinding;
import com.kondee.testmodule.view.PatternUnlockView;

import java.util.List;

public class TestFiveActivity extends AppCompatActivity {

    private static final String TAG = "Kondee";

    ActivityTestFiveBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_five);

        initInstance();
    }

    private void initInstance() {
        binding.patternView.setOnPatternDetectListener(new PatternUnlockView.OnPatternDetectListener() {
            @Override
            public void onPatternDetected(List<String> pattern) {

                String patternString = TextUtils.join(",", pattern);
                Log.d(TAG, "onPatternDetected: " + patternString);
            }
        });

    }

    /***********
     * Listener
     ***********/
}
