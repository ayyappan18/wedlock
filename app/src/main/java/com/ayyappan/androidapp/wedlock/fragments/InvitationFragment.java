package com.ayyappan.androidapp.wedlock.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class InvitationFragment extends Fragment {

    public static String DIRECTORY_WEDLOCK = "Wedlock";
    private static String fileName = "nivi_ayys_wedding_invitation.pdf";

    public static InvitationFragment newInstance() {
        InvitationFragment fragment = new InvitationFragment();
        return fragment;
    }

    public InvitationFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_invitation, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.title_fragment_invitation));

        // for refreshing UI
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_fragment_invitation));

        ImageView invitationPreview = (ImageView) rootView.findViewById(R.id.invitation_preview);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("application/pdf");
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);

        if (activities.size() > 0) {
            invitationPreview.setOnClickListener(invitationClickListener);
        } else {
            Toast.makeText(getContext(), "Please install PDF viewer to view the invitation", Toast.LENGTH_SHORT).show();
        }

        return rootView;
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
                AssetManager assetManager = getActivity().getAssets();
                in = assetManager.open(fileName);
                out = new BufferedOutputStream(new FileOutputStream(file));
                copyFile(in, out);
                in.close();
                out.flush();
                out.close();
                Toast.makeText(getContext(), "Invitation saved in Wedlock folder in storage", Toast.LENGTH_SHORT).show();
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
