package com.kondee.testmodule.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ActivityFourthBinding;
import com.kondee.testmodule.fragment.activity_fourth.LocationFragment;

public class FourthActivity extends AppCompatActivity /*implements OnMapReadyCallback*/ {

    ActivityFourthBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fourth);

        initInstance();
    }

    private void initInstance() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .add(binding.contentContainer.getId(), LocationFragment.newInstance(),"LocationFragment")
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out);
        }
        return super.onOptionsItemSelected(item);
    }
}
