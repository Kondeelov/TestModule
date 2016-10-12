package com.kondee.testmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ActivityImageDetailBinding;

public class ImageDetailActivity extends AppCompatActivity {


    private static final String TAG = "Kondee";
    ActivityImageDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_detail);

        initInstance();
    }

    private void initInstance() {
        String imgUrl = getIntent().getStringExtra("imageUrl");

        Glide.with(this)
                .load(imgUrl)
                .centerCrop()
                .into(binding.imvDetail);

        Log.d(TAG, "initInstance: "+getSupportFragmentManager().findFragmentByTag("MainFragmentActivitySecond"));

//        binding.imvDetail.setImageResource(imgUrl);
//        binding.tvName.setText();
//        binding.tvDescription.setText();
    }

}
