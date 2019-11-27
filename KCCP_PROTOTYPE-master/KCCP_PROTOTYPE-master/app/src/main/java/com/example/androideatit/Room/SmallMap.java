package com.example.androideatit.Room;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.androideatit.Common.Common;
import com.example.androideatit.Model.Board;
import com.example.androideatit.R;
import com.example.androideatit.Adapter.RoomAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class SmallMap extends AppCompatActivity {

    private TextView townTextView;
    private String townName;

    RoomAdapter adapter;
    final private DatabaseReference database = Common.getDatabase(Common.ROOM);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_small_map);

            townTextView = findViewById(R.id.townTextView);

            // 선택한 동네 이름으로 세팅
            townName = getIntent().getExtras().getString("townName");
            if (townName != null)
                townTextView.setText(townName);

            init();
            getData();
            findViewById(R.id.button_register).setOnClickListener(new View.OnClickListener() { //버튼 클릭했을시 사진첩 접근
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SmallMap.this, Register.class);
                    intent.putExtra("townName", townName);
                    startActivity(intent);
                }
            });


        } catch (Exception e) {
            Log.e("에러 원인 : ", e.getMessage());
        }
    }

    private void init() {  //리사이클러뷰 초기화 및 동작
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RoomAdapter(this);
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