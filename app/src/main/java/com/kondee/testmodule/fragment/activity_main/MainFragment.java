package com.kondee.testmodule.fragment.activity_main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.kondee.testmodule.R;
import com.kondee.testmodule.activity.AppLockActivity;
import com.kondee.testmodule.activity.FourthActivity;
import com.kondee.testmodule.activity.ThirdActivity;
import com.kondee.testmodule.applock.AppLock;
import com.kondee.testmodule.databinding.FragmentMainBinding;
import com.kondee.testmodule.view.AppLockView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment {

    private static final String TAG = "Kondee";
    FragmentMainBinding binding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        View rootView = binding.getRoot();

        initInstance();
        return rootView;
    }

    private void initInstance() {
        binding.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppLock.callAppLockActivityTo(getActivity(), FourthActivity.class, "1111", null, R.drawable.padlock);

            }
        });

        binding.etTest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

//        String version = "1.0.1";
//        String compareVersion = "1.0.0";
//        List<String> numberList1 = Arrays.asList(version.split("\\."));
//        List<String> numberList2 = Arrays.asList(compareVersion.split("\\."));
//
//        for (int i = 0; i < numberList1.size(); i++) {
//            if (numberList2.get(i).compareTo(numberList1.get(i)) > 0) {
//                Log.d(TAG, "initInstance: numberList 2 greater than 1");
//            } else if (numberList2.get(i).compareTo(numberList1.get(i)) < 0) {
//                Log.d(TAG, "initInstance: numberList 2 less than 1");
//            } else {
//                Log.d(TAG, "initInstance: numberList 2 equal 1");
//            }
//            Log.d(TAG, "initInstance: Ja tum a rai kor leaw tae mung eieieihi");
//        }

//        List<String> a = new ArrayList<>(5);
//        List<String> b = new ArrayList<>(5);
//
//        a.add("apple");
//        a.add("orange");
//        a.add("pear");
//        a.add("lemon");
//        a.add("mango");
//        a.add("papaya");
//
//        b.add("mango");
//        b.add("pear");
//        b.add("papaya");
//
//        Log.d(TAG, "initInstance: " + a.containsAll(b));
//        Log.d(TAG, "initInstance: " + a.toString());
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
