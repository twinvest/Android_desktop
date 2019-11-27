package com.example.adult.hts2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class Send extends Thread{

    private Socket socket;

    private OutputStream os;

    private Object object = null;

    private ObjectOutputStream oos;

    public Send(Socket socket){
        try {
            this.socket = socket;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(Object object){
        this.object = object;
        this.start();
    }

    public void run(){
        try {
            os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);

            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
