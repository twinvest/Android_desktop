package com.example.adult.hts2;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainPage extends FragmentActivity {

    private Mypage mypage;
    private Home home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mypage = new Mypage();
        home = new Home();

        BottomBar bar = (BottomBar) findViewById(R.id.bottomBar);

        bar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                if(tabId == R.id.tab_mypage){
                    transaction.replace(R.id.fragment_container, mypage).commit();
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
