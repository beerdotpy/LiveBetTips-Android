package com.livebettips.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.livebettips.R;
import com.livebettips.objects.Api;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ContactUs extends ActionBarActivity {

    EditText subject,message;
    Button send;
    String userEmail;
    Boolean isLoggedIn;
    SharedPreferences prefs;
    ProgressDialog progressDialog;
    Context ctx;
    HashMap hashMAp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         ctx = this;

        hashMAp = new HashMap();

        Api.initSlidingMenu(ctx).attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Sending Message");
        progressDialog.setIndeterminate(true);

         subject = (EditText) findViewById(R.id.et_contact_us_subject);
         message = (EditText) findViewById(R.id.et_contact_us_message);
         send = (Button) findViewById(R.id.bt_contact_us_send);

        prefs = getSharedPreferences("bettips",MODE_PRIVATE);
        isLoggedIn = prefs.getBoolean("isLoggedIn",false);
        userEmail = prefs.getString("userEmail","");
        Log.d("email",userEmail);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                        hashMAp.put("email",userEmail);
                        hashMAp.put("subject",subject.getText().toString());
                        hashMAp.put("content",message.getText().toString());

                        Callback callback = new Callback() {
                            @Override
                            public void success(Object o, Response response) {

                                Toast.makeText(ctx,"Message has sent",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                finish();

                            }

                            @Override
                            public void failure(RetrofitError error) {

                                Toast.makeText(ctx,"Some Error occured.Please try again",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            }
                        };

                       Api.userInterface.userContact(hashMAp, callback);




            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact_us, menu);
        return true;
    }

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
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent back = new Intent(this,PushedPredictions.class);
        finish();
        startActivity(back);
    }


}
