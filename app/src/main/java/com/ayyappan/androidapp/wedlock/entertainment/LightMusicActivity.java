package com.ayyappan.androidapp.wedlock.entertainment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;

public class LightMusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_music);

        Button button = (Button) findViewById(R.id.express_song_interest_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LightMusicActivity.this,SongRequestActivity.class));
            }
        });
    }
}
