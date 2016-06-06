package com.denshiksmile.android.travelup.utils;

import android.graphics.Bitmap;

import com.denshiksmile.android.travelup.objects.DataCoordinatesObject;
import com.denshiksmile.android.travelup.objects.DataImageObject;
import com.denshiksmile.android.travelup.objects.DataTextObject;
import com.denshiksmile.android.travelup.objects.MapPointObject;
import com.google.android.gms.fitness.data.DataPoint;

/**
 * Created by Denys Smile on 5/14/2016.
 */
public class MapPointFactoryUtil {
    private String nameOfPlace;
    private String descriptionOfPlace;
    private double latitude;
    private double longitude;
    private Bitmap bitmap;
    private MapPointObject pointObject;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public MapPointFactoryUtil(String nameOfPlace, String descriptionOfPlace, double latitude, double longitude) {
        this.nameOfPlace = nameOfPlace;
        this.descriptionOfPlace = descriptionOfPlace;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNameOfPlace() {
        return nameOfPlace;
    }

    public void setNameOfPlace(String nameOfPlace) {
        this.nameOfPlace = nameOfPlace;
    }

    public String getDescriptionOfPlace() {
        return descriptionOfPlace;
    }

    public void setDescriptionOfPlace(String descriptionOfPlace) {
        this.descriptionOfPlace = descriptionOfPlace;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public DataTextObject getDataTextObject(){
        DataTextObject dataTextObject = new DataTextObject(nameOfPlace, descriptionOfPlace);
        return dataTextObject;
    }

    public DataImageObject getDataImageObject() {
        String imageFile = DataImageObject.codeStringImage(bitmap);
        DataImageObject dataImageObject = new DataImageObject(imageFile);
        return dataImageObject;
    }

    public DataCoordinatesObject getDataCoordinatesObject () {
        DataCoordinatesObject dataCoordinatesObject = new DataCoordinatesObject(latitude, longitude);
        return dataCoordinatesObject;
    }

    public void setMapPointObject(DataImageObject imageObject, DataTextObject textObject, DataCoordinatesObject coordinatesObject) {
        pointObject = new MapPointObject(imageObject, textObject, coordinatesObject);
    }

    public MapPointObject getMapPointObject() {
        return pointObject;
    }
}
