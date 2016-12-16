package com.kondee.testmodule.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.kondee.testmodule.R;
import com.kondee.testmodule.adapter.MyRecyclerAdapter;
import com.kondee.testmodule.fragment.ImageDetailFragment;
import com.kondee.testmodule.fragment.activity_second.MainFragmentActivitySecond;
import com.kondee.testmodule.databinding.ActivitySecondBinding;
import com.kondee.testmodule.model.Modeller;
import com.kondee.testmodule.transition.DetailsTransition;
import com.kondee.testmodule.utils.Utils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "Kondee";
    ActivitySecondBinding binding;

    MainFragmentActivitySecond mainFragmentActivitySecond = MainFragmentActivitySecond.newInstance();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_second);

        initInstance();
    }

    private void initInstance() {

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentContainer, mainFragmentActivitySecond, "MainFragmentActivitySecond")
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
