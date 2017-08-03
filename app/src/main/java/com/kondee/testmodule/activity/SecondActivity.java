package com.kondee.testmodule.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kondee.testmodule.R;
import com.kondee.testmodule.fragment.activity_second.MainFragmentActivitySecond;
import com.kondee.testmodule.databinding.ActivitySecondBinding;

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
