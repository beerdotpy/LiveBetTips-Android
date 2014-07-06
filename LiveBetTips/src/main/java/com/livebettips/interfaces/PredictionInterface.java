package com.livebettips.interfaces;


import com.livebettips.objects.Prediction;

import retrofit.Callback;
import retrofit.http.GET;

public interface PredictionInterface {

    @GET("/predictions/?isPushed=True")
    void getpredictions(Callback<Prediction> callback);


}
