package com.denshiksmile.android.travelup.objects;

import com.denshiksmile.android.travelup.objects.interfaces.DataObject;

/**
 * Created by Denys Smile on 5/13/2016.
 */
public class MapPointObject implements DataObject {
    private DataCoordinatesObject dataCoordinatesObject;
    private DataImageObject dataImageObject;
    private DataTextObject dataTextObject;

    public MapPointObject() {

    }

    public MapPointObject (DataImageObject dataImageObject, DataTextObject dataTextObject) {
        this.dataImageObject = dataImageObject;
        this.dataTextObject = dataTextObject;
    }

    public MapPointObject (DataImageObject dataImageObject, DataTextObject dataTextObject, DataCoordinatesObject dataCoordinatesObject) {
        this.dataImageObject = dataImageObject;
        this.dataTextObject = dataTextObject;
        this.dataCoordinatesObject = dataCoordinatesObject;
    }

    public DataImageObject getDataImageObject() {
        return dataImageObject;
    }

    public void setDataImageObject(DataImageObject dataImageObject) {
        this.dataImageObject = dataImageObject;
    }

    public DataCoordinatesObject getDataCoordinatesObject() {
        return dataCoordinatesObject;
    }

    public void setDataCoordinatesObject(DataCoordinatesObject dataCoordinatesObject) {
        this.dataCoordinatesObject = dataCoordinatesObject;
    }

    public DataTextObject getDataTextObject() {
        return dataTextObject;
    }

    public void setDataTextObject(DataTextObject dataTextObject) {
        this.dataTextObject = dataTextObject;
    }
}
