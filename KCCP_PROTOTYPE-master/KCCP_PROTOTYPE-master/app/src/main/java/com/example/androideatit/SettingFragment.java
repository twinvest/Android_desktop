package com.example.androideatit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androideatit.Common.Common;

public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);


        LinearLayout infoChange = (LinearLayout) view.findViewById(R.id.info_change);
        LinearLayout appInfo = (LinearLayout) view.findViewById(R.id.app_info);


        infoChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.home_fragment, new InfoChangeFragment()).commit();
            }

        });

        appInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.home_fragment, new AppInfoFragment()).commit();
            }

        });

        return view;
    }


}
