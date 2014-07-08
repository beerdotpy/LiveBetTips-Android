package com.livebettips.interfaces;


import com.livebettips.objects.Prediction;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface PredictionInterface {

    @GET("/predictions/?isPushed=True")
    void getPushedPredictions(Callback<List<Prediction>> callback);

    @GET("/user/{userID}/predictions/")
    void getPurchasedPredictions(@Path("userID") int userID,Callback<List<Prediction>> callback);

}
