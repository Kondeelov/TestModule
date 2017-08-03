package com.kondee.testmodule.fragment.activity_main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.FragmentFourthBinding;
import com.kondee.testmodule.databinding.FragmentThirdBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class FourthFragment extends Fragment {

    private static final String TAG = "Kondee";
    FragmentFourthBinding binding;
    //    String[] test = {"Hello", "It's", "Me", "I", "Forgot", "That", "Can", "Heal", "You", "And", "Must", "Be"};
    private ArrayAdapter adapter;

    public static FourthFragment newInstance() {
        return new FourthFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fourth, container, false);
        View rootView = binding.getRoot();
        initInstance(rootView);

        return rootView;
    }

    private void initInstance(View rootView) {

    }

}
