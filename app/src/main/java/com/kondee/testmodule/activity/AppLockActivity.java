package com.kondee.testmodule.activity;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kondee.testmodule.R;
import com.kondee.testmodule.applock.AppLock;
import com.kondee.testmodule.databinding.ActivityAppLockBinding;
import com.kondee.testmodule.view.AppLockView;

public class AppLockActivity extends AppCompatActivity {

    private static final String TAG = "Kondee";
    ActivityAppLockBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_app_lock);

        initInstance();
    }

    private void initInstance() {
        binding.appLockView.setOnPinCodeListener(new AppLockView.OnPinCodeListener() {
            @Override
            public void onPinCorrect() {
                Log.d(TAG, "onPinCorrect: " + AppLockActivity.this);
                AppLock.intentAppLockActivityToTarget(AppLockActivity.this);
            }
        });

        binding.appLockView.setPinCode("1111");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
    }


}
