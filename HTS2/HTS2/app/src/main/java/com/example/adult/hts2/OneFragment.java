package com.example.adult.hts2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class OneFragment extends Fragment{

    //View mView;
    TextView mTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        //mTv =  view.findViewById(R.id.textView);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
    }

    public void setTextChange(String text){
        mTv.setText(text);
    }

    public void setActivityChange(){
       // Button btn = (Button)getActivity().findViewById(R.id.btn_change);
       // btn.setText("button change");
    }
}
