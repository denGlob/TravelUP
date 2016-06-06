package com.denshiksmile.android.travelup.objects;

import com.denshiksmile.android.travelup.objects.interfaces.DataObject;

/**
 * Created by Denys Smile on 5/12/2016.
 */
public class DataTextObject implements DataObject{
    private String description;
    private String name;

    public DataTextObject() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataTextObject(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

