package com.kondee.testmodule.model;

import com.google.android.gms.maps.model.LatLng;

public class Location {

    private LatLng location;
    private String markerName;
    private String description;

    public Location(LatLng location, String markerName, String description) {
        this.location = location;
        this.markerName = markerName;
        this.description = description;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getMarkerName() {
        return markerName;
    }

    public String getDescription() {
        return description;
    }
}
