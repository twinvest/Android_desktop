package com.example1.user.boardexam;

class Board {
    String title;
    String date;
    String content;
    int type;


    Board(){}
    Board(String title, String content){
        this.title=title;
        this.content=content;
    }

    Board(String title, String content, String date, int layouttype){
        this.title=title;
        this.content=content;
        this.date=date;
        this.type=layouttype;
    }
    public String getTitle(){return title;}
    public String getContent(){return content;}
    public String getDate(){return date;}
    public int getType(){return type;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public void setDate(String date) {

        this.date = date;
    }
}
