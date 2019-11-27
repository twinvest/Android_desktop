package com.example.capstone.Model;

public class ChatData {

    private String sender = "", message = "", receiver = "", time = "";

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public boolean isRelation(String user1, String user2){
        return receiver.equals(user1) && sender.equals(user2) || receiver.equals(user2) && sender.equals(user1);
    }

}

