package com.kondee.testmodule.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ActivityThirdBinding;
import com.kondee.testmodule.fragment.activity_third.MainFragmentActivityThird;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ThirdActivity extends AppCompatActivity {

    ActivityThirdBinding binding;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_third);

        initInstance();
    }

    private void initInstance() {

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        getSupportFragmentManager().beginTransaction()
//                .add(binding.contentContainer.getId(), MainFragmentActivityThird.newInstance(), "MainFragmentActivityThird")
//                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
