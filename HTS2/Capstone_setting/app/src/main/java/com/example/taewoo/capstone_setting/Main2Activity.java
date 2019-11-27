package com.example.taewoo.capstone_setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    EditText id;
    EditText pw;
    ImageButton loginButton;

    @Override
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

        if ((loginId).equals("kimtaewoo") && (loginPw).equals("123")) {

            Session.setAttribute(Main2Activity.this, "id", loginId);
            Log.d("kimtaewoo", "무엇");
            startActivity(new Intent(
                    getApplicationContext(), // 현재 화면의 제어권자
                    MainPage.class)); // 다음 화면인 MainPage 넘어간다
        } else {
            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
        }
    }
}