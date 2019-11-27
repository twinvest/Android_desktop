package com.example.androideatit.Room;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androideatit.Common.Common;
import com.example.androideatit.ObjectClickImageView;
import com.example.androideatit.R;
import com.example.androideatit.Room.SmallMap;

import java.util.Map;

public class MapFragment extends Fragment {

    final int[] townImages = Common.getTownImages();

    final String[] townNames = Common.getTownNames();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ObjectClickImageView[] town = new ObjectClickImageView[townImages.length];

        for (int i = 0; i < townImages.length; i++) {
            town[i] = view.findViewById(townImages[i]);
            final String townName = townNames[i];
            town[i].setOnObjectClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment smallMapFragment = new SmallMapFragment(); // Fragment 생성
                    Bundle bundle = new Bundle(); // 파라미터는 전달할 데이터 개수
                    bundle.putString("townName", townName); // key , value
                    smallMapFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.home_fragment, smallMapFragment).commit();

                }
            });
        }
        return view;
    }

}
