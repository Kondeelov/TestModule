package com.kondee.testmodule.fragment.activity_main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.google.android.gms.location.LocationServices;
import com.kondee.testmodule.R;
import com.kondee.testmodule.activity.AppLockActivity;
import com.kondee.testmodule.activity.FourthActivity;
import com.kondee.testmodule.activity.ThirdActivity;
import com.kondee.testmodule.applock.AppLock;
import com.kondee.testmodule.databinding.FragmentMainBinding;
import com.kondee.testmodule.eventbus.TestBus;
import com.kondee.testmodule.view.AppLockView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment {

    private static final String TAG = "Kondee";
    private static final int REQUEST_CODE = 12345;
    FragmentMainBinding binding;
    private TestBus testBus;
    private LocationManager locationManager;

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
        testBus = TestBus.newInstance();

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

        binding.etTest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                testBus.setString(s);
            }
        });
        binding.etTest2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                testBus.setString(s);
            }
        });
        binding.etTest3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                testBus.setString(s);
            }
        });

        testBus.getEvent()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (o.toString().length() > 0) {
                            binding.btnGo.setEnabled(true);
                        } else {
                            binding.btnGo.setEnabled(false);
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

//        Log.d(TAG, "initInstance: " + (1 << 0));

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        getLatLng();
    }

    private void getLatLng() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            }

            return;
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.d(TAG, "onLocationChanged: " + location.getLatitude());
                binding.etTest.setText(String.valueOf(location.getLatitude()));
                binding.etTest2.setText(String.valueOf(location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getLatLng();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    getLatLng();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
