package com.example.androideatit.Model;

public class Favorite {

    private String location;

    public Favorite(){

    }

    public Favorite(String location){
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
