package com.ayyappan.androidapp.wedlock.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayyappan.androidapp.wedlock.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Ayyappan on 02/11/2015.
 */
public class MiniMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private LatLng mPosFija;

    public MiniMapFragment() {
        super();
    }

    public static MiniMapFragment newInstance(LatLng posicion){
        MiniMapFragment frag = new MiniMapFragment();
        frag.mPosFija = posicion;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
        View v = super.onCreateView(arg0, arg1, arg2);
        getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        UiSettings settings = getMap().getUiSettings();
        settings.setAllGesturesEnabled(false);
        settings.setMyLocationButtonEnabled(false);

        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(mPosFija, 16));
        getMap().addMarker(new MarkerOptions().position(mPosFija).icon(BitmapDescriptorFactory.fromResource(R.drawable.cast_ic_notification_0)));
    }

}
