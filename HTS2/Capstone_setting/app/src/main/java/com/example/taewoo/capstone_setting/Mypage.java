package com.example.taewoo.capstone_setting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Mypage extends Fragment {
    public Mypage() {
        // Required empty public constructor
    }
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage, container, false);


        String id = Session.getAttribute(getContext(),"id");

        TextView idTextView = (TextView) view.findViewById(R.id.id);
        idTextView.setText(id);
        // Inflate the layout for this fragment
        return view;
    }
}