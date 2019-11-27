package com.example.taewoo.testfirebase;

class Board {
    String title;
    String content;
    String name;

    Board(){}

    Board(String title, String content){
        this.title=title;
        this.content=content;
        this.name = name;
    }
    public String getTitle(){return title;}
    public String getContent(){return content;}
    public String getName(){return name;}


}
