package com.ayyappan.androidapp.wedlock.unused;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.ayyappan.androidapp.wedlock.AboutActivity;
import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.TestActivity;
import com.ayyappan.androidapp.wedlock.database.DataSource;
import com.ayyappan.androidapp.wedlock.database.googledrive.GoogleDriveDataSource;
import com.ayyappan.androidapp.wedlock.imageslideshow.ImageViewerActivity;
import com.ayyappan.androidapp.wedlock.venue.bean.Venue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VenueDetailsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
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

    private static final long ANIM_VIEWPAGER_DELAY = 5000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;


    private ViewPager mViewPager;
    boolean stopSliding = false;
    private static GoogleMap map;

    private static List<Venue> venues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_total);
            // Add fragment code here

        //Fetch the venues list
        VenueFetcher venueFetcher = new VenueFetcher();
        venueFetcher.execute();

        //Drawer
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        getMenuInflater().inflate(R.menu.menu_venue_details, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
        } else if (id == R.id.nav_biography) {
            startActivity(new Intent(VenueDetailsActivity.this, ImageViewerActivity.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_blog) {

        } else if (id == R.id.nav_invitation) {
            startActivity(new Intent(VenueDetailsActivity.this, TestActivity.class));
        } else if (id == R.id.nav_venue) {
            startActivity(new Intent(VenueDetailsActivity.this, VenueDetailsActivity.class));

        }else if (id == R.id.nav_about) {
            startActivity(new Intent(VenueDetailsActivity.this, AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return venues.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return venues.get(position).getEventName();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

      /*  @Override
        public void onDestroyView() {
            super.onDestroyView();
            SupportMapFragment f = (SupportMapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            if (f != null)
                getFragmentManager().beginTransaction().remove(f).commit();
        }*/

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
          //  if (mSupportMapFragment == null) {
    /*        SupportMapFragment mSupportMapFragment = SupportMapFragment.newInstance();
           //}
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            // R.id.map is a layout
            transaction.replace(R.id.map, mSupportMapFragment).commit();
*/
            SupportMapFragment mMapFragment = (SupportMapFragment) getActivity()
               .getSupportFragmentManager().findFragmentById(R.id.map);
/*

            if (mMapFragment == null) {
                		        mMapFragment = SupportMapFragment.newInstance();
                		        FragmentManager fm = getActivity().getSupportFragmentManager();
                		        fm.beginTransaction().replace(R.id.map, mMapFragment).commit();
                		    }

*/
            View rootView = inflater.inflate(R.layout.fragment_venue_details, container, false);

            //Get venue details
            final Venue venue = venues.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1);

            //Event Title
            TextView textEventTitle = (TextView) rootView.findViewById(R.id.txt_event_name);

            //Event Location Details
            TextView textVenueTitle = (TextView) rootView.findViewById(R.id.VenueTitle);
            TextView textVenueAddress = (TextView) rootView.findViewById(R.id.VenueAddress);

            //Event Date TIme Details
            TextView textEventDay = (TextView) rootView.findViewById(R.id.txt_event_day);
            TextView textEventMonth = (TextView) rootView.findViewById(R.id.txt_event_month);
            TextView textEventDate = (TextView) rootView.findViewById(R.id.txt_event_date);
            TextView textEventTime = (TextView) rootView.findViewById(R.id.txt_event_time);

            //Set event location details
            String address = venue.getAddressLine1() + "\n" + venue.getAddressLine2() + "\n" + venue.getAddressLine3();
            textEventTitle.setText(venue.getEventName());
            textVenueTitle.setText(venue.getVenueName());
            textVenueAddress.setText(address);

            //Set event date time details
            DateTimeFormatter dayFormatter = DateTimeFormat.forPattern("E");
            DateTimeFormatter monthFormatter = DateTimeFormat.forPattern("MMM");
            DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("hh:mm a");

            String day = dayFormatter.withLocale(Locale.getDefault()).print(venue.getEventDate());
            String month = monthFormatter.withLocale(Locale.getDefault()).print(venue.getEventDate());
            String date = Integer.toString(venue.getEventDate().getDayOfMonth());
            String time = timeFormatter.withLocale(Locale.getDefault()).print(venue.getEventDate());

            textEventDay.setText(day);
            textEventMonth.setText(month);
            textEventDate.setText(date);
            textEventTime.setText(time);

            if (mMapFragment != null) {
                mMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        if (googleMap != null) {

                            googleMap.getUiSettings().setAllGesturesEnabled(true);
                            map = googleMap;
                            map.addMarker(new MarkerOptions().position(
                                    new LatLng(venue.getCoordinateLat(), venue.getCoordinateLong())).title(venue.getVenueName()));
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(venue.getCoordinateLat(), venue.getCoordinateLong()), 15));

                        }

                    }
                });
            }


           /* map = ((SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map)).getMap();

            map.addMarker(new MarkerOptions().position(
                    new LatLng(venue.getCoordinateLat(), venue.getCoordinateLong())).title(venue.getVenueName()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(venue.getCoordinateLat(), venue.getCoordinateLong()), 15));

*/

            return rootView;
        }
    }


    private class VenueFetcher extends AsyncTask<Void, Void, List<Venue>> {

        @Override
        protected List<Venue> doInBackground(Void... params) {
         //   try {
                DataSource dataSource = new GoogleDriveDataSource();
               // return dataSource.getVenues("17I2RPW-EO6NXsneJTX9cd_k9cqejhFGHEwKAes8UqV8");
                List<Venue> v = new ArrayList<>();
                v.add(new Venue("Engagement", new DateTime("2016-02-10"),"Hotel Savera","Samvesh","146, Dr.Radhakrishnan Road","Mylapore, Chennai","Tamil Nadu 600004, India",13.045239, 80.261680));
                v.add(new Venue("Reception", new DateTime("2016-02-10"),"Hotel Savera","Samvesh","146, Dr.Radhakrishnan Road","Mylapore, Chennai","Tamil Nadu 600004, India",13.045323, 80.071514));
                v.add(new Venue("Wedding", new DateTime("2016-02-10"),"Hotel Savera","Samvesh","146, Dr.Radhakrishnan Road","Mylapore, Chennai","Tamil Nadu 600004, India",13.045323, 80.071514));
                return v;
          /*  } catch (IOException e) {
                e.printStackTrace();
                return null;

            }  catch (ServiceException e){
                e.printStackTrace();
                return null;
            }*/
        }

        @Override
        protected void onPostExecute(List<Venue> venuess) {
            super.onPostExecute(venuess);
            venues = venuess;
            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.

            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);

///////



        }
    }
}

