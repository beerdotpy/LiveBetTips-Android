package com.livebettips.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.livebettips.R;
import com.livebettips.adapters.PredictionAdapter;
import com.livebettips.objects.Api;
import com.livebettips.objects.Prediction;

import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PushedPredictions extends FragmentActivity {

    ListView lv_prediction;
    PredictionAdapter predictionAdapter;
    List<Prediction> prediction1;
    Context ctx;
    Boolean isLoggedIn;
    int userID;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushedpredictions);

        ctx = this;

        Api.initSlidingMenu(ctx).attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        prefs = ctx.getSharedPreferences("bettips",MODE_PRIVATE);

        userID = prefs.getInt("userID",-1);
        isLoggedIn = prefs.getBoolean("isLoggedIn",false);


        lv_prediction = (ListView) findViewById(R.id.lv_pushedprediction_prediction);

        lv_prediction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isLoggedIn) {
                    Intent showPrediction = new Intent(PushedPredictions.this, ShowPredictionDetail.class);
                    showPrediction.putExtra("predictionID", prediction1.get(position).getId());
                    showPrediction.putExtra("leagueType",prediction1.get(position).getLeagueType());
                    showPrediction.putExtra("homeTeam",prediction1.get(position).getHomeTeam());
                    showPrediction.putExtra("awayTeam",prediction1.get(position).getAwayTeam());
                    showPrediction.putExtra("userID", userID);
                    startActivity(showPrediction);
                }else{
                    Toast.makeText(ctx,"Login is required to view the tip",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Api.predictionInterface.getPushedPredictions(new Callback<List<Prediction>>() {

            @Override
            public void success(List<Prediction> predictions, Response response) {

                Collections.reverse(predictions);
                prediction1 = predictions;
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
