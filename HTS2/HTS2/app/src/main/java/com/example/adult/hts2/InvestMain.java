/*
package com.example.adult.hts2;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class InvestMain extends AppCompatActivity implements View.OnClickListener {

    //FragmentManager fragmentManager = null;
    //Fragment를 제어하기 위한 매니져 객체

    private final int port = 8888;

    private ArrayList<Integer> list = new ArrayList<>();

    private final int stock = 1; //주식
    private final int futures = 2; //해외선물
    private final int bitcoin = 3; //비트코인
    private final int mypage = 4; //마이페이지
    private final int search = 5; //마이페이지



    private ImageButton stockButton, futuresButton, bitcoinButton, mypageButton,
    searchButton;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest_main);

        //xml에 있는 버튼을 자바 객체에 연결
        stockButton = (ImageButton) findViewById(R.id.stock);
        futuresButton = (ImageButton) findViewById(R.id.futures);
        bitcoinButton = (ImageButton) findViewById(R.id.bitcoin);
        mypageButton = (ImageButton) findViewById(R.id.mypage);
        searchButton = (ImageButton) findViewById(R.id.searchButton);

        // 탭 버튼에 대한 리스너 연결
        stockButton.setOnClickListener(this);
        futuresButton.setOnClickListener(this);
        bitcoinButton.setOnClickListener(this);
        mypageButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);

        //제일 처음 켜졌을 때 불릴 Fragment
        callFragment(mypage);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.stock :
                // '버튼1' 클릭 시 '프래그먼트1' 호출
                callFragment(stock);
                break;
            case R.id.futures :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(futures);
                break;
            case R.id.bitcoin :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(bitcoin);
                break;

            case R.id.mypage :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(mypage);
                break;
            case R.id.searchButton :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(search);
                break;
        }
    }

    private void callFragment(int frament_no) {

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no) {
            case 1:
                // '프래그먼트1' 호출
                Home stock = new Home();
                transaction.replace(R.id.fragment_container, stock).commit();
                break;
            case 2:
                // '프래그먼트2' 호출
                Futures futures = new Futures();
                transaction.replace(R.id.fragment_container, futures).commit();
                break;
            case 3:
                // '프래그먼트3' 호출
                Bitcoin bitcoin = new Bitcoin();
                transaction.replace(R.id.fragment_container, bitcoin).commit();
                break;
            case 4:
                // '프래그먼트4' 호출
                Mypage mypage = new Mypage();
                transaction.replace(R.id.fragment_container, mypage).commit();
                break;
            case search :
                SearchResult searchResult = new SearchResult();

                EditText search_bar = findViewById(R.id.search_bar);
                String kwd = search_bar.getText().toString();

                Log.e("search bar : ",kwd);

                List<String> list = new ArrayList<>();
                list.add("search");
                list.add(kwd);

                //Log.e("list : ",list);
                Connect.sender.send(list);

                transaction.replace(R.id.fragment_container, searchResult).commit();
                break;
            default:
                break;
        }
    }


}


*/
