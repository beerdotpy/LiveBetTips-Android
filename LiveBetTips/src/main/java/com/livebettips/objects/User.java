package com.livebettips.objects;


public class User {

    private Integer id;
    private String email;
    private String password;
    private String GCM_ID;

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
    public String getGCM_ID(){
        return GCM_ID;
    }
    public void setGCM_ID(String GCM_ID){
        this.GCM_ID = GCM_ID;
    }


}
