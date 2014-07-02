package com.livebettips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.livebettips.interfaces.UserInterface;
import retrofit.RestAdapter;


public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


         mCountDown.start();

         RestAdapter restAdapter = new RestAdapter.Builder()
                                  .setEndpoint("http://178.21.172.107/api")
                                  .build();

          UserInterface userInterface = restAdapter.create(UserInterface.class);
    }

    protected CountDownTimer mCountDown = new CountDownTimer(3000, 1000)
    {

        @Override
        public void onTick(long millisUntilFinished)
        {

            Log.d("Timer","time for 3sec");

        }

        @Override
        public void onFinish()
        {
            Log.d("Timer","3sec finish");


            Intent register=new Intent(Home.this,Register.class);
            startActivity(register);

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
