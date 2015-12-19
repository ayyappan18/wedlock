package com.ayyappan.androidapp.wedlock.venue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayyappan.androidapp.wedlock.AboutActivity;
import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.TestActivity;
import com.ayyappan.androidapp.wedlock.menudrawer.MenuDrawerActivity;
import com.ayyappan.androidapp.wedlock.venue.bean.Venue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Ayyappan on 03/11/2015.
 */
public class VenueFragment extends Fragment {

    private static View venueFragmentView;

    private static GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        venueFragmentView = (RelativeLayout) inflater.inflate(R.layout.fragment_venue_details, container, false);

        Venue venue = savedInstanceState.getParcelable("venue");

        TextView textVenueTitle = (TextView) venueFragmentView.findViewById(R.id.VenueTitle);
        TextView textVenueHall = (TextView) venueFragmentView.findViewById(R.id.VenueHall);
        TextView textVenueAddressLine1 = (TextView) venueFragmentView.findViewById(R.id.VenueAddress1);
        TextView textVenueAddressLine2 = (TextView) venueFragmentView.findViewById(R.id.VenueAddress2);
        TextView textVenueAddressLine3 = (TextView) venueFragmentView.findViewById(R.id.VenueAddress3);
        SupportMapFragment mMapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        textVenueTitle.setText(venue.getVenueName());
        textVenueHall.setText(venue.getHallName());
        textVenueAddressLine1.setText(venue.getAddressLine1());
        textVenueAddressLine2.setText(venue.getAddressLine2());
        textVenueAddressLine3.setText(venue.getAddressLine3());

        setUpMapIfNeeded(venue);
       /* if (mMapFragment != null) {
            mMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap gMap) {
                    if (gMap != null) {

                        googleMap.getUiSettings().setAllGesturesEnabled(true);
                        googleMap = gMap;
                        googleMap.addMarker(new MarkerOptions().position(
                                new LatLng(venue.getCoordinateLat(), venue.getCoordinateLong())).title(venue.getVenueName()));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(venue.getCoordinateLat(), venue.getCoordinateLong()), 15));

                    }

                }
            });
        }*/

        return venueFragmentView;
    }

    /**** The mapfragment's id must be removed from the FragmentManager
     **** or else if the same it is passed on the next time then
     **** app will crash ****/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (googleMap != null) {
            VenueActivity.fragmentManager.beginTransaction()
                    .remove(VenueActivity.fragmentManager.findFragmentById(R.id.map)).commit();
            googleMap = null;
        }
    }

    /***** Sets up the map if it is possible to do so *****/
    public void setUpMapIfNeeded(Venue venue) {
        // Do a null check to confirm that we have not already instantiated the map.
     /*   if (googleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            googleMap = ((SupportMapFragment) VenueActivity.fragmentManager
                    .findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (googleMap != null)
                setUpMap(venue);
        }*/
        setUpMap(venue);

    }

    /**
     * This is where we can add markers or lines, add listeners or move the
     * camera.
     * <p>
     * This should only be called once and when we are sure that {@link #googleMap}
     * is not null.
     */
    private void setUpMap(Venue venue) {

        // For showing a move to my loction button
        googleMap.setMyLocationEnabled(true);

        googleMap = ((SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        // For dropping a marker at a point on the Map
        googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(venue.getCoordinateLat(),venue.getCoordinateLong()))
                        .title(venue.getVenueName())
                        .snippet(venue.getAddressLine1()));

        // For zooming automatically to the Dropped PIN Location
        googleMap.moveCamera(
                CameraUpdateFactory
                        .newLatLngZoom(new LatLng(venue.getCoordinateLat(), venue.getCoordinateLong()), 15));

    }

}

