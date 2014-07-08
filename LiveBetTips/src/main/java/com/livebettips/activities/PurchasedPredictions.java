package com.livebettips.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.livebettips.R;
import com.livebettips.adapters.PredictionAdapter;
import com.livebettips.objects.Api;
import com.livebettips.objects.Prediction;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PurchasedPredictions extends ActionBarActivity {

    ListView lv_prediction;
    PredictionAdapter predictionAdapter;
    Context ctx;
    SharedPreferences prefs;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_predictions);

        ctx = this;
        prefs = ctx.getSharedPreferences("bettips",MODE_PRIVATE);

        userID = prefs.getInt("userID",-1);


        lv_prediction = (ListView) findViewById(R.id.lv_purchased_predictions_predictions);

        Api.predictionInterface.getPurchasedPredictions(userID, new Callback<List<Prediction>>() {

            @Override
            public void success(List<Prediction> predictions, Response response) {

                predictionAdapter = new PredictionAdapter(ctx, predictions);
                lv_prediction.setAdapter(predictionAdapter);

            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(ctx, "Please try again", Toast.LENGTH_SHORT).show();
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.purchased_predictions, menu);
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
