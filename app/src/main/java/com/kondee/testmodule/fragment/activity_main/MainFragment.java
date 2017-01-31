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
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
