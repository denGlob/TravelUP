package com.denshiksmile.android.travelup.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.denshiksmile.android.travelup.fragments.MapFragment;
import com.denshiksmile.android.travelup.utils.AuthDataProxyUtil;
import com.denshiksmile.android.travelup.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class UIActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private AuthDataProxyUtil mAuthDataProxyUtil = new AuthDataProxyUtil();

 ///   private SupportMapFragment sMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (mAuthDataProxyUtil.getAuthData() != null) {
            TextView userNameView = (TextView) findViewById(R.id.userNameView);
            TextView userEmailView = (TextView) findViewById(R.id.userEmailView);
            String name = "";
            String email = "";
            name = (String) mAuthDataProxyUtil.getAuthData().getProviderData().get("displayName");
            email = (String) mAuthDataProxyUtil.getAuthData().getProviderData().get("displayEmail");
            if (!email.equals("") && !name.equals("")) {
                userNameView.setText(name);
                userEmailView.setText(email);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static FragmentManager fragmentManager;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        Class fClass = null;

//        if(fragment.isAdded()) {
//            fragmentManager.beginTransaction().hide(sMapFragment).commit();
//        }

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_map:
                fClass = MapFragment.class;
                break;
            case R.id.nav_statistics:
            //    fragmentClass = FlashlightFragment.class;
                break;
            case R.id.nav_send:
            //    fragmentClass = CompassFragment.class;
                break;
            case R.id.nav_share:
            //    fragmentClass = FireFragment.class;
                break;
            default:
         //       fragmentClass = null;
                break;

        }

        try {
            fragment = (Fragment) fClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (!fragment.isAdded())
            fragmentManager.beginTransaction().add(R.id.fragmentContent, fragment).commit();
        else
            fragmentManager.beginTransaction().show(fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        MapFragment mapFragment = new MapFragment();
//        mapFragment.mapOnClick();
    }
}
