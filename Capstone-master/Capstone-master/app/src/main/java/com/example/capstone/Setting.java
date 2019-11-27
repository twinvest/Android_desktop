package com.example.capstone;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.capstone.Common.Infomation;


public class Setting extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final String myID = Infomation.getMyId();

        TextView idTextView = (TextView) findViewById(R.id.id);
        idTextView.setText(myID);
    }
}
