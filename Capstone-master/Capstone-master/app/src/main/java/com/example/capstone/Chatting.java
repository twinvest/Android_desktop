package com.example.capstone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.capstone.Common.Infomation;
import com.example.capstone.Model.ChatData;
import com.example.capstone.ViewHolder.MessageAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Chatting extends AppCompatActivity {

    private MessageAdapter messageAdapter;
    private List<ChatData> chatList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        // 상대바
        final String opponentID = getIntent().getExtras().getString("USER_ID");
        final String myID = Infomation.getMyId();

        final String chatName = Infomation.integrate(myID, opponentID);

        final DatabaseReference contentsRef = Infomation.getDatabase(Infomation.CHAT_INFOMAION).child(chatName);

        recyclerView = findViewById(R.id.listView2);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 전송 버튼 클릭 시 메세지 전송
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText write = (EditText) findViewById(R.id.writeEdit);

                ChatData chatData = new ChatData();
                chatData.setSender(myID); // 내 아이디
                chatData.setReceiver(opponentID); // 상대방 아이디
                chatData.setMessage(write.getText().toString()); // 메세지
                chatData.setTime(Infomation.chatTimeStamp()); // 전송 시간

                contentsRef.push().setValue(chatData); // DB에 저장
                write.setText(""); // 전송 후 초기화
            }
        });

        // 메세지 도착 시 업데이트
        // addValueEventListener 변경 및 추가 시 Child DB 전부 돌려줌.
        // addChildEventListener 하위에 추가된 DB 넘겨준다.
        contentsRef.addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chat = dataSnapshot.getValue(ChatData.class);  // chatData를 가져오고
                // 받아온 내용의 Sender, Receiver 과 채팅방 id 들과 같아야 한다.
                if (chat.isRelation(myID, opponentID)) {
                    chatList.add(chat);
                    messageAdapter = new MessageAdapter(getApplicationContext(), chatList);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
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
