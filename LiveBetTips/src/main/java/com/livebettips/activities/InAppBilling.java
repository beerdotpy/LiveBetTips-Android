package com.livebettips.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.livebettips.R;
import com.livebettips.util.IabHelper;
import com.livebettips.util.IabResult;
import com.livebettips.util.Inventory;

import java.util.ArrayList;
import java.util.List;


public class InAppBilling extends ActionBarActivity {

    IabHelper mHelper;
    String key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwelLplECXOl8ddRu+C5zguREkOaUY3Pj/e6u2cpKwPF78HgRsfsKS0XTjNtskMMORQpfBHACRESWZ9r6DoimmwCaLCjRXJCorEvZVQYjiTP+YFS7nkRaIEtbrXO1ZR0qcvTfNBiJM1X+hqkbXlsuoLjp1gNi00xzis98au57ZYR8grKXvtq/jYQzSnJtP2NAibCLmEW0CLlF63r+1yVsEMGMIoK3+cdji5uj25TZ4VQG2PwPUS9+m/ZgXVT9J038gaVk5QYieWg39ZfC5uGCBO4sy0LJg65LCeP5bajb1dhDb9DTKVsL+w7F636lPHj87XsnSEBG2qmbm+6b6u4yJwIDAQAB";
    String TAG = "LiveBetTips";
    List additionalSkuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_billing);

        additionalSkuList = new ArrayList();
        additionalSkuList.add("test2");
        additionalSkuList.add("credit_1");
        additionalSkuList.add("credit_5");


        mHelper = new IabHelper(this,key);
        mHelper.enableDebugLogging(true);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                }else{
                    Log.d(TAG, "setting up In-app Billing: " + result);
                }
                // Hooray, IAB is fully set up!

                mHelper.queryInventoryAsync(true, additionalSkuList,
                        mQueryFinishedListener);

            }
        });
    }


    IabHelper.QueryInventoryFinishedListener
            mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory)
        {
            if (result.isFailure()) {
                // handle error
                return;
            }

            Log.d("result",result.toString());

            String credit_1_Price =
                    inventory.getSkuDetails("test2").getPrice();
            String credit_2_Price =
                    inventory.getSkuDetails("credit_5").getPrice();
            String credit_3_Price =
                    inventory.getSkuDetails("credit_1").getPrice();
            Log.d("Credit 1",credit_1_Price);
            Log.d("Credit 5",credit_2_Price);
            Log.d("Credit 5",credit_3_Price);

            // update the UI
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
//
//        // Pass on the activity result to the helper for handling
//        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
//            // not handled, so handle it ourselves (here's where you'd
//            // perform any handling of activity results not related to in-app billing..
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//        else {
//            Log.d(TAG, "onActivityResult handled by IABUtil.");
//        }
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.in_app_billing, menu);
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
