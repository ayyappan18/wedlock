package com.ayyappan.androidapp.wedlock.entertainment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.entertainment.bean.Song;
import com.ayyappan.androidapp.wedlock.database.MongoDB;
import com.ayyappan.androidapp.wedlock.home.GlobalData;

public class SongRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_request);

        final TextView movieName = (TextView) findViewById(R.id.movie_name);
        final TextView songName = (TextView) findViewById(R.id.song_name);



        final Button button = (Button) findViewById(R.id.submit_song_interest_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = new GlobalData(getApplicationContext()).getUser().getName();
                Song song = new Song(fullName,movieName.getText().toString(), songName.getText().toString());

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                new SendSongInterestAsync().execute(song);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                //TODO: Disable button
                button.setEnabled(false);
                button.setClickable(false);
            }
        });
    }

    private class SendSongInterestAsync extends AsyncTask<Song, Void, Void> {

        @Override
        protected Void doInBackground(Song... songs) {
            MongoDB db = new MongoDB();
            db.sendSongInterest(songs[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(SongRequestActivity.this, "Your song interest has been saved." , Toast.LENGTH_SHORT).show();
            final Button button = (Button) findViewById(R.id.submit_song_interest_button);
            button.setEnabled(true);
            button.setClickable(true);
        }
    }
}
