package com.livebettips.interfaces;

import com.livebettips.objects.Profile;
import com.livebettips.objects.User;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface UserInterface {

    @POST("/user/")
     void createUser(@Body User user, Callback<User> cb);

    @POST("/user/login/")
     void userLogin(@Body User user,Callback<Profile> userCallback);

    @POST("/user/contactUs/")
    void userContact(@Body Map hashMap,Callback<Map> callback);
}
