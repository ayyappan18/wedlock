package com.ayyappan.androidapp.wedlock.menudrawer.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions;

import java.util.HashMap;

/**
 * Created by Ayyappan on 20/12/2015.
 */
public class IconDecoder {

    private static final int HOME_ICON_RES_ID = R.drawable.home_icon;
    private static final int BIOGRAPHY_ICON_RES_ID = R.drawable.bio_icon;
    private static final int GALLERY_ICON_RES_ID = R.drawable.gallery_icon;
    private static final int ENTERTAINMENT_ICON_RES_ID = R.drawable.entertainment_icon;
    private static final int INVITATION_ICON_RES_ID = R.drawable.invitation_icon;
    private static final int EVENTS_ICON_RES_ID = R.drawable.event_icon;
    private static final int ABOUT_ICON_RES_ID = R.drawable.about_icon;

    private static final int BRIDE_RES_ID = R.drawable.bride;
    private static final int GROOM_RES_ID = R.drawable.groom;

    private static HashMap<String,Bitmap> menuIcons = null;
    private static HashMap<String, Bitmap> biographyBitmaps = null;

    public static HashMap<String, Bitmap> getMenuIconBitMaps(Context context){
        if(menuIcons ==null) {
            HashMap<String, Bitmap> bitmaps = new HashMap<>();
            bitmaps.put(MenuOptions.HOME, decodeSampledBitmapFromResource(context.getResources(), HOME_ICON_RES_ID, 48, 48));
            bitmaps.put(MenuOptions.BIOGRAPHY, decodeSampledBitmapFromResource(context.getResources(), BIOGRAPHY_ICON_RES_ID, 48, 48));
            bitmaps.put(MenuOptions.GALLERY, decodeSampledBitmapFromResource(context.getResources(), GALLERY_ICON_RES_ID, 48, 48));
            bitmaps.put(MenuOptions.ENTERTAINMENT, decodeSampledBitmapFromResource(context.getResources(), ENTERTAINMENT_ICON_RES_ID, 48, 48));
            bitmaps.put(MenuOptions.INVITATION, decodeSampledBitmapFromResource(context.getResources(), INVITATION_ICON_RES_ID, 48, 48));
            bitmaps.put(MenuOptions.EVENTS, decodeSampledBitmapFromResource(context.getResources(), EVENTS_ICON_RES_ID, 48, 48));
            bitmaps.put(MenuOptions.ABOUT, decodeSampledBitmapFromResource(context.getResources(), ABOUT_ICON_RES_ID, 48, 48));
            menuIcons = bitmaps;
            return bitmaps;
        }
        else
         return menuIcons;
    }

    public static HashMap<String, Bitmap> getCoupleProfileBitMaps(Context context){
        if(biographyBitmaps == null){
            HashMap<String, Bitmap> bitmaps = new HashMap<>();
            bitmaps.put("bride",decodeSampledBitmapFromResource(context.getResources(), BRIDE_RES_ID, 512, 512));
            bitmaps.put("groom",decodeSampledBitmapFromResource(context.getResources(), GROOM_RES_ID, 512, 512));
            biographyBitmaps = bitmaps;
            return bitmaps;
        }
        else
            return biographyBitmaps;
    }

    private static int getBitmapId(String header){
        switch(header){
            case MenuOptions.HOME : return R.drawable.home_icon;
            case MenuOptions.BIOGRAPHY : return R.drawable.bio_icon;
            case MenuOptions.GALLERY : return R.drawable.gallery_icon;
            case MenuOptions.ENTERTAINMENT : return R.drawable.entertainment_icon;
            case MenuOptions.INVITATION : return R.drawable.invitation_icon;
            case MenuOptions.EVENTS : return R.drawable.event_icon;
            case MenuOptions.ABOUT : return R.drawable.about_icon;
            default : return R.drawable. about_icon;
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
