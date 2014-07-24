package com.livebettips.activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.livebettips.R;
import com.livebettips.adapters.PredictionAdapter;
import com.livebettips.fragments.FilterFragment;
import com.livebettips.objects.Api;
import com.livebettips.objects.Filter;
import com.livebettips.objects.Prediction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PushedPredictions extends ActionBarActivity implements FilterFragment.DialogListener{

    ListView lv_prediction;
    PredictionAdapter predictionAdapter;
    List<Prediction> prediction1;
    Context ctx;
    Boolean isLoggedIn;
    int userID,credit;
    SharedPreferences prefs;
    ProgressDialog progressDialog;
    TextView tv_filter,tv_credit;
    List<String> league = new ArrayList<String>();
    List<String> predictionName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushedpredictions);

        ctx = this;

        tv_filter = (TextView) findViewById(R.id.tv_header_filter);
        tv_credit = (TextView) findViewById(R.id.tv_header_credits);

        Api.initSlidingMenu(ctx).attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Fetching Predictions ...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        prefs = ctx.getSharedPreferences("bettips",MODE_PRIVATE);

        userID = prefs.getInt("userID",0);
        isLoggedIn = prefs.getBoolean("isLoggedIn",false);
        credit = prefs.getInt("credit",0);


        tv_credit.setText(Integer.toString(credit)+" Credits");

        lv_prediction = (ListView) findViewById(R.id.lv_pushedprediction_prediction);

        lv_prediction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isLoggedIn) {
                    Intent showPrediction = new Intent(PushedPredictions.this, ShowPredictionDetail.class);
                    showPrediction.putExtra("predictionID", prediction1.get(position).getID());
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

        tv_filter.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                DialogFragment newFragment = new FilterFragment(league,predictionName);
                newFragment.show(ft,"sda");


            }
        });

        Api.predictionInterface.getPushedPredictions(new Callback<List<Prediction>>() {

            @Override
            public void success(List<Prediction> predictions, Response response) {

                Api.predictionInterface.getFilter(new Callback<Filter>() {
                    @Override
                    public void success(Filter filter, Response response) {

                        league = filter.getLeague();
                        league.add(0,"ALL");
                        predictionName = filter.getPredictionName();
                        predictionName.add(0,"ALL");
                    }
                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(ctx,"Some error occured.Please try again",Toast.LENGTH_LONG).show();

                    }
                });

                Collections.reverse(predictions);
                prediction1 = predictions;
                predictionAdapter = new PredictionAdapter(ctx,predictions);
                lv_prediction.setAdapter(predictionAdapter);
                progressDialog.dismiss();

            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(ctx,"Please try again",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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

    @Override
    public void onFinishEditDialog(String league,String prediction) {

        progressDialog.setMessage("Filtering...");
        progressDialog.show();

        if(league.contentEquals("ALL") && prediction.contentEquals("ALL")){
            progressDialog.dismiss();
            return ;
        }else if(league.contentEquals("ALL")){
                  league = "null";
        }else if(prediction.contentEquals("ALL")) {
                   prediction = "null";
        }

        Api.predictionInterface.filterPredictions(league,prediction, new Callback<List<Prediction>>() {
            @Override
            public void success(List<Prediction> predictions, Response response) {

                Collections.reverse(predictions);
                prediction1 = predictions;
                predictionAdapter = new PredictionAdapter(ctx,predictions);
                lv_prediction.setAdapter(predictionAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(ctx,"Some error occured.Please try again!",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        });




        }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent back = new Intent(this,PushedPredictions.class);
        finish();
        startActivity(back);
    }


}

