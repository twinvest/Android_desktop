package com.example.adult.hts2;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MessageHandler extends Handler {
    Handler handler = null;


    public void post(View view){
        super.post(new Runnable() {
            View view = null;
            @Override
            public void run() {
                //TextView tv = (TextView)view.findViewById(R.id.textView);
            }
        });
    }


}
