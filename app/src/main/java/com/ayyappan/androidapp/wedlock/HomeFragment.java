package com.ayyappan.androidapp.wedlock;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    private TextView txtTimerDay, txtTimerHour, txtTimerMinute, txtTimerSecond, txtUsername;
    private TextView tvEvent;
    private Handler handler;
    private Runnable runnable;
    private RelativeLayout goingLayout;
    private RelativeLayout eventRespondLayout;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.title_fragment_home));

        // for refreshing UI
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
    //    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_fragment_home));

      /*  eventRespondLayout = (RelativeLayout) rootView.findViewById(R.id.EventRespond);
        Button clickGoingButton = (Button) rootView.findViewById(R.id.going);
        goingLayout = (RelativeLayout) rootView.findViewById(R.id.GoingResponse);
        clickGoingButton.setOnClickListener(GoingEventButtonListener());

        Button clickMaybeButton = (Button) rootView.findViewById(R.id.maybe);
        clickMaybeButton.setOnClickListener(MaybeEventButtonListener());
*/
        txtTimerDay = (TextView) rootView.findViewById(R.id.txtTimerDay);
        txtTimerHour = (TextView) rootView.findViewById(R.id.txtTimerHour);
        txtTimerMinute = (TextView) rootView.findViewById(R.id.txtTimerMinute);
        txtTimerSecond = (TextView) rootView.findViewById(R.id.txtTimerSecond);

     /*   String firstName = new GlobalData(getContext()).getUser().getName();
        txtUsername = (TextView) rootView.findViewById(R.id.username);
        txtUsername.setText("Welcome " + firstName + ",");*/

        countDownStart(rootView);
        return rootView;
    }

    private View.OnClickListener GoingEventButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventRespondLayout.setVisibility(View.INVISIBLE);
                goingLayout.setVisibility(View.VISIBLE);

            }
        };
    }

    private View.OnClickListener MaybeEventButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Send event to FB

            }
        };
    }

    public void countDownStart(final View rootView) {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    // Please here set your event date//YYYY-MM-DD
                    Date futureDate = dateFormat.parse("2016-02-10");
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        txtTimerDay.setText("" + String.format("%02d", days));
                        txtTimerHour.setText("" + String.format("%02d", hours));
                        txtTimerMinute.setText(""
                                + String.format("%02d", minutes));
                        txtTimerSecond.setText(""
                                + String.format("%02d", seconds));
                    } else {
                        tvEvent.setVisibility(View.VISIBLE);
                        tvEvent.setText("Start event now!");
                        textViewGone(rootView);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }

    public void textViewGone(View rootView) {
        rootView.findViewById(R.id.txtTimerDay).setVisibility(View.GONE);
        rootView.findViewById(R.id.txt_TimerDay).setVisibility(View.GONE);
        rootView.findViewById(R.id.txtTimerHour).setVisibility(View.GONE);
        rootView.findViewById(R.id.txt_TimerHour).setVisibility(View.GONE);
        rootView.findViewById(R.id.txtTimerMinute).setVisibility(View.GONE);
        rootView.findViewById(R.id.txt_TimerMinute).setVisibility(View.GONE);
        rootView.findViewById(R.id.txtTimerSecond).setVisibility(View.GONE);
        rootView.findViewById(R.id.txt_TimerSecond).setVisibility(View.GONE);
    }

}
