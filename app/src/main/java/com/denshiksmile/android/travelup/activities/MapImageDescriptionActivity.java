package com.denshiksmile.android.travelup.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.denshiksmile.android.travelup.R;
import com.denshiksmile.android.travelup.connectors.UserConnector;
import com.denshiksmile.android.travelup.fragments.MapFragment;
import com.denshiksmile.android.travelup.objects.DataCoordinatesObject;
import com.denshiksmile.android.travelup.objects.DataImageObject;
import com.denshiksmile.android.travelup.objects.DataTextObject;
import com.denshiksmile.android.travelup.objects.MapPointObject;
import com.denshiksmile.android.travelup.utils.MapPointFactoryUtil;
import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MapImageDescriptionActivity extends Activity {

	int REQUEST_CAMERA = 0, SELECT_FILE = 1;
	Button btnSelect;
	Button btnAccept;
	ImageView ivImage;

	EditText txtNameOfPlace;
	EditText txtDescriptionOfPlace;

	String nameOfPlace = "";
	String desOfPlace = "";

	MapPointFactoryUtil pointFactoryUtil;
	MapPointObject pointObject;
	UserConnector connector;

//	Firebase ref = new Firebase(getResources().getString(R.string.firebase_url));
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_set_image_description);
		btnSelect = (Button) findViewById(R.id.btnSelectPhoto);
		btnSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectImage();
			}
		});

		btnAccept = (Button) findViewById(R.id.btnAcceptPlace);
		ivImage = (ImageView) findViewById(R.id.ivImage);

		txtNameOfPlace = (EditText) findViewById(R.id.placeName);
		nameOfPlace = txtNameOfPlace.getText().toString();

		txtDescriptionOfPlace = (EditText) findViewById(R.id.descriptionOfThePlace);
		desOfPlace = txtDescriptionOfPlace.getText().toString();

		btnAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					nameOfPlace = txtNameOfPlace.getText().toString();
					desOfPlace = txtDescriptionOfPlace.getText().toString();
					pointFactoryUtil = new MapPointFactoryUtil(nameOfPlace, desOfPlace, DataCoordinatesObject.latitude, DataCoordinatesObject.longtitude);
//					if (bm != null) {
//						pointFactoryUtil.setBitmap(bm);
//					} else if (thumbnail != null) {
//						pointFactoryUtil.setBitmap(thumbnail);
//					} else
//						pointFactoryUtil.setBitmap(null);
					DataImageObject imageObject = pointFactoryUtil.getDataImageObject();
					DataTextObject textObject = pointFactoryUtil.getDataTextObject();
					DataCoordinatesObject coordinatesObject = pointFactoryUtil.getDataCoordinatesObject();
					pointObject = new MapPointObject(imageObject, textObject, coordinatesObject);
					connector = new UserConnector(pointObject);
					connector.setData();
					Intent ttsSettings = new Intent("com.denshiksmile.android.travelup.fragments.UIActivity");
					ttsSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(ttsSettings);
				} catch (Exception e) {
					Toast.makeText(MapImageDescriptionActivity.this, "Please, fill the fields", Toast.LENGTH_SHORT);
				}

			}
		});
	}

	private void selectImage() {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(MapImageDescriptionActivity.this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent, REQUEST_CAMERA);
				} else if (items[item].equals("Choose from Library")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(
							Intent.createChooser(intent, "Select File"),
							SELECT_FILE);
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == SELECT_FILE)
				onSelectFromGalleryResult(data);
			else if (requestCode == REQUEST_CAMERA)
				onCaptureImageResult(data);
		}
	}

	Uri mImageFile;

	Bitmap thumbnail;
	private void onCaptureImageResult(Intent data) {
		thumbnail = (Bitmap) data.getExtras().get("data");
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

		File destination = new File(Environment.getExternalStorageDirectory(),
				System.currentTimeMillis() + ".jpg");

		FileOutputStream fo;
		try {
			destination.createNewFile();
			mImageFile = Uri.fromFile(destination);
			fo = new FileOutputStream(destination);
			fo.write(bytes.toByteArray());
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ivImage.setImageBitmap(thumbnail);
	}

	@SuppressWarnings("deprecation")
	Bitmap bm;
	private void onSelectFromGalleryResult(Intent data) {
		Uri selectedImageUri = data.getData();
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
				null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();

		String selectedImagePath = cursor.getString(column_index);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(selectedImagePath, options);
		final int REQUIRED_SIZE = 200;
		int scale = 1;
		while (options.outWidth / scale / 2 >= REQUIRED_SIZE
				&& options.outHeight / scale / 2 >= REQUIRED_SIZE)
			scale *= 2;
		options.inSampleSize = scale;
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(selectedImagePath, options);

		mImageFile = Uri.fromFile(new File(selectedImagePath));

		ivImage.setImageBitmap(bm);
	}

}
