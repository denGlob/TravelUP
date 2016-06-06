package com.denshiksmile.android.travelup.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.denshiksmile.android.travelup.objects.DataImageObject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.UploadTask;

import java.io.File;

/**
 * Created by Denys Smile on 5/21/2016.
 */
public class ImageTransactionUtil {

    private DataImageObject dataImageObject;
    private Uri imageFile;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    public ImageTransactionUtil(DataImageObject dataImageObject, Uri imageFile) {
        this.dataImageObject = dataImageObject;
        this.imageFile = imageFile;
    }

    public ImageTransactionUtil(DataImageObject dataImageObject) {
        this.dataImageObject = dataImageObject;
    }

    public ImageTransactionUtil() {
    }

    public DataImageObject getDataImageObject() {
        return dataImageObject;
    }

    public void setDataImageObject(DataImageObject dataImageObject) {
        this.dataImageObject = dataImageObject;
    }

    public Uri getImageFile() {
        return imageFile;
    }

    public void setImageFile(Uri imageFile) {
        this.imageFile = imageFile;
    }

    public void uploadImageFile() {

// Create the file metadata
        metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

// Upload file and metadata to the path 'images/mountains.jpg'
        uploadTask = storageRef.child("images/"+file.getLastPathSegment()).putFile(file, metadata);

// Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Throwable throwable) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete
                Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
            }
        });
    }
}
