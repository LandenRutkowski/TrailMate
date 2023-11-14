package com.example.trailmate;
public class Coordinate {
    private String title;
    private double latitude;
    private double longitude;
    private String description;

    public Coordinate() {
    }

    public Coordinate(String title, double latitude, double longitude, String description) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return title;
    }
}
