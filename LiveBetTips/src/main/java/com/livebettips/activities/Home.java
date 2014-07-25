package com.livebettips.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.livebettips.R;
import com.livebettips.objects.Api;

import java.io.IOException;

public class Home extends Activity {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    GoogleCloudMessaging gcm;
    String regID;
    Context ctx;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Boolean isFirstRun;
    String SENDER_ID = "369459831895";
    Boolean regGCMID = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        prefs = getSharedPreferences("bettips", MODE_PRIVATE);
        editor = prefs.edit();
        ctx= this;

        if (checkPlayServices()) {

            isFirstRun = prefs.getBoolean("isFirstRun", true);
            if (isFirstRun) {
                Log.d("first run","App running for first time");
                editor.putInt("VersionCode", getAppVersion(this));
                editor.putBoolean("isFirstRun",false);
                editor.commit();
            }

            gcm = GoogleCloudMessaging.getInstance(this);
            regID = getRegistrationID(this);

            if (regID.isEmpty()) {
                registerInBackground();

            }else {

                Api.applicationContext = this;
                mCountDown.start();
            }

       }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("LiveBetTips", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private String getRegistrationID(Context context) {
        prefs = getSharedPreferences("bettips", MODE_PRIVATE);
        String registrationId = prefs.getString("GCM_REG_ID", "");
        if (registrationId.isEmpty()) {
            regGCMID = false;
            Log.i("LiveBetTips", "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt("VersionCode", Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            regGCMID = false;
            Log.i("LiveBetTips", "App version changed.");
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public void registerInBackground() {
        new AsyncTask<Void, String, String>() {

            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {

                        gcm = GoogleCloudMessaging.getInstance(Home.this);

                        regID = gcm.register(SENDER_ID);

                    msg = "Device registered, registration ID=" + regID;
                    editor.putString("GCM_REG_ID", regID);
                    editor.commit();
                    regGCMID = true;

                } catch (IOException ex) {

                    regGCMID = false;

                    msg = "Error :" + ex.getMessage();

                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.d("GCM_REG_ID", msg + "\n");

                if(!regGCMID){
                    showalert();
                }else {
                    Api.applicationContext = Home.this;
                    mCountDown.start();
                }

            }
        }.execute(null, null, null);
    }

    void showalert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setMessage(getString(R.string.alert_text))
                .setCancelable(true)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {

                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        Api.applicationContext = Home.this;
                        mCountDown.start();


                    }
                })
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                       finish();
                    }
                })
                .show();
    }

    protected CountDownTimer mCountDown = new CountDownTimer(3000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {

            Log.d("Timer", "time for 3sec");

        }

        @Override
        public void onFinish() {
            Log.d("Timer", "3sec finish");


            Intent register = new Intent(Home.this, PushedPredictions.class);
            startActivity(register);

            finish();

        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Api.slidingMenu.toggle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

}
