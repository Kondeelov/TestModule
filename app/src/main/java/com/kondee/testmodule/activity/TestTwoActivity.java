package com.kondee.testmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kondee.testmodule.R;
import com.kondee.testmodule.TestAlertBlurBackgroundDialog;
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
        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TestDialogFragment fragment = TestDialogFragment.newInstance();
//                fragment.show(getSupportFragmentManager(), "Test");

//                AlertDialog.Builder builder = new AlertDialog.Builder(TestTwoActivity.this, R.style.Theme_D1NoTitleDim);
//                builder.setView(R.layout.alert_test_blur);
//                builder.setRecycleOnMeasureEnabled(true);
//
//                AlertDialog dialog = builder.create();
//
//                Bitmap blurActivity = BlurBitmapFactory.getBlurActivity(TestTwoActivity.this);
//                Drawable drawable = new BitmapDrawable(getResources(), blurActivity);
//
//                Window window = dialog.getWindow();
//
//                dialog.getWindow().setBackgroundDrawable(drawable);
//
//                window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                window.setGravity(Gravity.CENTER);
//
//                dialog.show();


                TestAlertBlurBackgroundDialog dialog = TestAlertBlurBackgroundDialog.newInstance(TestTwoActivity.this, binding.btnTest);
                dialog.show(getSupportFragmentManager(), "test");
            }
        });

        binding.btnTest2.setOnClickListener(v -> {
            TestAlertBlurBackgroundDialog dialog = TestAlertBlurBackgroundDialog.newInstance(TestTwoActivity.this, binding.btnTest2);
            dialog.show(getSupportFragmentManager(), "test");
        });
    }
}
