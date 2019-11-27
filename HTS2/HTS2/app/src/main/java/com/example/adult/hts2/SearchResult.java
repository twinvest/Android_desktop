package com.example.adult.hts2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SearchResult extends Fragment {

    public SearchResult() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        //Connect.receive.start();

        Button btn = new Button(view.getContext());
        btn.setText("자바코드로 버튼 만들기");
        btn.setTextColor(0xFFFF00);
        btn.setTextSize(30);

        LinearLayout linearLayout = view.findViewById(R.id.buttonAdd);

        linearLayout.addView(btn);

        return view;
    }

}
