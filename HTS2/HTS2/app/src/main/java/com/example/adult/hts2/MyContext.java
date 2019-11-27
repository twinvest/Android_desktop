package com.example.adult.hts2;

import android.content.Context;

public class MyContext {

    private static MyContext INSTANCE;
    private Context mContext;

    private MyContext(Context _context){
        mContext = _context;
    }

    public static MyContext getINSTANCE(Context _context) {
        if(INSTANCE == null){
            INSTANCE = new MyContext(_context.getApplicationContext());
        }
        return INSTANCE;
    }

    public Context getmContext() {
        return mContext;
    }
}
