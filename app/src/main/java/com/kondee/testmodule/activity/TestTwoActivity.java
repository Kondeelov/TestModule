package com.kondee.testmodule.activity;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.kondee.testmodule.BlurBitmapFactory;
import com.kondee.testmodule.R;
import com.kondee.testmodule.TestAlertBlurBackgroundDialog;
import com.kondee.testmodule.databinding.ActivityTestTwoBinding;
import com.kondee.testmodule.fragment.TestDialogFragment;
import com.kondee.testmodule.manager.Contextor;
import com.kondee.testmodule.utils.Utils;

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


                TestAlertBlurBackgroundDialog dialog = TestAlertBlurBackgroundDialog.newInstance(TestTwoActivity.this);
                dialog.show(getSupportFragmentManager(), "test");
            }
        });
    }
}
