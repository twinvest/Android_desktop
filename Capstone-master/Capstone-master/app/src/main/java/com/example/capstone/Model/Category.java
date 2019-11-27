package com.example.capstone.Model;

public class Category {
    private String Name;
    private String Image;

    public Category(){}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Category(String Name, String Image){
        this.Name = Name;
        this.Image = Image;
    }
}
