package com.livebettips.interfaces;


import com.livebettips.objects.Filter;
import com.livebettips.objects.Prediction;
import com.livebettips.objects.PredictionDetail;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface PredictionInterface {

    @GET("/predictions/?isPushed=True")
    void getPushedPredictions(Callback<List<Prediction>> callback);

    @GET("/user/{userID}/predictions/")
    void getPurchasedPredictions(@Path("userID") int userID,Callback<List<Prediction>> callback);

    @GET("/user/{userID}/prediction/{predictionID}")
    void getPredictionDetail(@Path("userID") int userID,@Path("predictionID") int predictionID,
                                 Callback<PredictionDetail> callback);

    @GET("/filter/")
    void getFilter(Callback<Filter> getFilter);

    @GET("/predictions/filter/")
    void filterPredictions(@Query("league") String league,@Query("predictionName") String predictionName,
                            Callback<List<Prediction>> callback);


}
