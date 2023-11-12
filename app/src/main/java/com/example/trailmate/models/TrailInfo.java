package com.example.trailmate.models;

import android.graphics.Bitmap;

import java.lang.reflect.Array;

public class TrailInfo {

    private String trailName;
    private String trailLength;
    private String OperatingTimes;

    private int[] location;

    private Bitmap[] photos;

    public TrailInfo() {

    }

    public String getTrailName() {
        return trailName;
    }
    public String getTrailLength() {
        return trailLength;
    }
    public String getOperatingTimes() {
        return OperatingTimes;
    }
    public int[] getLocation() {
        return location;
    }
    public Bitmap[] getPhotos() {
        return photos;
    }

    public void setTrailName(String trailName) {
        this.trailName = trailName;
    }

    public void setTrailLength(String trailLength) {
        this.trailLength = trailLength;
    }
}
