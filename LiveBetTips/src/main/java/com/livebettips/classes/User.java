package com.livebettips.classes;


import com.livebettips.interfaces.UserInterface;

public class User {

    public String email;
    public String password;
    public static UserInterface userInterface;
    String API_URL= "http://178.21.172.107" ;
    public User(){

        RestAdapter userAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL+"/api")
                .build();

        this.userInterface = userAdapter.create(UserInterface.class);

    }

    public void setEmail(String email){

        this.email= email;
    }
    public void setPassword(String passwd){
        this.password = passwd;
    }
}
