package com.example.androideatit.Room;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androideatit.Adapter.RoomAdapter;
import com.example.androideatit.Common.Common;
import com.example.androideatit.Model.Board;
import com.example.androideatit.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class SmallMapFragment extends Fragment {

    private TextView townTextView;
    private String townName;

    RoomAdapter adapter;
    final private DatabaseReference database = Common.getDatabase(Common.ROOM);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_small_map, container, false);
        try {
            townTextView = view.findViewById(R.id.townTextView);

            if(getArguments() != null)
            // 선택한 동네 이름으로 세팅
            townName = getArguments().getString("townName"); // 전달한 key 값
            else
                townName = "비 정상적인 접근";

            if (townName != null)
                townTextView.setText(townName);

            init(view);
            getData();
            view.findViewById(R.id.button_register).setOnClickListener(new View.OnClickListener() { //버튼 클릭했을시 사진첩 접근
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), Register.class);
                    intent.putExtra("townName", townName);
                    startActivity(intent);
                }
            });


        } catch (Exception e) {
            Log.e("에러 원인 : ", e.getMessage());
        }
        return view;
    }


    private void init(View view) {  //리사이클러뷰 초기화 및 동작
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RoomAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        database.child(townName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Board board = dataSnapshot.getValue(Board.class);
                adapter.add(board, dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // listview 갱신.
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                adapter.remove(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();     // listview 갱신
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}
