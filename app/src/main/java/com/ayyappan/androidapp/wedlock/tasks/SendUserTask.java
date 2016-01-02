package com.ayyappan.androidapp.wedlock.tasks;

import android.os.AsyncTask;

import com.ayyappan.androidapp.wedlock.database.MongoDB;
import com.ayyappan.androidapp.wedlock.model.User;

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
        try {
            remoteDB.sendUser(user);
        }catch(Exception ex){
            System.out.println(ex);
            return null;
        }
        return null;
    }

}
