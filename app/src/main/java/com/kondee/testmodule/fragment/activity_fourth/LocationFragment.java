package com.kondee.testmodule.fragment.activity_fourth;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.FragmentLocationBinding;
import com.kondee.testmodule.model.Location;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

public class LocationFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "Kondee";
    private static final int FINE_LOCATION_REQUEST_CODE = 11245;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 11110;
    FragmentLocationBinding binding;
    private GoogleMap gMap;

    public static LocationFragment newInstance() {
        return new LocationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location, container, false);
        View rootView = binding.getRoot();

        initInstance(savedInstanceState);

        return rootView;
    }

    private void initInstance(Bundle savedInstanceState) {

        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.onResume();

        binding.mapView.getMapAsync(this);

//        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.contentContainer);
        startSearch();
    }

    public void startSearch() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        gMap = googleMap;

//        LatLng location = new LatLng(-34, 151);
        List<Location> locations = new ArrayList<>();

        locations.add(new Location(new LatLng(-33.8688, 151.2093), "Sydney", "The most populous city in Australia."));
        locations.add(new Location(new LatLng(13.7563, 100.5018), "Bangkok", "The most populous city in Thailand."));


        for (Location location : locations) {
            addMarker(googleMap, location.getLocation(), location.getMarkerName(), location.getDescription());
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations.get(1).getLocation(), 6));

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16));
            }
        });

        googleMap.getUiSettings().setMapToolbarEnabled(false);

        getFineLocation(googleMap);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Place place = PlaceAutocomplete.getPlace(getActivity(), data);
            Log.d(TAG, "onActivityResult: " + place.getLatLng().toString());

            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),19));
        }
    }

    private void getFineLocation(final GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUEST_CODE);

            return;
        } else {
            gMap.setMyLocationEnabled(true);

            gMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    Log.d(TAG, "zoom: " + googleMap.getCameraPosition().zoom);
                    Log.d(TAG, "location: " + googleMap.getCameraPosition().target);

                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);


                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        // Go to Location Settings
//                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                        displayLocationSettingRequest(getActivity());
                    }

                    // Creating a criteria object to retrieve provider

                    Criteria criteria = new Criteria();

                    // Getting the name of the best provider
                    String provider = locationManager.getBestProvider(criteria, true);

                    android.location.Location location = locationManager.getLastKnownLocation(provider);

                    Log.d(TAG, "onMyLocationButtonClick: " + (googleMap.getCameraPosition().zoom != 16));
                    if (googleMap.getCameraPosition().zoom != 16)
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));

                    if (location != null) {
                        // Getting latitude of the current location
                        double latitude = location.getLatitude();

                        // Getting longitude of the current location
                        double longitude = location.getLongitude();

                        // Creating a LatLng object for the current location
                        LatLng now = new LatLng(latitude, longitude);

                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(now));

                    }

                    return false;
                }
            });
        }
    }

    private void displayLocationSettingRequest(Context context) {

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);

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
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    private void addMarker(GoogleMap googleMap, LatLng position, String name, String description) {
        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                .position(position)
                .title(name)
                .snippet(description));
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case FINE_LOCATION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    gMap.setMyLocationEnabled(true);

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
