package com.ayyappan.androidapp.wedlock.childfragments;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.model.Venue;
import com.ayyappan.androidapp.wedlock.utils.IconDecoder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Created by Ayyappan on 05/11/2015.
 */
public class EventDetailsFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    Venue venue;
    private static GoogleMap googleMap;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EventDetailsFragment newInstance(int sectionNumber, Venue venue) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        fragment.venue = venue;
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public EventDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View venueFragmentView = inflater.inflate(R.layout.fragment_event_details, container, false);

        //Event Icon
        ImageView eventIcon = (ImageView) venueFragmentView.findViewById(R.id.event_icon);

        //Event Title
        TextView textEventTitle = (TextView) venueFragmentView.findViewById(R.id.txt_event_name);

        //Event Location Details
        TextView textVenueTitle = (TextView) venueFragmentView.findViewById(R.id.VenueTitle);
        TextView textVenueAddress = (TextView) venueFragmentView.findViewById(R.id.VenueAddress);

        int eventResourceId = IconDecoder.getResourceId(venue.getEventName(), getContext());
        eventIcon.setImageResource(eventResourceId);

        //Set event location details
        String address = venue.getAddressLine1() + "\n" + venue.getAddressLine2() + "\n" + venue.getAddressLine3();
        textEventTitle.setText(venue.getEventName());
        textVenueTitle.setText(venue.getVenueName());
        textVenueAddress.setText(address);

        //Set event date time details
        DateTimeFormatter dayFormatter = DateTimeFormat.forPattern("EEEE");
        DateTimeFormatter monthFormatter = DateTimeFormat.forPattern("MMM");
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("hh:mm a");

        String day = dayFormatter.withLocale(Locale.getDefault()).print(venue.getEventDate());
        String month = monthFormatter.withLocale(Locale.getDefault()).print(venue.getEventDate());
        String date = Integer.toString(venue.getEventDate().getDayOfMonth());
        String time = timeFormatter.withLocale(Locale.getDefault()).print(venue.getEventDate());

        String dateTime = day + "," + month + date + "-" + time;
        TextView textEventDay = (TextView) venueFragmentView.findViewById(R.id.txt_event_date_time);
        textEventDay.setText(dateTime);

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
        setUpMapIfNeeded(venue);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (googleMap != null) {
            googleMap = null;
        }
        SupportMapFragment myMapFragment = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map));
        if (myMapFragment != null)
            getFragmentManager().beginTransaction().remove(myMapFragment).commit();
    }

    public void setUpMapIfNeeded(final Venue venue) {
        SupportMapFragment myMapFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map));
        if (myMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            myMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, myMapFragment).commit();
        }
        myMapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;
                setUpMap(venue);
            }
        });

    }

    private void setUpMap(Venue venue) {
        googleMap.setMyLocationEnabled(true);
        googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(venue.getCoordinateLat(), venue.getCoordinateLong()))
                        .title(venue.getVenueName())
                        .snippet(venue.getAddressLine1()));
        googleMap.moveCamera(
                CameraUpdateFactory
                        .newLatLngZoom(new LatLng(venue.getCoordinateLat(), venue.getCoordinateLong()), 15));
    }
}