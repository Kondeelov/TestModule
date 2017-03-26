package com.kondee.testmodule.fragment.activity_main;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.kondee.testmodule.LocationTracker;
import com.kondee.testmodule.R;
import com.kondee.testmodule.activity.FourthActivity;
import com.kondee.testmodule.activity.SwipeTestActivity;
import com.kondee.testmodule.applock.AppLock;
import com.kondee.testmodule.databinding.FragmentMainBinding;
import com.kondee.testmodule.exception.PermissionException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        com.google.android.gms.location.LocationListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final long MIN_UPDATE_TIME = 1000 * 10;
    private static final float MIN_UPDATE_DISTANCE = 10;
    private static final String TAG = "Kondee";
    private static final int REQUEST_CODE = 12345;
    FragmentMainBinding binding;
    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain"
    };
    private LocationManager locationManager;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private String providerName = "";
    private double latitude;
    private double longitude;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

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
//        testBus = TestBus.newInstance();
        buildGoogleApiClient();

        binding.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                AppLock.callAppLockActivityTo(getActivity(), FourthActivity.class, "1111", null, R.drawable.padlock);

                Intent intent = new Intent(getActivity(), SwipeTestActivity.class);
                startActivity(intent);

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        binding.etTest.setAdapter(adapter);

        binding.etTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Panel,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            }
                        }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

//        binding.tvTestBadge.setText("1");

//        binding.tvTestBadge.setOnClickListener(v -> {
//            binding.tvTestBadge.setText("123");
//
//        });

//        testBus.getEvent()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        if (o.toString().length() > 0) {
//                            binding.btnGo.setEnabled(true);
//                        } else {
//                            binding.btnGo.setEnabled(false);
//                        }
//                    }
//                });

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

        callLocationTracker();
    }

    private void callLocationTracker() {

        LocationTracker locationTracker = new LocationTracker(getActivity());
        try {
            locationTracker.startLocationManager();
        } catch (PermissionException e) {
            e.printStackTrace();
        }
    }

    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED /*&& ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            }
            return;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
    }

    private void updateGPSCoordinates(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

//            Log.d(TAG, "updateGPSCoordinates: " + latitude + " : " + longitude);

            binding.tvLocation.setText(latitude + " : " + longitude);

            updateGeocoderAddress(location);
        }
    }

    private void updateGeocoderAddress(Location location) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.US);

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            Address now = addresses.get(0);

            binding.tvAddress.setText(now.getLocality() + " " + now.getThoroughfare() + " " + now.getSubThoroughfare() + " " + now.getSubLocality() + " " + now.getAdminArea() + " " + now.getCountryName() + " " + now.getCountryCode());

        } catch (IOException e) {
            e.printStackTrace();
        }
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
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    checkPermission();

                } else {

                }
                break;
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        Log.d(TAG, "onConnected: ");

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(1500);

        checkPermission();

        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location == null) {
            // Blank for a moment...
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            updateGPSCoordinates(location);
        }

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        final PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            public static final int REQUEST_CHECK_SETTINGS = 0x1;

            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
//                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
//                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
//        Log.d(TAG, "onConnectionSuspended: ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.d(TAG, "onConnectionFailed: ");
    }

    @Override
    public void onLocationChanged(Location location) {
//        Log.d(TAG, "onLocationChanged: " + location.toString());
        updateGPSCoordinates(location);
    }
}
