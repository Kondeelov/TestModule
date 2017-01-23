package com.kondee.testmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kondee.testmodule.R;
import com.kondee.testmodule.adapter.TestThreeAdapter;
import com.kondee.testmodule.databinding.ActivityTestThreeBinding;
import com.kondee.testmodule.listener.HideSoftInputOnFocusChangeListener;

import java.util.ArrayList;
import java.util.List;

public class TestThreeActivity extends AppCompatActivity {

    private static final String TAG = "Kondee";
    ActivityTestThreeBinding binding;
    private List<Integer> numberList = new ArrayList<Integer>();
    private boolean isSet;
    private boolean isSeries;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_three);

        initInstance();
    }

    private void initInstance() {

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.etNumber.setOnFocusChangeListener(new HideSoftInputOnFocusChangeListener());

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final TestThreeAdapter adapter = new TestThreeAdapter(numberList);
        binding.recyclerView.setAdapter(adapter);

        binding.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(binding.etNumber.getText().toString());
                numberList.add(number);

                if (numberList.size() == 1)
                    adapter.notifyItemChanged(0);
                else
                    adapter.notifyItemInserted((numberList.size() - 1));
            }
        });

        binding.lnSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSeries = !isSeries;

                toggleSeries();


            }
        });

        binding.lnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSet = !isSet;

                toggleSet();

            }
        });

//        binding.btnOK.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                int number = Integer.parseInt(binding.etNumber.getText().toString());
//                numberList.add(0, number);
//
//
//                adapter.notifyItemInserted(0);
//                adapter.notifyItemRangeChanged(0, adapter.getItemCount());
//
//                return true;
//            }
//        });


    }

    private void toggleSet() {
        if (isSet)
            binding.lnSet.setBackground(ContextCompat.getDrawable(TestThreeActivity.this, R.drawable.bg_select));
        else
            binding.lnSet.setBackground(ContextCompat.getDrawable(TestThreeActivity.this, R.drawable.bg_unselect));
    }

    private void toggleSeries() {
        if (isSeries)
            binding.lnSeries.setBackground(ContextCompat.getDrawable(TestThreeActivity.this, R.drawable.bg_select));
        else
            binding.lnSeries.setBackground(ContextCompat.getDrawable(TestThreeActivity.this, R.drawable.bg_unselect));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
