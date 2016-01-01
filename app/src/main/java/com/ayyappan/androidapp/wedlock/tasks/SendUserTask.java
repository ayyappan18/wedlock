package com.ayyappan.androidapp.wedlock.tasks;

import android.os.AsyncTask;

import com.ayyappan.androidapp.wedlock.database.MongoDB;
import com.ayyappan.androidapp.wedlock.login.bean.User;

/**
 * Created by Ayyappan on 31/12/2015.
 */
public class SendUserTask extends AsyncTask<User, Void, Void> {
    private User user;

    public SendUserTask(User user) {
        this.user = user;
    }

    @Override
    protected Void doInBackground(User... params) {
        MongoDB remoteDB = new MongoDB();
        remoteDB.sendUser(user);
        return null;
    }

}
