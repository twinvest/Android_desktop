package com.example.adult.hts2;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {




    private String id;
    private String pw;

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }



    @Override
    public void onCreate(){
        super.onCreate();


    }
}