package com.example.asus.weatherapplication;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

public class FirebasetoWeather extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    SharedPreferences shared;
    SharedPreferences.Editor edit;
    @Override
    public void onTokenRefresh() {
        shared=getSharedPreferences("app",MODE_PRIVATE);
        edit=shared.edit();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
        edit.putString("registration_id",refreshedToken);
        edit.commit();

        // TODO: Implement this method to send any registration to your app's servers.
        // sendRegistrationToServer(refreshedToken);
    }
}
