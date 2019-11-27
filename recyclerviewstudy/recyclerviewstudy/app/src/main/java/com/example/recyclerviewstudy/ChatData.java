package com.example.recyclerviewstudy;

import java.io.Serializable;

public class ChatData implements Serializable {
    private String msg;
    private String nickname;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
