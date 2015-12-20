package com.ayyappan.androidapp.wedlock.unused;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.menudrawer.MenuDrawerActivity;
import com.ayyappan.androidapp.wedlock.venue.adapter.SectionsPagerAdapter;
import com.ayyappan.androidapp.wedlock.venue.bean.Venue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Ayyappan on 04/11/2015.
 */
public class VenueActivityOld extends MenuDrawerActivity {

    private static GoogleMap googleMap;
    public static FragmentManager fragmentManager;
    SupportMapFragment mMapFragment;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);
        super.onCreateDrawer();

     /*   List<Venue> venues = getIntent().getExtras().getParcelableArray("venues");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);*/
/*

        venueFragment = new VenueFragment();
        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        //fragmentManager = getSupportFragmentManager();
        venueFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, venueFragment).commit();
*/


        /*Venue venue = getIntent().getExtras().getParcelable("venue");

        TextView textVenueTitle = (TextView) findViewById(R.id.VenueTitle);
        TextView textVenueHall = (TextView) findViewById(R.id.VenueHall);
        TextView textVenueAddressLine1 = (TextView) findViewById(R.id.VenueAddress1);
        TextView textVenueAddressLine2 = (TextView) findViewById(R.id.VenueAddress2);
        TextView textVenueAddressLine3 = (TextView) findViewById(R.id.VenueAddress3);
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        textVenueTitle.setText(venue.getVenueName());
        textVenueHall.setText(venue.getHallName());
        textVenueAddressLine1.setText(venue.getAddressLine1());
        textVenueAddressLine2.setText(venue.getAddressLine2());
        textVenueAddressLine3.setText(venue.getAddressLine3());

        setUpMapIfNeeded(venue);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      /*  if (googleMap != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentById(R.id.gmap)).commit();
            googleMap = null;
        }*/
    }

    /*****
     * Sets up the map if it is possible to do so
     *****/
    public void setUpMapIfNeeded(Venue venue) {
        // Do a null check to confirm that we have not already instantiated the map.
   //     if (googleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
         /*   googleMap = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.gmap)).getMap();
           // setUpMap(venue);*/

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

          //  setUpMap(venue);

            // Check if we were successful in obtaining the map.
      /*  }
        if (googleMap != null)
        googleMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gmap)).getMap();*/
    }

    /**
     * This is where we can add markers or lines, add listeners or move the
     * camera.
     * <p/>
     * This should only be called once and when we are sure that {@link #googleMap}
     * is not null.
     */
    private void setUpMap(final Venue venue) {

        if (mMapFragment != null) {
            mMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap gmap) {
                    if (googleMap != null) {

                        googleMap = gmap;

                        googleMap.setMyLocationEnabled(true);
                        googleMap.getUiSettings().setAllGesturesEnabled(true);

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
            });

        }
    }
}
