package com.example.androideatit.WantRoom;

public class ListViewItem {
    private String title ;
    private String date ;
    private String genderPeriodPrice;
    private String location;

    public ListViewItem(String title, String date, String genderPeriodPrice, String location) {
        this.title = title;
        this.date = date;
        this.genderPeriodPrice = genderPeriodPrice;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGenderPeriodPrice() {
        return genderPeriodPrice;
    }

    public void setGenderPeriodPrice(String genderPeriodPrice) {
        this.genderPeriodPrice = genderPeriodPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
