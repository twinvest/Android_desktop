package com.example.adult.hts2;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.net.Socket;



class Connect extends Thread{

    private String ip = "192.168.0.44";

    private int port = 8010;

    private Socket socket = null;

    public static Send sender;

    public static Receive receive;

    private Context context;

    private int connect = 0;

    public void close() throws IOException {
        socket.close();
        Log.e("연결 종료", "합니다.");
    }

    public Connect() {

    }

    public void run() {
        try {
            socket = new Socket(ip,port);

            sender = new Send(socket);
            receive = new Receive(socket);

            connect = 1;

        } catch (Exception e) {

            connect = -1;

            Log.e("에러는? ",e.getMessage());

        }
    }

    public int isConnect() {
        return connect;
    }
}
