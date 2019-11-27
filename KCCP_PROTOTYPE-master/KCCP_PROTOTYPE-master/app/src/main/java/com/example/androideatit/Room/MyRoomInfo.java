package com.example.androideatit.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androideatit.Common.Common;
import com.example.androideatit.Model.Board;
import com.example.androideatit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MyRoomInfo extends AppCompatActivity {

    private Board board;

    final String myID = Common.getMyId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_room_info);

        // Recycler 에서 보낸 Board 정보
        board = (Board) getIntent().getSerializableExtra("INFO");
        final String boardID = getIntent().getStringExtra("BOARD_ID");


        LinearLayout change = (LinearLayout) findViewById(R.id.change_board);
        LinearLayout delete = (LinearLayout) findViewById(R.id.delete_board);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Register.class);
                intent.putExtra("townName", board.getLocation());
                intent.putExtra("BOARD", board);
                startActivity(intent);
            }
        });

        //'삭제하기'버튼 클릭했을때 사진 삭제
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());

                alert.setTitle("게시글 삭제");
                alert.setMessage("게시글을 삭제하시겠습니까?\n한번 삭제하면 취소할 수 없습니다.");

                alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Create a reference to the file to delete
                        StorageReference desertRef = Common.getStorageRef().child("room/" + board.getFilename());
                        ;
                        // Delete the file
                        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            //사진 삭제 성공 하면 DB도 삭제
                            public void onSuccess(Void aVoid) {

                                // DB 삭제
                                DatabaseReference ref = Common.getDatabase(Common.ROOM).child(board.getLocation()).child(boardID);

                                ref.removeValue();

                                Toast.makeText(getApplicationContext(), "삭제 완료", Toast.LENGTH_LONG).show();

                                Log.e("삭제 완료 : ", board.getFilename());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.e("삭제 실패 : ", board.getFilename());
                            }
                        });

                        finish();


                    }
                });
                alert.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
                    }
                });

                alert.show();

            }
        });


        // xml과 바인딩
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        ImageView imageView = (ImageView) findViewById(R.id.imageview);

        textView1.setText(board.getTitle());
        textView2.setText(board.getContractType());

        Picasso.with(getApplicationContext()).load(board.getUri()).fit().centerInside().into(imageView);

    }
}
