package com.denshiksmile.android.travelup.objects;

import com.denshiksmile.android.travelup.objects.interfaces.DataObject;

/**
 * Created by Denys Smile on 5/12/2016.
 */
public class DataCoordinatesObject implements DataObject {

    public static double latitude;
    public static double longtitude;

    public DataCoordinatesObject() {

    }

    public DataCoordinatesObject(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
