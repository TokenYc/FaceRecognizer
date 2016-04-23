package edu.uj.faceRecognizer.faceRecognition.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * User: piotrplaneta
 * Date: 16.01.2013
 * Time: 00:07
 */
public class AppPreferences {
    private static final String APP_SHARED_PREFS = "edu.uj.faceRecognizer.faceRecognition_preferences";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    public AppPreferences(Context context)
    {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_WORLD_READABLE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public String getEmail() {
        return appSharedPrefs.getString("email", "none");
    }

    public void setEmail(String email) {
        prefsEditor.putString("email", email);
        prefsEditor.commit();
    }
}
