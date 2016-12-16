package com.kondee.testmodule.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ActivityFourthBinding;
import com.kondee.testmodule.fragment.activity_fourth.LocationFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FourthActivity extends AppCompatActivity /*implements OnMapReadyCallback*/ {

    ActivityFourthBinding binding;
    private LocationFragment fragment;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fourth);

        initInstance();
    }

    private void initInstance() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragment = LocationFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(binding.contentContainer.getId(), fragment, "LocationFragment")
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forth_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
        } else if (item.getItemId() == R.id.menuSearch) {
            if (getSupportFragmentManager().findFragmentById(R.id.contentContainer) == fragment) {
                fragment.startSearch();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
