package com.ayyappan.androidapp.wedlock;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.menudrawer.MenuDrawerActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class InvitationActivity extends MenuDrawerActivity {

    public static String DIRECTORY_WEDLOCK = "Wedlock";
    private static String fileName = "nivi_ayys_wedding_invitation.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        super.onCreateDrawer();

        ImageView invitationPreview = (ImageView) findViewById(R.id.invitation_preview);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("application/pdf");
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);

        if (activities.size() > 0) {
            invitationPreview.setImageDrawable(getResources().getDrawable(R.drawable.invitation_preview));
            invitationPreview.setOnClickListener(invitationClickListener);
        } else {
            Toast.makeText(getApplicationContext(), "Please install PDF viewer to view the invitation", Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener invitationClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            copyInvitationPdfToSdCardIfRequired();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://" + Environment.getExternalStoragePublicDirectory(DIRECTORY_WEDLOCK) + File.separator + fileName), "application/pdf");
            startActivity(intent);
        }
    };

    private void copyInvitationPdfToSdCardIfRequired() {
        String strDir = Environment.getExternalStoragePublicDirectory(DIRECTORY_WEDLOCK) + File.separator;
        File fileDir = new File(strDir);
        fileDir.mkdirs();
        File file = new File(fileDir, fileName);
        if (!file.exists()) {
            try {
                InputStream in;
                OutputStream out;
                AssetManager assetManager = getAssets();
                in = assetManager.open(fileName);
                out = new BufferedOutputStream(new FileOutputStream(file));
                copyFile(in, out);
                in.close();
                out.flush();
                out.close();
                Toast.makeText(getApplicationContext(), "Invitation saved in Wedlock folder in storage", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
