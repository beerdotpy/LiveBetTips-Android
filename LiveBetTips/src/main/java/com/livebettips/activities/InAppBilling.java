package com.livebettips.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.livebettips.R;
import com.livebettips.objects.Api;
import com.livebettips.util.IabHelper;
import com.livebettips.util.IabResult;
import com.livebettips.util.Inventory;
import com.livebettips.util.Purchase;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class InAppBilling extends ActionBarActivity {

    IabHelper mHelper;
    String TAG = "LiveBetTips";
    String key1 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwelLplECXOl8ddRu+C5zguREkOaUY3Pj/e6u2cpKwPF78HgRsfsKS0XTjNtskMMORQpfBHACR";
    String key2 = "ESWZ9r6DoimmwCaLCjRXJCorEvZVQYjiTP+YFS7nkRaIEtbrXO1ZR0qcvTfNBiJM1X+hqkbXlsuoLjp1gNi00xzis98au57ZYR8grKXvtq/jYQzSnJtP2";
    String key3 = "NAibCLmEW0CLlF63r+1yVsEMGMIoK3+cdji5uj25TZ4VQG2PwPUS9+m/ZgXVT9J038gaVk5QYieWg39ZfC5uGCBO4sy0LJg65LCeP5bajb1dhDb9DTKVs";
    String key4 = "L+w7F636lPHj87XsnSEBG2qmbm+6b6u4yJwIDAQAB";
    TextView tv_5creditPrice,tv_1creditPrice;
    Button bt_1credit,bt_5credit;
    ArrayList<String> SkuList;
    ProgressDialog progressDialog;
    Context ctx;
    Boolean mTaskisRunning = false;
    int userID,credit,creditID,value;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String credit_1="credit_1",credit_5="credit_5";
    HashMap hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_billing);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ctx = this;
        hashMap = new HashMap();

        prefs = ctx.getSharedPreferences("bettips", MODE_PRIVATE);
        editor = prefs.edit();
        userID = prefs.getInt("userID",0);

        hashMap.put("userID",userID);

        Api.initSlidingMenu(ctx).attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Fetching Credits");
        progressDialog.setIndeterminate(true);


        SkuList  = new ArrayList<String>();
        SkuList.add(credit_1);
        SkuList.add(credit_5);

        tv_1creditPrice = (TextView) findViewById(R.id.tv_app_billing_1price);
        tv_5creditPrice = (TextView) findViewById(R.id.tv_app_billing_5price);

        bt_1credit = (Button) findViewById(R.id.bt_app_billing_1credit);
        bt_5credit = (Button) findViewById(R.id.bt_app_billing_5credit);

        mHelper = new IabHelper(InAppBilling.this,key1+key2+key3+key4);
        mHelper.enableDebugLogging(true);

        mTaskisRunning = true;

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                    Toast.makeText(ctx,"There was problem setting purchases.Please try again",Toast.LENGTH_LONG).show();
                }else{
                    Log.d(TAG, "setting up In-app Billing: " + result);

                }
                // Hooray, IAB is fully set up!
                progressDialog.show();

                mHelper.queryInventoryAsync(true,SkuList,
                              mQueryFinishedListener);

            }
        });

         bt_1credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mHelper!=null) {
                    try {
                        if(!mTaskisRunning) {

                            mHelper.launchPurchaseFlow(InAppBilling.this, credit_1, 10001,
                                    mPurchaseFinishedListener, "1creditPurchased");
                            mTaskisRunning = true;
                        }
                    } catch (IllegalStateException e) {
                        Toast.makeText(ctx, "Please retry in a few seconds.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    Log.d("purchase", "1credit");
                }
                }
        });

        bt_5credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mHelper!=null) {
                    try {
                        if(!mTaskisRunning) {

                            mHelper.launchPurchaseFlow(InAppBilling.this, credit_5, 10002,
                                    mPurchaseFinishedListener, "1creditPurchased");
                            mTaskisRunning = true;
                        }
                    } catch (IllegalStateException e) {
                        Toast.makeText(ctx, "Please retry in a few seconds.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    Log.d("purchase", "5credit");
                }

            }
        });

    }

    IabHelper.QueryInventoryFinishedListener
            mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory)
        {
            if (result.isFailure()) {
                // handle error
                progressDialog.dismiss();
                Toast.makeText(ctx,result.getMessage(),Toast.LENGTH_LONG).show();
                finish();

            }


            String creditPrice_1 =
                    inventory.getSkuDetails(credit_1).getPrice();
            String creditPrice_5 =
                    inventory.getSkuDetails(credit_5).getPrice();

            tv_1creditPrice.setText("1 Credit cost " +creditPrice_1 );
            tv_5creditPrice.setText("5 Credits cost "+creditPrice_5);

            progressDialog.dismiss();
            mTaskisRunning = false;

            Log.d("Credit 5",creditPrice_1);
            Log.d("Credit 5",creditPrice_5);

                 // update the UI
        }
    };

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase)
        {
            progressDialog.setMessage("Buying Credits");
            progressDialog.show();
            if (result.isFailure()) {
                progressDialog.dismiss();
                Toast.makeText(ctx,"Purchase was cancelled",Toast.LENGTH_LONG).show();
                Log.d(TAG, "Error purchasing: " + result);
                return;
            }
            else if (purchase.getSku().equals("credit_1")) {
                Log.d("credit","1 purchased");
                mHelper.consumeAsync(purchase,
                        mConsumeFinishedListener);
                mTaskisRunning = false;
                value = 1;
                hashMap.put("credit",1);
                hashMap.put("creditID",1);

                // consume the gas and update the UI
            }
            else if (purchase.getSku().equals("credit_5")) {
                Log.d("credit","5 purchased");
                mHelper.consumeAsync(purchase,
                        mConsumeFinishedListener);
                mTaskisRunning = false;
                value = 5;
                hashMap.put("credit",5);
                hashMap.put("creditID",2);

                // give user access to premium content and update the UI
            }


            Api.userInterface.userbuyCredit(hashMap,new Callback<HashMap>() {
                @Override
                public void success(HashMap hashMap, Response response) {

                    progressDialog.dismiss();
                    editor.putInt("credit",prefs.getInt("credit",0)+value);
                    editor.commit();
                    Toast.makeText(ctx,"Credits purchased",Toast.LENGTH_LONG).show();

                }

                @Override
                public void failure(RetrofitError error) {

                    Toast.makeText(ctx,"Some error occured.Please try again",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                }
            });

        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase, IabResult result) {
                    if (result.isSuccess()) {
                        Log.d("credit",purchase.toString());
                        // provision the in-app purchase to the user
                        // (for example, credit 50 gold coins to player's character)
                    }
                    else {
                        // handle error
                    }
                }
            };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        if (mHelper == null) return;
        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {

            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app billing..
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            mTaskisRunning = false;
            Log.d("request",Integer.toString(requestCode));
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.in_app_billing, menu);
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
