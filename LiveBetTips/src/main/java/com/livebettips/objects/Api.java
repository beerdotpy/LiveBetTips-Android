package com.livebettips.objects;

import android.content.Context;
import android.content.SharedPreferences;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.livebettips.R;
import com.livebettips.interfaces.PredictionInterface;
import com.livebettips.interfaces.UserInterface;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class Api {

    static final String API_URL= "http://178.21.172.107" ;
    public static UserInterface userInterface;
    public static PredictionInterface predictionInterface;
    public static Context applicationContext;
    public static SlidingMenu slidingMenu;

    static{

        init();
    }

    static RestAdapter restAdapter;

    private static void init(){
        restAdapter = createRestAdapter(API_URL);
        userInterface = restAdapter.create(UserInterface.class);
        predictionInterface = restAdapter.create(PredictionInterface.class);
    }

    public static SlidingMenu initSlidingMenu(Context ctx){

        slidingMenu = new SlidingMenu(ctx);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.slidingmenu_shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setMenu(R.layout.slidingmenu);

        //  getActionBar().setDisplayHomeAsUpEnabled(true);

        return slidingMenu;

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
                            request.addHeader("Authorization","Basic "+authToken);
                        }
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return  restAdapter;
    }

}

