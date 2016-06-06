package com.denshiksmile.android.travelup.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.denshiksmile.android.travelup.objects.interfaces.DataObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Denys Smile on 5/12/2016.
 */
public class DataImageObject implements DataObject {
    private String imageFile;

    public DataImageObject() {

    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public DataImageObject(String imageFile) {

        this.imageFile = imageFile;
    }

    @Deprecated
    public static String codeStringImage(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        bitmap.recycle();
        byte[] byteArray = outputStream.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return imageFile;
    }

    @Deprecated
    public static Bitmap decodeStringImage(String imageFile) {
        byte[] byteArray = Base64.decode(imageFile, Base64.DEFAULT);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }
}
