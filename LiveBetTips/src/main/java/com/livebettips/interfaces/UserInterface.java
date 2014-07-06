package com.livebettips.interfaces;

import com.livebettips.objects.Profile;
import com.livebettips.objects.User;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface UserInterface {

    @POST("/user/")
     void createUser(@Body User user, Callback<User> cb);

    @POST("/user/login/")
     void userlogin(@Body User user,Callback<Profile> userCallback);


}
