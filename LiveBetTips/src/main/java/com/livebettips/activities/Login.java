package com.livebettips.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.livebettips.R;
import com.livebettips.objects.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends ActionBarActivity {

    TextView tv_validEmail,tv_validPassword;
    EditText et_email,et_password;
    Button bt_login;
    String email,password;
    Boolean valid = false;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = (EditText) findViewById(R.id.et_login_email);
        et_password = (EditText) findViewById(R.id.et_login_password);
        tv_validEmail = (TextView) findViewById(R.id.tv_login_validEmail);
        tv_validPassword = (TextView) findViewById(R.id.tv_login_validPassword);
        bt_login = (Button) findViewById(R.id.bt_login_login);

        user = new User();

        bt_login.setEnabled(false);

        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                    email = et_email.getText().toString();
                    Matcher matcher = pattern.matcher(email);
                    if(!matcher.matches()){
                        tv_validEmail.setText("Please Enter a valid Email ID");
                        valid=false;
                    }else{
                        tv_validEmail.setText("");
                        valid=true;
                    }
                }
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (valid && s.length() > 5) {
                    user.setEmail(email);
                    user.setPassword(s.toString());
                    bt_login.setEnabled(true);
                    tv_validPassword.setText("");

                } else {
                    tv_validPassword.setText("Password should be greater than 5 characters");
                    bt_login.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
