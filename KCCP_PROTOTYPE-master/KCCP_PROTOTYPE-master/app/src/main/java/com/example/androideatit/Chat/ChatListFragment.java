package com.example.androideatit.Chat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androideatit.Common.Common;
import com.example.androideatit.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChatListFragment extends Fragment {

    private DatabaseReference chatRef = Common.getDatabase(Common.CHAT_INFOMAION);

    // 사용자 이름름
    String myName = Common.getUserName(), myID = Common.getMyId();


    ArrayAdapter adapter;
    ArrayList<String> userList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        // 사용자 채팅 리스트를 firebase에서 받아올 변수랑 연결
        final ListView listView = (ListView)view. findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);

        // 채팅방 보여주기
        showListView();

        // 채팅방 들어가기
        listViewClick(listView);

        return view;
    }



    public void listViewClick(ListView listView) {

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                Toast.makeText(getContext(), "long~~~~~~~~~", Toast.LENGTH_SHORT).show();

                String user = userList.get(position);
                final String chatName = Common.integrate(user, myName);

                final CharSequence[] items = {"채팅방 이름 설정", "나가기"};
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());


                // 제목셋팅
                alertDialogBuilder.setTitle(user);

                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int id) {

                        // 채팅방 나가기
//                        if (items[id].equals("나가기")) {
//                            databaseReference.child(chatName).removeValue();
//                        }


                        // 프로그램을 종료한다
                        Toast.makeText(getContext(),
                                items[id] + " 선택했습니다.",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();


                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(
                        getContext(), // 현재화면의 제어권자
                        Chatting.class); // 다음넘어갈 화면

                // 클릭한 user id를 넘긴다.
                intent.putExtra("USER_ID", adapter.getItem(position).toString());

                startActivity(intent);
            }
        });
    }


    public void showListView() {

        chatRef.addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // 상대방 채팅자 id 구함.
                String opponentId = Common.getOther(dataSnapshot.getKey(), myID, ", ");
                // null 이면 나랑의 채팅은 아닌 것
                if (opponentId != null) {
                    userList.add(opponentId);
                    adapter.add(opponentId);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
