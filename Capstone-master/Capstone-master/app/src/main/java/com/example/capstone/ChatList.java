package com.example.capstone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.capstone.Common.Infomation;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChatList extends AppCompatActivity {

    private DatabaseReference chatRef = Infomation.getDatabase(Infomation.CHAT_INFOMAION);

    // 리스트뷰 길게 클릭 시 팝업창 생성을 위한 객체
    final Context context = this;

    // 사용자 이름름
    String myName = Infomation.getUserName(), myID = Infomation.getMyId();


    ArrayAdapter adapter;
    ArrayList<String> userList = new ArrayList<>();
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        // 사용자 채팅 리스트를 firebase에서 받아올 변수랑 연결
        final ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);

        // 채팅방 보여주기
        showListView();

        // 채팅방 들어가기
        listViewClick(listView);

    }


    public void alertListen(String title, String message) {
        alert = new AlertDialog.Builder(this);

        alert.setTitle(title);
        alert.setMessage(message);


        final EditText name = new EditText(this);
        alert.setView(name);


        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String username = name.getText().toString();

                for (String user : userList) {
                    if (username.equals(user)) {
                        Toast.makeText(getApplicationContext(), "채팅방이 존재합니다.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });

        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
    }

    // 채팅방 삭제
    public void deleteChat() {

    }

    public void listViewClick(ListView listView) {

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                Toast.makeText(getBaseContext(), "long~~~~~~~~~", Toast.LENGTH_SHORT).show();

                String user = userList.get(position);
                final String chatName = Infomation.integrate(user, myName);

                final CharSequence[] items = {"채팅방 이름 설정", "나가기"};
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);


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
                        Toast.makeText(getApplicationContext(),
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
                        getApplicationContext(), // 현재화면의 제어권자
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
                String opponentId = Infomation.getOther(dataSnapshot.getKey(), myID, ", ");
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