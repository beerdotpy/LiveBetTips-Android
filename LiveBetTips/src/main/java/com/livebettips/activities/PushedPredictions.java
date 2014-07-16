package com.livebettips.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.livebettips.R;
import com.livebettips.adapters.PredictionAdapter;
import com.livebettips.objects.Api;
import com.livebettips.objects.Prediction;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PushedPredictions extends FragmentActivity {

    ListView lv_prediction;
    PredictionAdapter predictionAdapter;
    Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushedpredictions);

        ctx = this;

        Api.initSlidingMenu(ctx).attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        lv_prediction = (ListView) findViewById(R.id.lv_pushedprediction_prediction);

        Api.predictionInterface.getPushedPredictions(new Callback<List<Prediction>>() {

            @Override
            public void success(List<Prediction> predictions, Response response) {

                predictionAdapter = new PredictionAdapter(ctx,predictions);
                lv_prediction.setAdapter(predictionAdapter);

            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(ctx,"Please try again",Toast.LENGTH_SHORT).show();
            }
        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.predictions, menu);
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
