package com.kondee.testmodule.fragment.activity_main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.FragmentThirdBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ThirdFragment extends Fragment {

    private static final String TAG = "Kondee";
    FragmentThirdBinding binding;
    String[] test = {"Hello","It's","Me","I","Forgot","That","Can","Heal","You","And","Must","Be"};
    private ArrayAdapter adapter;

    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_third, container, false);
        View rootView = binding.getRoot();
        initInstance(rootView);

        return rootView;
    }

    private void initInstance(View rootView) {

        Arrays.sort(test);

        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,test);

        binding.listView.setAdapter(adapter);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        Observable<List<String>> observable = Observable.fromCallable(new Callable<List<String>>() {
//            @Override
//            public List<String> call() throws Exception {
//                return getNameList();
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//
//        Observer observer = new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                Log.d(TAG, "onCompleted: ");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError: " + e);
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.d(TAG, "onNext: CodeName " + s);
//            }
//        };
//
//        observable.subscribe(observer);
    }

    public List<String> getNameList() {
        List<String> nameList = new ArrayList<>(Arrays.asList(
                "Cupcake",
                "Donut",
                "Eclair",
                "Froyo",
                "Gingerbread",
                "Honeycomb",
                "Ice Cream Sandwich",
                "Jelly Bean",
                "Kitkat",
                "Lollipop",
                "Marshmallow",
                "Nougat"));
        return nameList;
    }
}
