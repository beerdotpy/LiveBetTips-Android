package com.livebettips.interfaces;

import com.livebettips.objects.Profile;
import com.livebettips.objects.User;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface UserInterface {

    @POST("/user/")
     void createUser(@Body User user, Callback<User> cb);

    @POST("/user/login/")
     void userLogin(@Body User user,Callback<Profile> userCallback);

    @POST("/user/contactUs/")
    void userContact(@Body HashMap hashMap,Callback<HashMap> callback);

    @POST("/user/logout/")
    void userLogout(@Body HashMap hasMAp,Callback<HashMap> callback);

    @POST("/user/resetpassword/")
    void userresetPassword(@Body HashMap hasMAp,Callback<HashMap> callback);

    @POST("/user/credit/buy/")
    void userbuyCredit(@Body HashMap hasMAp,Callback<HashMap> callback);

    @POST("/user/prediction/buy/")
    void buyPrediction(@Body HashMap hasMAp,Callback<HashMap> callback);


}
