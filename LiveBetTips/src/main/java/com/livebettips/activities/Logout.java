package com.livebettips.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.livebettips.R;
import com.livebettips.objects.Api;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Logout extends ActionBarActivity {

    Context ctx;
    ProgressDialog progressDialog;
    HashMap hashMap;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        ctx =this;
        hashMap = new HashMap();
        prefs = getSharedPreferences("bettips",MODE_PRIVATE);
        editor = prefs.edit();

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Logging Out");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        hashMap.put("deviceID",prefs.getString("GCM_REG_ID",""));

        Callback callback = new Callback(){


            @Override
            public void success(Object o, Response response) {

                editor.putBoolean("isLoggedIn",false);
                editor.remove("userID");
                editor.remove("userEmail");
                editor.remove("authToken");
                editor.commit();
                Toast.makeText(ctx,"You have logged out",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

                Intent intent = new Intent(Logout.this,Login.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void failure(RetrofitError error) {

                progressDialog.dismiss();

                Toast.makeText(ctx,"Some problem occured. ",Toast.LENGTH_LONG).show();
                finish();

            }
        };

          Api.userInterface.userLogout(hashMap,callback);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
