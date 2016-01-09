package com.ayyappan.androidapp.wedlock.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.database.local.DBHelper;
import com.ayyappan.androidapp.wedlock.database.mongolab.PostRSVPResponseAsyncTask;
import com.ayyappan.androidapp.wedlock.database.mongolab.UpdateRSVPResponseAsyncTask;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.model.Rsvp;
import com.ayyappan.androidapp.wedlock.model.User;

public class RSVPFragment extends Fragment {

    private GlobalData data;
    private DBHelper local;
    public static RSVPFragment newInstance() {
        RSVPFragment fragment = new RSVPFragment();
        return fragment;
    }

    public RSVPFragment() {

    }

    TextView response;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_rsvp, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.title_fragment_rsvp));

        // for refreshing UI
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //  ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_fragment_about));

        ImageView yes = (ImageView) rootView.findViewById(R.id.yes_button);
        yes.setOnClickListener(yesResponse());

        ImageView mayBe = (ImageView) rootView.findViewById(R.id.maybe_button);
        mayBe.setOnClickListener(mayBeResponse());

        ImageView no = (ImageView) rootView.findViewById(R.id.no_button);
        no.setOnClickListener(noResponse());

        data = new GlobalData(getContext());

        response = (TextView) rootView.findViewById(R.id.rsvp_response);

        local = new DBHelper(getContext());
        Rsvp rsvp = local.retrieveRsvp(data.getUser().getName());
        if (rsvp == null)
            response.setVisibility(View.INVISIBLE);
        else {
            response.setText("You have selected " + rsvp.getResponse());
            response.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    private View.OnClickListener yesResponse() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText("You have selected Yes");
                response.setVisibility(View.VISIBLE);

                User user = data.getUser();
                Rsvp rsvp = new Rsvp(user.getName(), user.getEmail(), "Yes");
                insertOrUpdateInMongo(user,rsvp);
            }
        };
    }

    private View.OnClickListener mayBeResponse() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText("You have selected Maybe");
                response.setVisibility(View.VISIBLE);
                User user = data.getUser();
                Rsvp rsvp = new Rsvp(user.getName(), user.getEmail(), "Maybe");
                insertOrUpdateInMongo(user, rsvp);
            }
        };
    }

    private View.OnClickListener noResponse() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText("You have selected No");
                response.setVisibility(View.VISIBLE);
                User user = data.getUser();
                Rsvp rsvp = new Rsvp(user.getName(), user.getEmail(), "No");
                insertOrUpdateInMongo(user,rsvp);
            }
        };
    }

    private void insertOrUpdateInMongo(User user, Rsvp rsvp){
        Rsvp existingRsvp = local.retrieveRsvp(user.getName());
        if(existingRsvp != null && existingRsvp.getOid() != null) {
            rsvp.setOid(existingRsvp.getOid());
            new UpdateRSVPResponseAsyncTask(getContext()).execute(rsvp);
        }
        else{
            new PostRSVPResponseAsyncTask(getContext()).execute(rsvp);
        }
    }
}
