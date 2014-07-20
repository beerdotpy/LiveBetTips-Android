package com.livebettips.objects;


public class User {

    private Integer id;
    private String email;
    private String password;
    private String deviceID;
    private String deviceType = "Android";

    public Integer getId(){
       return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setEmail(String email){

        this.email= email;
    }
    public String getEmail(){
        return email;
    }

    public void setPassword(String passwd){
        this.password = passwd;
    }

    public String getPassword(){
        return password;
    }

    public String getDeviceID(){
        return deviceID;
    }
    public void setDeviceID(String GCM_ID){
        this.deviceID = GCM_ID;
    }


}
