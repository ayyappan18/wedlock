package com.ayyappan.androidapp.wedlock.utils;

import android.text.TextUtils;

/**
 * Created by AndreBTS on 25/09/2015.
 */
public class ValidateUserInfo {
    public static boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        //TODO change for your own logic
        return password.length() > 4;
    }
}
