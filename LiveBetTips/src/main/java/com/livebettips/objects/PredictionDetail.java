package com.livebettips.objects;

public class PredictionDetail {

    private int id;
    private String message ;
    private String name;


    public int getid(){
        return id;
    }

    public void setid(int id){
        this.id = id;
    }

    public void setname(String name){
        this.name = name;
    }

    public void setmessage(String message){
        this.message = message;
    }

    public String getname(){
        return name;
    }
    public String  getmessage(){
        return message;
    }

}
