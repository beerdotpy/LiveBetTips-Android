package com.livebettips.activites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.livebettips.R;
import com.livebettips.classes.User;
import com.livebettips.interfaces.UserInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Register extends Activity {

    Button bt_register;
    EditText et_email,et_password,et_repassword;
    String email,password,repassword;
    Context ctx;
    Pattern pattern;
    User user;
    String API_URL= "http://178.21.172.107" ;
    UserInterface userInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RestAdapter userAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL+"/api")
                .build();

        userInterface = userAdapter.create(UserInterface.class);
        ctx = this;

        bt_register = (Button) findViewById(R.id.bt_register);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_repassword = (EditText) findViewById(R.id.et_repassword);
        user = new User();

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);

        /* TO DO
              implement dynamic checking for password
         */

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = et_email.getText().toString();
                password = et_password.getText().toString();
                repassword = et_repassword.getText().toString();

                boolean valid = checkDetails(email,password,repassword);
                if(valid)
                {
                    final ProgressDialog progressDialog = new ProgressDialog(ctx);
                    progressDialog.setTitle("Please Wait");
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();

                    Callback callback = new Callback() {
                        @Override
                        public void success(Object o, Response response) {
                            // Read response here
                            progressDialog.dismiss();
                            Log.d("Object", o.toString());
                            Toast.makeText(ctx,"Registered Successfully.\n" +
                                                "Email has been sent for verification ",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            progressDialog.dismiss();
                            Log.d("error", retrofitError.toString());
                            // Catch error here
                        } };
                    userInterface.createUser(user, callback);
                }
            }
        });


    }

    public boolean checkDetails(String email,String password,String repassword){

        Matcher matcher = pattern.matcher(email);
        if (!email.contentEquals("") && !password.contentEquals("")  && !repassword.contentEquals("")   ){   //Check if any field is left empty
            if (matcher.matches()) {                                                                         //Check if email id is valid or not
                if (password.contentEquals(repassword)) {                                                    //Check if password matches or not
                    Toast.makeText(ctx, "Password match", Toast.LENGTH_LONG).show();
                    user.setEmail(email);
                    user.setPassword(password);
                    return true;
                } else {
                    Toast.makeText(ctx, "Password did not match", Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                Toast.makeText(ctx, "Please enter a valid EmailID", Toast.LENGTH_LONG).show();
                return false;
            }
        }else{
            Toast.makeText(ctx,"Please fill all the details",Toast.LENGTH_LONG).show();
            return false;
        }
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
