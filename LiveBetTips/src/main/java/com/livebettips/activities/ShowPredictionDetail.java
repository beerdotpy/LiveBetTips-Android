package com.livebettips.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.livebettips.R;
import com.livebettips.objects.Api;
import com.livebettips.objects.PredictionDetail;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ShowPredictionDetail extends ActionBarActivity {

    TextView league_type,team,message,name;
    int userId,predictionID;
    Context ctx;
    String leagueType,homeTeam,awayTeam;
    ProgressDialog progressDialog;
    HashMap hashMap;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ctx = this;

        Api.initSlidingMenu(ctx).attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        hashMap =  new HashMap();
        prefs = ctx.getSharedPreferences("bettips",MODE_PRIVATE);
        editor = prefs.edit();

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading Prediction");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        league_type = (TextView) findViewById(R.id.tv_prediction_leaguetype);
        team = (TextView) findViewById(R.id.tv_prediction_match);
        message = (TextView) findViewById(R.id.tv_prediction_message);
        name = (TextView) findViewById(R.id.tv_prediction_name);

      userId = getIntent().getIntExtra("userID",-1);
      predictionID = getIntent().getIntExtra("predictionID",0);
      hashMap.put("userID",userId);
      hashMap.put("predictionID",predictionID);
      awayTeam = getIntent().getStringExtra("awayTeam");
      homeTeam = getIntent().getStringExtra("homeTeam");
      leagueType = getIntent().getStringExtra("leagueType");

        getPrediction();



    }


    void getPrediction(){

        Api.predictionInterface.getPredictionDetail(userId,predictionID,new Callback<PredictionDetail>(){

            @Override
            public void success(PredictionDetail predictionDetail, Response response) {

                league_type.setText(leagueType);
                team.setText(awayTeam + " VS " + homeTeam);
                name.setText(predictionDetail.getname());
                message.setText(predictionDetail.getmessage());
                progressDialog.dismiss();

            }

            @Override
            public void failure(RetrofitError error) {

                progressDialog.dismiss();
                buyprediction();

            }
        });
    }

    void buyprediction(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowPredictionDetail.this);
        builder.setMessage(getString(R.string.tip_buy))
                .setCancelable(true)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {

                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        finish();

                    }
                })
                .setPositiveButton("Purchase", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        Api.userInterface.buyPrediction(hashMap, new Callback<HashMap>() {
                            @Override
                            public void success(HashMap hashMap, Response response) {

                                editor.putInt("credit", prefs.getInt("credit", 0) - 1);
                                editor.commit();
                                getPrediction();

                            }

                            @Override
                            public void failure(RetrofitError error) {

                                try {
                                    if(error.getResponse().getStatus()==401){

                                    }

                                } catch (NullPointerException e) {
                                    Toast.makeText(ctx, "You dont have enough Credits", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(ShowPredictionDetail.this, InAppBilling.class));
                                    finish();
                                }


                            }
                        });


                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.prediction_detail, menu);
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
        finish();

    }

}
