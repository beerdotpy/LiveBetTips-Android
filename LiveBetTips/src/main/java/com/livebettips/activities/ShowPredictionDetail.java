package com.livebettips.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.livebettips.R;
import com.livebettips.objects.Api;
import com.livebettips.objects.PredictionDetail;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ShowPredictionDetail extends ActionBarActivity {

    TextView league_type,team,message,name;
    int userId,predictionID;
    Context ctx;
    String leagueType,homeTeam,awayTeam;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        ctx = this;

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
      awayTeam = getIntent().getStringExtra("awayTeam");
      homeTeam = getIntent().getStringExtra("homeTeam");
      leagueType = getIntent().getStringExtra("leagueType");

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
                Toast.makeText(ctx,"Sorry! Prediction is paid. Please buy to view it",Toast.LENGTH_LONG).show();
                finish();


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.prediction_detail, menu);
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
