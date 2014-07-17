package com.livebettips.objects;


public class User {

    private Integer id;
    private String email;
    private String password;
    private String gcm_id;
    private String gcm_type = "Android";

    public Integer getId(){
       return id;
    }
    public void setEmail(String email){

        this.email= email;
    }
    public void setPassword(String passwd){
        this.password = passwd;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getGcm_id(){
        return gcm_id;
    }
    public void setGcm_id(String GCM_ID){
        this.gcm_id = GCM_ID;
    }


}
