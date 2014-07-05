package com.livebettips.objects;


public class User {

    private Integer id;
    private String email;
    private String password;

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


}
