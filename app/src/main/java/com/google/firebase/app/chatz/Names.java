package com.google.firebase.app.chatz;

/**
 * Created by touseef on 1/29/2018.
 */

public class Names {
  String s1,s2;
    private int id;
    public Names(String s1,String s2){
        this.s1=s1;
        this.s2=s2;
    }
    public Names(String s1,String s2,int id){
        this.s1=s1;
        this.s2=s2;
        this.id=id;
    }

    public String getS1() {
        return s1;
    }

    public String getS2() {
        return s2;
    }
    public int  getImageResourceId(){
        return id;
    }
}
