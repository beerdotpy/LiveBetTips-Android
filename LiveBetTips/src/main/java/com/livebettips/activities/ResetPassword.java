package com.livebettips.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.livebettips.R;
import com.livebettips.objects.Api;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ResetPassword extends ActionBarActivity {

    EditText et_email;
    Button send;
    HashMap hashMap;
    Context ctx;
    String email;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ctx = this;

        hashMap = new HashMap();


        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Sending email...");
        progressDialog.setIndeterminate(true);

        et_email = (EditText) findViewById(R.id.et_reset_password_email);
     send = (Button) findViewById(R.id.bt_reset_password_reset);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                email = et_email.getText().toString();
                hashMap.put("email",email);

                Callback callback = new Callback() {
                    @Override
                    public void success(Object o, Response response) {

                        progressDialog.dismiss();
                        Toast.makeText(ctx,"Email has been sent to "+email+" for reseting password",Toast.LENGTH_LONG).show();
                        finish();

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        progressDialog.dismiss();
                        Toast.makeText(ctx,"Some error occurred. Please try again",Toast.LENGTH_LONG).show();

                    }
                };


                Api.userInterface.userresetPassword(hashMap, callback);


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reset_password, menu);
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
