package com.kondee.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kondee on 6/28/2017.
 */

public class CourseModel {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("url")
    @Expose
    public Url url = new Url();
    @SerializedName("timestamp")
    @Expose
    public String timestamp;

    public String getName() {
        return name;
    }

    public CourseModel setName(String name) {
        this.name = name;
        return this;
    }

    public Url getUrl() {
        return url;
    }

    public CourseModel setUrl(Url url) {
        this.url = url;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public CourseModel setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
