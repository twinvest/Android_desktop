package com.example.adult.hts2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class Home extends Fragment {

    ArrayList<TextView> textViews = new ArrayList<>();


    public Home() {
        // Required empty public constructor
    }

    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);

            textViews.add((TextView) view.findViewById(R.id.price1));
            textViews.add((TextView) view.findViewById(R.id.price2));
            textViews.add((TextView) view.findViewById(R.id.price3));
            textViews.add((TextView) view.findViewById(R.id.price4));
            textViews.add((TextView) view.findViewById(R.id.price5));
            textViews.add((TextView) view.findViewById(R.id.price6));
            textViews.add((TextView) view.findViewById(R.id.price7));
            textViews.add((TextView) view.findViewById(R.id.price8));
            textViews.add((TextView) view.findViewById(R.id.price9));
            textViews.add((TextView) view.findViewById(R.id.price10));
            textViews.add((TextView) view.findViewById(R.id.price11));


            //Connect connect = new Connect(textViews);
            //connect.start();

        // Inflate the layout for this fragment
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stock:

        }
    }


}

