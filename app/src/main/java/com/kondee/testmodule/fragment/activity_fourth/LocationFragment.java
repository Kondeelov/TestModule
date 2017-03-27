package com.kondee.testmodule.fragment.activity_fourth;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;
import com.kondee.testmodule.R;
import com.kondee.testmodule.activity.FourthActivity;
import com.kondee.testmodule.activity.MainActivity;
import com.kondee.testmodule.activity.SecondActivity;
import com.kondee.testmodule.databinding.FragmentLocationBinding;

import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

public class LocationFragment extends Fragment implements OnMapReadyCallback,
        LocationListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "Kondee";
    private static final int FINE_LOCATION_REQUEST_CODE = 11245;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 11110;
    FragmentLocationBinding binding;
    private GoogleMap gMap;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Marker marker;
    private CircleOptions circleOptions;
    private LatLngBounds latLngBounds = new LatLngBounds(new LatLng(0, 0), new LatLng(0, 0));

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

        binding.btnGo.setOnClickListener(onBtnGoClickListener);

        startSearch();
    }

    public void startSearch() {
        try {
            AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setCountry("TH").build();
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(autocompleteFilter).build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        gMap = googleMap;

//      Bangkok LatLng
        LatLng location = new LatLng(13.7563, 100.5018);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));

//        LatLngBounds bangkok = toBounds(location, 200);

//        googleMap.setOnCameraMoveListener(() -> {
//            Log.d(TAG, "onMapReady: " + bangkok.contains(googleMap.getCameraPosition().target));
//        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16));
            }
        });

        googleMap.getUiSettings().setMapToolbarEnabled(false);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location)
                .title("1");

        gMap.addMarker(markerOptions);

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.hideInfoWindow();
                marker.getTitle();

                return false;
            }
        });

        gMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {
                gMap.clear();
                gMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                circleOptions = new CircleOptions()
                        .center(marker.getPosition())
                        .strokeColor(Color.argb(50, 70, 70, 70))
                        .fillColor(Color.argb(100, 150, 150, 150))
                        .radius(50);
                gMap.addCircle(circleOptions);
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });

        getFineLocation(googleMap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Place place = PlaceAutocomplete.getPlace(getActivity(), data);
//            Log.d(TAG, "onActivityResult: " + place.getLatLng().toString());

            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 19));

            marker = gMap.addMarker(new MarkerOptions().draggable(true).position(place.getLatLng()));
            circleOptions = new CircleOptions()
                    .center(place.getLatLng())
                    .strokeColor(Color.argb(50, 70, 70, 70))
                    .fillColor(Color.argb(100, 150, 150, 150))
                    .radius(50);
            gMap.addCircle(circleOptions);

            latLngBounds = toBounds(marker.getPosition(), 500);

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

                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);


                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        // Go to Location Settings
//                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                        displayLocationSettingRequest(getActivity());
                    }

                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (lastKnownLocation != null) {
                        if (latLngBounds.contains(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()))) {
                            Log.d(TAG, "onLocationChanged: Fuck Yeah!!!");
                            Toast.makeText(getActivity(), "Yeah!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    return false;
                }
            });
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

    private void displayLocationSettingRequest(Context context) {

        buildGoogleApiClient();

    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
        if (googleApiClient != null)
            if (!googleApiClient.isConnecting() || !googleApiClient.isConnected()) {
                googleApiClient.connect();
            }
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(getActivity(), "onConnected", Toast.LENGTH_SHORT).show();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(1500);

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

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

//

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getActivity(), "onConnectionSuspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "onConnectionFailed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        if (location != null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            LatLng now = new LatLng(latitude, longitude);

            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(now, 16));

//            LatLngBounds latLngBounds = LatLngBounds.createFromAttributes(getActivity(), null);

            Log.d(TAG, "onLocationChanged: " + now.longitude);

            if (latLngBounds.contains(now)) {
                Log.d(TAG, "onLocationChanged: Fuck Yeah!!!");
                Toast.makeText(getActivity(), "Yeah!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public LatLngBounds toBounds(LatLng center, double radius) {
        LatLng southwest = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 225);
        LatLng northeast = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 45);
        return new LatLngBounds(southwest, northeast);
    }

    /************
     * Listener
     ************/

    View.OnClickListener onBtnGoClickListener = v -> {

//            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
//
//            Intent intent = new Intent(getActivity(), FourthActivity.class);
//
//            PendingIntent pendingIntent = PendingIntent.getActivities(getActivity(), (int) System.currentTimeMillis(), new Intent[]{intent}, 0);
//
//            builder.setSmallIcon(R.drawable.star)
//                    .setContentTitle("Test Notification...Test Notification...Test Notification...Test Notification...")
//                    .setContentText("")
//                    .addAction(R.drawable.ic_media_play, "Play", pendingIntent)
//                    .setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle().bigText("Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n" +
//                            "Test Test Test Test Test Test Test Test Test \n"))
//                    .setAutoCancel(true);
//
//            Intent resultIntent = new Intent(getActivity(), FourthActivity.class);
//
//            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
//            stackBuilder.addParentStack(FourthActivity.class);
//            stackBuilder.addNextIntent(resultIntent);
//
//            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setContentIntent(resultPendingIntent);
//
//            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.notify(1, builder.build());

        RemoteViews remoteViews = new RemoteViews(getActivity().getPackageName(), R.layout.test_noti);
        remoteViews.setTextViewText(R.id.title, "Hello");
        remoteViews.setTextViewText(R.id.text, "World!!!");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        builder.setAutoCancel(true)
//                    .setSmallIcon(R.drawable.star)
//                    .setContent(remoteViews);
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .setContentTitle("Set your title")
                .setContentText("Pull to view image!")
                .addAction(android.R.drawable.ic_media_play, "play", null);

        Bitmap bitmap_image = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.bg_nav_header);
        NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(bitmap_image);
        s.setSummaryText("HaHaHa I got it!");
        builder.setStyle(s);

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

    };

}


