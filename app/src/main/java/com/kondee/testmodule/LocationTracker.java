package com.kondee.testmodule;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.kondee.testmodule.exception.PermissionException;

import java.security.acl.Permission;

public class LocationTracker implements LocationListener {

    private static final long MIN_UPDATE_TIME = 1000 * 10;
    private static final float MIN_UPDATE_DISTANCE = 10;
    private static final String TAG = "Kondee";
    private Activity activity;
    private LocationManager locationManager;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private String providerName;

    public LocationTracker(Activity activity) {
        this.activity = activity;
        try {
            startLocationManager();
        } catch (PermissionException e) {
            e.printStackTrace();
        }
    }

    public void startLocationManager() throws PermissionException {

    }

    @Override
    public void onLocationChanged(Location location) {

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
}
