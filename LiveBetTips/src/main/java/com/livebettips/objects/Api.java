package com.livebettips.objects;

import com.livebettips.interfaces.UserInterface;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class Api {

    static String API_URL= "http://178.21.172.107" ;
    public static UserInterface userInterface;

    static{

        init();
    }

    static RestAdapter restAdapter;

    private static void init(){
        restAdapter = createRestAdapter(API_URL);
        userInterface = restAdapter.create(UserInterface.class);
    }

    private static RestAdapter createRestAdapter(String URL){

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URL+"/api")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                       request.addHeader("Content-Type","application/json");
                    }
                })
                .build();
        return  restAdapter;
    }

}

