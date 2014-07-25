package com.livebettips.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.livebettips.R;
import com.livebettips.objects.Api;
import com.livebettips.objects.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Register extends ActionBarActivity {

    Button bt_register;
    EditText et_email,et_password,et_repassword;
    TextView tv_validEmail,tv_validPassword,tv_validrePassword,tv_terms;
    String email,password,repassword;
    User user;
    Context ctx;
    Boolean valid=false;
    Boolean terms_accepted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ctx = this;

        Api.initSlidingMenu(ctx).attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        bt_register = (Button) findViewById(R.id.bt_register_register);
        et_email = (EditText) findViewById(R.id.et_register_email);
        et_password = (EditText) findViewById(R.id.et_register_password);
        et_repassword = (EditText) findViewById(R.id.et_register_repassword);
        tv_validEmail = (TextView) findViewById(R.id.tv_register_validEmail);
        tv_validPassword = (TextView) findViewById(R.id.tv_register_validPassword);
        tv_validrePassword = (TextView) findViewById(R.id.tv_register_validrePassword);
        tv_terms = (TextView) findViewById(R.id.tv_register_terms);
        tv_terms.setText(getString(R.string.agree)+" "+getString(R.string.terms));
        user = new User();

        bt_register.setEnabled(false);

        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                    email = et_email.getText().toString();
                    Matcher matcher = pattern.matcher(email);
                    if(!matcher.matches()){
                        tv_validEmail.setTextColor(Color.RED);
                        tv_validEmail.setText("Please Enter a valid Email ID");
                        valid=false;
                    }else{
                        tv_validEmail.setTextColor(Color.WHITE);
                        tv_validEmail.setText("Email ID is valid");
                        valid=true;
                    }
                }
            }
        });

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    password = et_password.getText().toString();
                    if (password != "") {
                        if (password.length() >= 5 && password.length() < 8) {
                            tv_validPassword.setTextColor(Color.BLUE);
                            tv_validPassword.setText("Strength : FAIR ");
                            valid=true;
                        }else if(password.length() >= 8 ) {
                            tv_validPassword.setTextColor(Color.YELLOW);
                            tv_validPassword.setText("Strength : STRONG");
                            valid = true;
                        }else{
                            tv_validPassword.setTextColor(Color.RED);
                            tv_validPassword.setText("Password must be greater than 5 characters");
                            valid=false;
                        }
                    }else{
                            tv_validPassword.setTextColor(Color.RED);
                            tv_validPassword.setText("This field is required");
                            valid=false;
                    }
                }
            }
        });
         et_repassword.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                   if(password.contentEquals(s)){
                       tv_validrePassword.setTextColor(Color.WHITE);
                       tv_validrePassword.setText(getString(R.string.password_match));
                       if(valid){
                           bt_register.setEnabled(true);
                       }
                   }else{
                       tv_validrePassword.setTextColor(Color.RED);
                       tv_validrePassword.setText("Password does not match");
                       bt_register.setEnabled(false);
                   }
             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(terms_accepted) {
                    user.setEmail(et_email.getText().toString());
                    user.setPassword(et_password.getText().toString());
                    final ProgressDialog progressDialog = new ProgressDialog(ctx);
                    progressDialog.setTitle("Please Wait");
                    progressDialog.setMessage("Registering...");
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();

                    Callback callback = new Callback() {
                        @Override
                        public void success(Object o, Response response) {
                            // Read response here
                            progressDialog.dismiss();
                            tv_validEmail.setTextColor(Color.WHITE);
                            tv_validEmail.setText("Email ID successfully registered");
                            Toast.makeText(ctx, "Registered Successfully.\n" +
                                    "Email has been sent for verification ", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(Register.this, Login.class));
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            progressDialog.dismiss();
                            if (retrofitError.getResponse().getStatus() == 409) {
                                tv_validEmail.setTextColor(Color.RED);
                                tv_validEmail.setText("Email ID already exists");
                            }

                            Log.d("error", retrofitError.toString());
                            // Catch error here
                        }
                    };
                    Api.userInterface.createUser(user, callback);
                }else{
                    Toast.makeText(ctx,"Please accept the terms!",Toast.LENGTH_LONG).show();
                }
            }
        });

        tv_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setMessage(getString(R.string.termsText)
                        )
                        .setCancelable(true)
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {

                            public void onCancel(DialogInterface dialog) {
                                // TODO Auto-generated method stub
                                terms_accepted= false;

                            }
                        })
                        .setPositiveButton(getString(R.string.accept),new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                terms_accepted = true;

                            }
                        })
                        .show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
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
