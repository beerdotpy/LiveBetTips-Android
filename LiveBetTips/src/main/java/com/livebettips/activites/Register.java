package com.livebettips.activites;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.livebettips.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register extends Activity {

    Button bt_register;
    EditText et_email,et_password,et_repassword;
    String email,password,repassword;
    Context ctx;
    Pattern pattern;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ctx = getApplicationContext();

        bt_register = (Button) findViewById(R.id.bt_register);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_repassword = (EditText) findViewById(R.id.et_repassword);

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);

        /* TO DO
             check for null pointer in edit text values
             implement dynamic checking for password
         */

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = et_email.getText().toString();
                password = et_password.getText().toString();
                repassword = et_repassword.getText().toString();

                Matcher matcher = pattern.matcher(email);

                if (matcher.matches()) {
                    if (password.contentEquals(repassword)) {
                        Toast.makeText(ctx, "Password match", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ctx, "Password did not match", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ctx, "Please enter a valid email id", Toast.LENGTH_LONG).show();
                }
            }
        });

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
