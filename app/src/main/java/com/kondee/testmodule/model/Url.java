package com.kondee.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kondee on 6/28/2017.
 */

public class Url {
    @SerializedName("small")
    @Expose
    public String small;
    @SerializedName("medium")
    @Expose
    public String medium;
    @SerializedName("large")
    @Expose
    public String large;

    public String getSmall() {
        return small;
    }

    public Url setSmall(String small) {
        this.small = small;
        return this;
    }

    public String getMedium() {
        return medium;
    }

    public Url setMedium(String medium) {
        this.medium = medium;
        return this;
    }

    public String getLarge() {
        return large;
    }

    public Url setLarge(String large) {
        this.large = large;
        return this;
    }
}
