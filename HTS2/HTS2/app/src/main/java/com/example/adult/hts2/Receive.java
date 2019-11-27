package com.example.adult.hts2;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receive extends Thread{

    private Socket socket;

    private InputStream is;

    private ObjectInputStream ois;

    private Object object;

    public Receive(Socket socket){
        try {
            this.socket = socket;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 반 영구적 대기
    public Object receive(){
        start();
        while(object == null){
        }
        Log.e("receive : ",(String)object);
        return object;
    }

    public void run(){
        try {
            object = null;
            is = socket.getInputStream();
            ois = new ObjectInputStream(is);
            object = ois.readObject();

            Log.e("안녕",(String)object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
