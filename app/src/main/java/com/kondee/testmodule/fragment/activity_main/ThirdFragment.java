package com.kondee.testmodule.fragment.activity_main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.FragmentThirdBinding;
import com.kondee.testmodule.databinding.ItemTestThreeListBinding;
import com.kondee.testmodule.databinding.ListTestItemBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

public class ThirdFragment extends Fragment {

    private static final String TAG = "Kondee";
    FragmentThirdBinding binding;
    String[] test = {"Hello", "It's", "Me", "I", "Forgot", "That", "Can", "Heal", "You", "And", "Must", "Be"};
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

//        Arrays.sort(test);

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, getNameList());

        binding.listView.setAdapter(adapter);



        Observable<String> observable1 = Observable.fromIterable(getNameList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<String> observable2 = Observable.fromIterable(getVersionList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Integer> observable = Observable.fromIterable(getIntList())
                .zipWith(Observable.fromIterable(getReverseIntList()).subscribeOn(Schedulers.io()), new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observer observer = new Observer<Integer>() {

            @Override
            public void onError(Throwable e) {
//                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
//                Log.d(TAG, "onComplete: ");
            }

            @Override
            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer i) {
//                Log.d(TAG, "onNext: " + i);
            }
        };

        observable.subscribe(observer);
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
                "Nougat",
                "O-MG"));
        return nameList;
    }

    public List<String> getVersionList() {
        List<String> nameList = new ArrayList<>(Arrays.asList(
                "1.0",
                "1.6",
                "2.1",
                "2.1",
                "2.2",
                "2.3",
                "4.2",
                "4.3",
                "4.4",
                "5",
                "6",
                "7"));
        return nameList;
    }

    public List<Integer> getIntList() {
        List<Integer> nameList = new ArrayList<>(Arrays.asList(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10,
                11,
                12));
        return nameList;
    }

    public List<Integer> getReverseIntList() {
        List<Integer> nameList = new ArrayList<>(Arrays.asList(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10,
                11,
                12,
                13));
        Collections.reverse(nameList);
        return nameList;
    }

}
