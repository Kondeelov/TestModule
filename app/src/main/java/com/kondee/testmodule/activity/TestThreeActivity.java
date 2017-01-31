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
import android.widget.TextView;

import com.kondee.testmodule.R;
import com.kondee.testmodule.adapter.TestThreeAdapter;
import com.kondee.testmodule.databinding.ActivityTestThreeBinding;
import com.kondee.testmodule.listener.HideSoftInputOnFocusChangeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestThreeActivity extends AppCompatActivity {

    private static final String TAG = "Kondee";
    ActivityTestThreeBinding binding;
    private List<String> numberList = new ArrayList<>();
    private boolean isSet;
    private boolean isSeries;
    private TestThreeAdapter adapter;
    private List<String> seriesList = new ArrayList<>();
    private List<String> setList = new ArrayList<>();
    private int seriesAmount;
    private ArrayList<String> oldNumberList= new ArrayList<>();

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

                setList.add(showString);
            }

            numberList.addAll(1, setList);

            adapter.notifyItemRangeInserted(index, setList.size());

            sort();

        } else {

            List<Integer> removeSetList = removeAll(numberList, setList);

            for (int i = removeSetList.size() - 1; i >= 0; i--) {
                adapter.notifyItemRemoved(removeSetList.get(i));
            }

            setList.clear();

            binding.lnSet.setBackground(ContextCompat.getDrawable(TestThreeActivity.this, R.drawable.bg_unselect));
        }

    }

    private void toggleSeries() {
        String getString = binding.etNumber.getText().toString();

        if (isSeries) {
            binding.lnSeries.setBackground(ContextCompat.getDrawable(TestThreeActivity.this, R.drawable.bg_select));

            String seriesString = getString;

            for (int i = getString.length() - 1; i >= 2; i--) {

                seriesString = seriesString.substring(1);
                seriesList.add(seriesString);

                seriesAmount++;
            }

            numberList.addAll(1, seriesList);

            adapter.notifyItemRangeInserted(1, seriesList.size());

            sort();

        } else {

            List<Integer> removeSeriesList = removeAll(numberList, seriesList);

            for (int i = removeSeriesList.size() - 1; i >= 0; i--) {
                adapter.notifyItemRemoved(removeSeriesList.get(i));
            }

//            adapter.notifyItemRangeRemoved(1, seriesList.size());
//            adapter.notifyItemRangeChanged(0, numberList.size(),numberList);

            seriesList.clear();

            binding.lnSeries.setBackground(ContextCompat.getDrawable(TestThreeActivity.this, R.drawable.bg_unselect));
        }
    }

    private void sort() {
        oldNumberList = (ArrayList<String>) numberList;

        Collections.sort(numberList);

        for (int i = 0; i < numberList.size(); i++) {
            Log.d(TAG, "sort: " + oldNumberList.get(i) + "  " + numberList.get(i));
//            adapter.notifyItemMoved(i, oldNumberList.indexOf(numberList.get(i)));
        }


        if (numberList.indexOf(binding.etNumber.getText().toString()) == 1) {
            Collections.swap(numberList, 0, 1);
            adapter.notifyItemMoved(0, 1);
        }

        adapter.notifyItemRangeChanged(0, numberList.size(), numberList);
    }

    public List<Integer> removeAll(List<?> baseCollection, List<?> removeListCollection) {
        List<Integer> integerList = new ArrayList<>();

        for (int i = 0; i < baseCollection.size(); i++) {

            if (removeListCollection.contains(baseCollection.get(i))) {

                integerList.add(i);
            }
        }

        for (int j = integerList.size() - 1; j >= 0; j--) {

            baseCollection.remove((int) (integerList.get(j)));
        }

        return integerList;
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