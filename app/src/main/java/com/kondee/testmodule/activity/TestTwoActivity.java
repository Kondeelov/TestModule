package com.kondee.testmodule.activity;

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

import com.kondee.testmodule.BlurBitmapFactory;
import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ActivityTestTwoBinding;
import com.kondee.testmodule.fragment.TestDialogFragment;
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

//                binding.imvTest.setImageBitmap(BlurBitmapFactory.getBlurActivity(TestTwoActivity.this));

                AlertDialog.Builder builder = new AlertDialog.Builder(TestTwoActivity.this);
                builder.setView(R.layout.alert_test_blur);
//
                AlertDialog dialog = builder.create();

                Bitmap blurActivity = BlurBitmapFactory.getBlurActivity(TestTwoActivity.this);
////                Bitmap blurActivity = BlurBitmapFactory.fastblur(BlurBitmapFactory.takeScreenShot(TestTwoActivity.this), 7);
                Drawable drawable = new BitmapDrawable(getResources(), blurActivity);
//
                Window window = dialog.getWindow();
                window.setBackgroundDrawable(drawable);

                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.gravity = Gravity.END | Gravity.BOTTOM;

                dialog.show();
            }
        });
    }
}
