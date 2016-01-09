package com.ayyappan.androidapp.wedlock.activities;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.database.mongolab.PostSongDetailsAsyncTask;
import com.ayyappan.androidapp.wedlock.model.Song;
import com.ayyappan.androidapp.wedlock.database.MongoDB;
import com.ayyappan.androidapp.wedlock.home.GlobalData;

public class SongRequestActivity extends AppCompatActivity {

    private static boolean isBackPressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_song_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView movieName = (TextView) findViewById(R.id.movie_name);
        final TextView songName = (TextView) findViewById(R.id.song_name);


        final Button button = (Button) findViewById(R.id.submit_song_interest_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = new GlobalData(getApplicationContext()).getUser().getName();
                Song song = new Song(fullName, movieName.getText().toString(), songName.getText().toString());

                new PostSongDetailsAsyncTask(new PostSongDetailsAsyncTask.AsyncResponse() {
                    @Override
                    public void processFinish(Boolean result) {
                        if (true) {
                            Toast.makeText(getApplicationContext(), "Song interest is sent", Toast.LENGTH_SHORT).show();
                            final Button button = (Button) findViewById(R.id.submit_song_interest_button);
                            //TODO: Disable button
                            button.setEnabled(false);
                            button.setClickable(false);
                            if(!isBackPressed)
                            onBackPressed();
                        }
                    }
                }).execute(song);
            }
        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isBackPressed=true;
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        finish();
    }
}
