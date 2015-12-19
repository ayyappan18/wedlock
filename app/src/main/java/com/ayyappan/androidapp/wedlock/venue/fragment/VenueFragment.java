package com.ayyappan.androidapp.wedlock.venue.fragment;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.venue.VenueActivity;
import com.ayyappan.androidapp.wedlock.venue.bean.Venue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ayyappan on 05/11/2015.
 */
public class VenueFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    private static GoogleMap googleMap;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static VenueFragment newInstance(int sectionNumber) {
        VenueFragment fragment = new VenueFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public VenueFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<Venue> venues = getActivity().getIntent().getParcelableArrayListExtra("venues");
        final Venue venue = venues.get(this.getArguments().getInt(ARG_SECTION_NUMBER));

        View venueFragmentView = inflater.inflate(R.layout.fragment_venue_details, container, false);

        TextView textVenueTitle = (TextView) venueFragmentView.findViewById(R.id.VenueTitle);
        TextView textVenueHall = (TextView) venueFragmentView.findViewById(R.id.VenueHall);
        TextView textVenueAddressLine1 = (TextView) venueFragmentView.findViewById(R.id.VenueAddress1);
        TextView textVenueAddressLine2 = (TextView) venueFragmentView.findViewById(R.id.VenueAddress2);
        TextView textVenueAddressLine3 = (TextView) venueFragmentView.findViewById(R.id.VenueAddress3);
        MapFragment mMapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);

        textVenueTitle.setText(venue.getVenueName());
        textVenueHall.setText(venue.getHallName());
        textVenueAddressLine1.setText(venue.getAddressLine1());
        textVenueAddressLine2.setText(venue.getAddressLine2());
        textVenueAddressLine3.setText(venue.getAddressLine3());
        setUpMapIfNeeded(venue);

        Button getDirectionButton = (Button) venueFragmentView.findViewById(R.id.GetDirections);
        getDirectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + venue.getCoordinateLat() + "," + venue.getCoordinateLong()));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                if (isIntentAvailable(getContext(), intent))
                    startActivity(intent);
                else
                    Toast.makeText(getContext(), "No map app is installed", Toast.LENGTH_SHORT).show();
            }
        });

        Button addToCalendarButton = (Button) venueFragmentView.findViewById(R.id.AddToCaledar);
        addToCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long beginTime = venue.getEventDate().getMillis();
                Long endTime = beginTime + 1000 * 60 * 60 * 4;
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                        .putExtra(CalendarContract.Events.TITLE, "Nivedhitha & Ayyappan Reception")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "Reception")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, venue.getVenueName())
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

                if (isIntentAvailable(getContext(), intent))
                    startActivity(intent);

                else
                    Toast.makeText(getContext(), "No calendar application configured", Toast.LENGTH_SHORT).show();
            }
        });

        return venueFragmentView;
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        return !context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).isEmpty();
    }

    @Override
    public void onResume() {
        super.onResume();
        //add the markers
        // if (googleMap != null) {
        ArrayList<Venue> venues = getActivity().getIntent().getParcelableArrayListExtra("venues");
        Venue venue = venues.get(this.getArguments().getInt(ARG_SECTION_NUMBER));
        setUpMapIfNeeded(venue);
        //}
    }

    /****
     * The mapfragment's id must be removed from the FragmentManager
     * *** or else if the same it is passed on the next time then
     * *** app will crash
     ****/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (googleMap != null) {
        /*    VenueActivity.fragmentManager.beginTransaction()
                    .remove(VenueActivity.fragmentManager.findFragmentById(R.id.map)).commit();*/
            googleMap = null;
        }
        //if (googleMap != null) {

            /*FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment fragment = (fm.findFragmentById(R.id.map));
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fragment);
            ft.commit();*/
        //}
        SupportMapFragment myMapFragment = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map));
        if (myMapFragment != null)
            getFragmentManager().beginTransaction().remove(myMapFragment).commit();
       /* if (this.myMapFragment != null
                && getFragmentManager().findFragmentById(
                this.myMapFragment.getId()) != null) {

            getFragmentManager().beginTransaction().remove(this.myMapFragment)
                    .commit();
            this.myMapFragment = null;
        }
*/
    }

    /*****
     * Sets up the map if it is possible to do so
     *****/
    public void setUpMapIfNeeded(final Venue venue) {
        // Do a null check to confirm that we have not already instantiated the map.
        //  if (myMapFragment == null) {
        // Try to obtain the map from the SupportMapFragment.

        SupportMapFragment myMapFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map));
        if (myMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            myMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, myMapFragment).commit();
        }
        if (myMapFragment != null) {
            myMapFragment.getMapAsync(new OnMapReadyCallback() {

                @Override
                public void onMapReady(GoogleMap gMap) {
                    // TODO Auto-generated method stub
                    googleMap = gMap;
                    setUpMap(venue);
                }
            });
        }
/*
            googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager()
                    .findFragmentById(R.id.map)).getMap();
           */
        // Check if we were successful in obtaining the map.
        /*    if (googleMap != null)
                setUpMap(venue);*/
        //  }
    }

    private void setUpMap(Venue venue) {


 /*       if(googleMap == null){
            googleMap = ((SupportMapFragment) getFragmentManager()
                    .findFragmentById(R.id.map)).getMap();
        }*/
        // For showing a move to my loction button
        googleMap.setMyLocationEnabled(true);

       /* googleMap = ((SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();*/
        // For dropping a marker at a point on the Map
        googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(venue.getCoordinateLat(), venue.getCoordinateLong()))
                        .title(venue.getVenueName())
                        .snippet(venue.getAddressLine1()));

        // For zooming automatically to the Dropped PIN Location
        googleMap.moveCamera(
                CameraUpdateFactory
                        .newLatLngZoom(new LatLng(venue.getCoordinateLat(), venue.getCoordinateLong()), 15));

    }
}