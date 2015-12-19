package com.ayyappan.androidapp.wedlock.entertainment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.menudrawer.MenuDrawerActivity;

public class LightMusicActivity extends MenuDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_music);
        super.onCreateDrawer();

        Button button = (Button) findViewById(R.id.express_song_interest_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LightMusicActivity.this,SongRequestActivity.class));
            }
        });
    }
}
