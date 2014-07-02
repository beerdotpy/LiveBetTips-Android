package com.livebettips.activites;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.livebettips.R;


public class Register extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //        User user = new User();
//        user.email = "sarthak_mehrish@yahoo.co.in";
//        user.password = "qwerty";
//
//        Callback callback = new Callback() {
//            @Override
//            public void success(Object o, Response response) {
//                // Read response here
//                Log.d("Response", response.toString());
//            }
//
//            @Override
//            public void failure(RetrofitError retrofitError) {
//
//                Log.d("error", retrofitError.toString());
//                // Catch error here
//            } };
//        userInterface.createUser(user,callback);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
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
