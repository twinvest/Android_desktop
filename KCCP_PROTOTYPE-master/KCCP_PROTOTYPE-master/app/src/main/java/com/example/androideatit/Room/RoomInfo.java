package com.example.androideatit.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androideatit.Chat.Chatting;
import com.example.androideatit.Common.Common;
import com.example.androideatit.Model.Board;
import com.example.androideatit.Model.Favorite;
import com.example.androideatit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class RoomInfo extends AppCompatActivity {

    private Board board;

    final String myID = Common.getMyId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Recycler 에서 보낸 Board 정보
        board = (Board) getIntent().getSerializableExtra("INFO");
        final String boardID = getIntent().getStringExtra("BOARD_ID");

        setContentView(R.layout.activity_roominfo);

        // 각 버튼들과 바인딩
        LinearLayout chat = (LinearLayout) findViewById(R.id.chat);
        LinearLayout report = (LinearLayout) findViewById(R.id.report);


        // 즐겨찾기 버튼 초기화
        initFavorite(myID, boardID);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomInfo.this, Chatting.class);
                intent.putExtra("USER_NAME", board.getUserName());
                intent.putExtra("USER_ID", board.getUserId());
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportAlert();
            }
        });

        // xml과 바인딩
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        ImageView imageView = (ImageView) findViewById(R.id.imageview);

        textView1.setText(board.getTitle());
        textView2.setText(board.getContractType());

        Picasso.with(

                getApplicationContext()).

                load(board.getUri()).

                fit().

                centerInside().

                into(imageView);

    }

    // 신고 버튼 클릭 시 팝업 창 설정
    public void reportAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("신고");
        builder.setMessage("해당 게시글을 신고하시겠습니까?\n허위 신고는 제제를 받을 수 있습니다.");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "접수 되었습니다.", Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "취소 되었습니다.", Toast.LENGTH_LONG).show();
                    }
                });

        builder.show();
    }

    // 내 ID, 보드 ID --(입력)--> 즐겨찾기를 위한 초기화 작업
    private void initFavorite(final String myID, final String boardID) {

        final ImageView star = findViewById(R.id.star);

        // 즐겨 찾기 DB 참조
        final DatabaseReference scrapRef = Common.getDatabase("Scrap").child(myID).child(boardID);

        // 별 이미지
        final Drawable ON = getResources().getDrawable(R.drawable.star_on, getApplicationContext().getTheme());
        final Drawable OFF = getResources().getDrawable(R.drawable.star_off, getApplicationContext().getTheme());

        // 방 입장 시 원래 상태로 초기화
        scrapRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 보드 아이디가 존재 시
                if (dataSnapshot.exists())
                    star.setImageDrawable(ON);
                else
                    star.setImageDrawable(OFF);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        // 즐겨 찾기 클릭 시
        findViewById(R.id.scrap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrapRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {    // 즐겨찾기 해제
                            star.setImageDrawable(OFF);
                            dataSnapshot.getRef().removeValue();
                        } else {     // 즐겨 찾기
                            star.setImageDrawable(ON);
                            scrapRef.setValue(new Favorite(board.getLocation()));
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }


}