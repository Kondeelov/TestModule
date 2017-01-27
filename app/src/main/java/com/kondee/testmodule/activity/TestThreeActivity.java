package com.kondee.testmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kondee.testmodule.R;
import com.kondee.testmodule.adapter.TestThreeAdapter;
import com.kondee.testmodule.databinding.ActivityTestThreeBinding;
import com.kondee.testmodule.listener.HideSoftInputOnFocusChangeListener;

import java.util.ArrayList;
import java.util.List;

public class TestThreeActivity extends AppCompatActivity {

    private static final String TAG = "Kondee";
    ActivityTestThreeBinding binding;
    private List<String> numberList = new ArrayList<String>();
    private boolean isSet;
    private boolean isSeries;
    private TestThreeAdapter adapter;

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

//        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new TestThreeAdapter(numberList);
        binding.recyclerView.setAdapter(adapter);

        binding.btnPurchase.setEnabled(false);

        binding.etNumber.setOnFocusChangeListener(new HideSoftInputOnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                super.onFocusChange(v, hasFocus);

                if (!hasFocus) {
                    addNumber();
                }
            }
        });

        binding.etNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    binding.etNumber.clearFocus();

                    return true;
                }
                return false;
            }
        });

        binding.lnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSet = !isSet;

                toggleSet();

            }
        });

        binding.lnSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSeries = !isSeries;

                toggleSeries();


            }
        });

    }

    private void addNumber() {

        String number = binding.etNumber.getText().toString();
        if (!number.equals("") && number.length() >= 2) {
            numberList.add(0, number);

            if (numberList.size() == 1)
                adapter.notifyItemChanged(0);
            else
                adapter.notifyItemInserted(0);
        } else {
            binding.etNumber.setText("");
        }
    }

    private void toggleSet() {
        if (isSet) {
            binding.lnSet.setBackground(ContextCompat.getDrawable(TestThreeActivity.this, R.drawable.bg_select));

            String frontString = binding.etNumber.getText().toString().substring(0, binding.etNumber.length() - 2);
            String baseString = binding.etNumber.getText().toString().substring(binding.etNumber.length() - 2);

            String showString;

            int index = 1;

            for (int i = 0; i <= 2; i++) {
                int baseNumber = Integer.parseInt(baseString) % 40 + (40 * i);
                if (String.valueOf(baseNumber).length() == 1) {
                    showString = frontString + "0" + String.valueOf(baseNumber);
                } else {
                    showString = frontString + String.valueOf(baseNumber);
                }

                if (showString.equals(binding.etNumber.getText().toString()) || baseNumber >= 100) {
                    continue;
                }

                numberList.add(index, showString);

                adapter.notifyItemInserted(index);
//                adapter.notifyDataSetChanged();

                index++;
            }

        } else {

            String baseString = binding.etNumber.getText().toString().substring(binding.etNumber.length() - 2);

            int index = 0;


            if (Integer.parseInt(baseString) % 40 + (40 * 2) >= 100) {
                index = 1;
            } else {
                index = 2;
            }

            for (int i = 1; i <= index; i++) {
                numberList.remove(1);
//                adapter.notifyItemRemoved(i);
            }

//            numberList.remove();
            adapter.notifyItemRangeRemoved(1, index);

            binding.lnSet.setBackground(ContextCompat.getDrawable(TestThreeActivity.this, R.drawable.bg_unselect));
        }

        Log.d(TAG, "toggleSet: " + numberList.size());
    }

    private void toggleSeries() {
        if (isSeries) {
            binding.lnSeries.setBackground(ContextCompat.getDrawable(TestThreeActivity.this, R.drawable.bg_select));
        } else {
            binding.lnSeries.setBackground(ContextCompat.getDrawable(TestThreeActivity.this, R.drawable.bg_unselect));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
