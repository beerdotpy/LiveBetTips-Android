package com.livebettips.interfaces;


import com.livebettips.objects.Prediction;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface PredictionInterface {

    @GET("/predictions/?isPushed=True")
    void getPushedPredictions(Callback<List<Prediction>> callback);

}
