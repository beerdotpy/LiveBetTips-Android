package com.livebettips.objects;

import android.content.Context;
import android.content.SharedPreferences;

import com.livebettips.interfaces.PredictionInterface;
import com.livebettips.interfaces.UserInterface;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class Api {

    static final String API_URL= "http://178.21.172.107" ;
    public static UserInterface userInterface;
    public static PredictionInterface predictionInterface;
    public static Context applicationContext;

    static{

        init();
    }

    static RestAdapter restAdapter;

    private static void init(){
        restAdapter = createRestAdapter(API_URL);
        userInterface = restAdapter.create(UserInterface.class);
        predictionInterface = restAdapter.create(PredictionInterface.class);
    }

    private static RestAdapter createRestAdapter(String URL){

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URL+"/api")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Content-Type", "application/json");

                        SharedPreferences prefs = applicationContext.getSharedPreferences("bettips",Context.MODE_PRIVATE);
                        String authToken = prefs.getString("authToken","NA");
                        if(!authToken.contentEquals("NA")){
                            request.addHeader("Authorization","Basi "+authToken);
                        }
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return  restAdapter;
    }

}

