package com.ayyappan.androidapp.wedlock.login.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * Created by AndreBTS on 25/09/2015.
 */
public class CheckNetwork {

    public boolean isConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public boolean isOnline(final Context context) {
        if (isConnected(context)) {
            return true;
        }
        AlertDialog.Builder dialogb = new AlertDialog.Builder(context);

        dialogb.setTitle("No Internet.. :(");
        dialogb.setMessage("We need internet to work. Kindly switch it on.");
        dialogb.setPositiveButton("Turn on", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent( Settings.ACTION_WIRELESS_SETTINGS);
                context.startActivity(myIntent);
                //get gps

            }
        });
        dialogb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub


            }
        });
        dialogb.show();
        return false;
    }
}
