package com.kondee.testmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kondee.testmodule.R;
import com.kondee.testmodule.adapter.viewpager.ViewPagerTransformer;
import com.kondee.testmodule.databinding.ActivityTestBinding;
import com.kondee.testmodule.databinding.ViewPagerItemBinding;
import com.kondee.testmodule.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class TestActivity extends AppCompatActivity {

    ActivityTestBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);

        initInstance();
    }

    private void initInstance() {


        binding.viewPager.setAdapter(new PagerAdapter() {

            ViewPagerItemBinding binding;

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                binding = ViewPagerItemBinding.inflate(LayoutInflater.from(container.getContext()), container, false);
                container.addView(binding.getRoot());

                binding.getRoot().setId(position);

                binding.titleTextView.setText(String.valueOf(position));

                return binding.getRoot();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

        });

        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.setPageMargin(Utils.dp2px(this, 24));
        binding.viewPager.setPageTransformer(false, new ViewPagerTransformer(binding.viewPager));

        binding.viewPager.setCurrentItem(2);
    }
}
