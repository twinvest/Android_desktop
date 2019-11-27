package com.example.androideatit;


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
import android.widget.Toast;

import com.example.androideatit.Adapter.RoomAdapter;
import com.example.androideatit.Common.Common;
import com.example.androideatit.Model.Board;
import com.example.androideatit.Model.Favorite;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MyScrapFragment extends Fragment {


    private RoomAdapter adapter;

    private final String myID = Common.getMyId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_my_scrap, container, false);
        Toast.makeText(getContext(), "????",Toast.LENGTH_LONG);
        Log.e("sSS","SSS");
        init(view);
        getData();
        return view;
    }


    private void init(View view) {  //리사이클러뷰 초기화 및 동작
        RecyclerView recyclerView = view.findViewById(R.id.scrap);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RoomAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }

    private void getData() {

        final DatabaseReference scrapRef = Common.getDatabase(Common.SCRAP).child(myID);
        final DatabaseReference roomRef = Common.getDatabase(Common.ROOM);

        // scrap - id 까지 참조. key : boardId | value : location
        scrapRef.addChildEventListener(new ChildEventListener() {
                                           @Override
                                           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                               final String boardID = dataSnapshot.getKey();
                                               final Favorite loaction = dataSnapshot.getValue(Favorite.class);
                                               roomRef.child(loaction.getLocation()).child(boardID).addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                       Board board = dataSnapshot.getValue(Board.class);
                                                       adapter.add(board, dataSnapshot.getKey());
                                                       adapter.notifyDataSetChanged();
                                                   }

                                                   @Override
                                                   public void onCancelled(@NonNull DatabaseError databaseError) {
                                                       Log.e("취소 에러 확인 : ", databaseError.toString());

                                                   }
                                               });


                                           }

                                           @Override
                                           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                               adapter.notifyDataSetChanged();
                                           }

                                           @Override
                                           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                                               final String boardID = dataSnapshot.getKey();
                                               adapter.remove(boardID);
                                               adapter.notifyDataSetChanged();
                                           }

                                           @Override
                                           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                               adapter.notifyDataSetChanged();
                                           }

                                           @Override
                                           public void onCancelled(@NonNull DatabaseError databaseError) {
                                               Log.e("취소 에러 확인 : ", databaseError.getMessage());
                                           }
                                       }
        );
    }


}
