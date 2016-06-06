package com.denshiksmile.android.travelup.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.denshiksmile.android.travelup.R;
import com.denshiksmile.android.travelup.activities.MapImageDescriptionActivity;
import com.denshiksmile.android.travelup.activities.UIActivity;
import com.denshiksmile.android.travelup.objects.DataCoordinatesObject;
import com.denshiksmile.android.travelup.utils.GPSTrackerUtil;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Denys Smile on 5/11/2016.
 */
public class MapFragment extends Fragment {
    private static LinearLayout view;
    private static GoogleMap mGoogleMap;
    public static double latitude;
    public static double longitude;
    private GPSTrackerUtil gpsTrackerUtil;

//    private Firebase ref = new Firebase(getResources().getString(R.string.firebase_url));

    static FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        fragmentManager = getChildFragmentManager();
        view = (LinearLayout) inflater.inflate(R.layout.map_fragment, container, false);
        onMapReady();
        setUpMapIfNeeded();
        mapOnClick();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mGoogleMap != null) {
            fragmentManager.beginTransaction()
                    .remove(fragmentManager.findFragmentById(R.id.mapFragment)).commit();
            mGoogleMap = null;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mGoogleMap != null)
            setUpMap();

        if (mGoogleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mGoogleMap = ((SupportMapFragment) fragmentManager
                    .findFragmentById(R.id.mapFragment)).getMap(); // getMap is deprecated
            // Check if we were successful in obtaining the map.
            if (mGoogleMap != null)
                setUpMap();
        }
    }

    public void onMapReady() {
        latitude = 0.0;
        longitude = 0.0;
        gpsTrackerUtil = new GPSTrackerUtil(getActivity());

        if (gpsTrackerUtil.canGetLocation()) {
            latitude = gpsTrackerUtil.getLatitude();
            longitude = gpsTrackerUtil.getLongitude();
        } else {
            gpsTrackerUtil.showNetworkSettingsAlert();
        }
    }

    /***** Sets up the map if it is possible to do so *****/
    public static void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mGoogleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mGoogleMap = ((SupportMapFragment) fragmentManager
                    .findFragmentById(R.id.mapFragment)).getMap();
            // Check if we were successful in obtaining the map.
            if (mGoogleMap != null)
                setUpMap();
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the
     * camera.
     * <p>
     * This should only be called once and when we are sure that {@link #mGoogleMap}
     * is not null.
     */
    private static void setUpMap() {
        // For showing a move to my loction button
        if (ActivityCompat.checkSelfPermission(view.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        // For dropping a marker at a point on the Map
        mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("Location")
                .snippet("You're here"));
        // For zooming automatically to the Dropped PIN Location
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
                longitude), 12.0f));
    }

    public void mapOnClick() {
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                DataCoordinatesObject.latitude = latitude;
                DataCoordinatesObject.longtitude = longitude;
                Intent intent = new Intent(getActivity(), MapImageDescriptionActivity.class);
                getActivity().startActivity(intent);
//                if (returnToMap == true) {
//                    Location location = new Location("Something");
//                    location.setLongitude(latLng.longitude);
//                    location.setLatitude(latLng.latitude);
//
//                    LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//                    MarkerOptions markerOptions = new MarkerOptions()
//                            .position(newLatLng)
//                            .title(newLatLng.toString());
//
//                    mGoogleMap.addMarker(markerOptions);
//                }
            }
        });
    }

}
