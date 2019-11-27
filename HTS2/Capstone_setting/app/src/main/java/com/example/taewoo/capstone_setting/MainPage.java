package com.example.taewoo.capstone_setting;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainPage extends FragmentActivity {

    private Mypage mypage;
    //private Home home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mypage = new Mypage();
       //home = new Home();

        BottomBar bar = (BottomBar) findViewById(R.id.bottomBar);       //bottomBar는 activity_main_page.xml의 제일 밑에 있음~~

        bar.setOnTabSelectListener(new OnTabSelectListener() {               //리스너 두고
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                if(tabId == R.id.tab_mypage){              //바텀바의 tab_mypage누르면
                    transaction.replace(R.id.fragment_container, mypage).commit();  //여기로 ㄱㄱ mypage는 위에서 참조변수에 객체 생성해 놨당.
                }

 /*               else if(tabId == R.id.home){
                    transaction.replace(R.id.fragment_container, home).commit();

                }

                else if(tabId == R.id.tab_macro_setting){
                    transaction.replace(R.id.contentContainer, setMacroFragment).commit();

                }*/
            }
        });

    }
    /*public void initFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, home);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/
}