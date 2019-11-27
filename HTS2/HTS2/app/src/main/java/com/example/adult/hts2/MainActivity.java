package com.example.adult.hts2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity/* implements View.OnClickListener*/ {

    EditText id;
    EditText pw;
    ImageButton loginButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //로그인 화면들과 객체와 바인딩
        id = (EditText) findViewById(R.id.id);
        pw = (EditText) findViewById(R.id.pw);
        loginButton = (ImageButton) findViewById(R.id.loginButton);

    }

    // Listener 두는 방법 과 xml에서 onclick을 설정하는 방법이 있음.
    public void loginClick(View view) {

        String loginId = id.getText().toString();
        String loginPw = pw.getText().toString();

        if((loginId).equals("k") && (loginPw).equals("k")){

            Session.setAttribute(MainActivity.this, "id",loginId);


            startActivity(new Intent(
                    getApplicationContext(), // 현재 화면의 제어권자
                    MainPage.class)); // 다음 화면으로 넘어간다
        }else{
            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
        }

/*        Connect connect = new Connect();
        connect.start();
        // 인터넷 연결 될 때 까지 대기
        while (0 == connect.isConnect()) {        }

        // 연결 완료시
        if (1 == connect.isConnect()) {

            List<String> login = new ArrayList<>();
            login.add("loginCheck");
            login.add(id.getText().toString());
            login.add(pw.getText().toString());

            *//*네트워크 연결하기*//*
            connect.sender.send(login);
            String result = (String)connect.receive.receive();

            if(result.equals("exist")){
                startActivity(new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        InvestMain.class)); // 다음 화면으로 넘어간다
            }else {
                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
            }

        } else if (-1 == connect.isConnect()) { // 연결 실패
            Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_LONG).show();
        }*/

    }
}


