package com.example.androideatit.WantRoom;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androideatit.Common.Common;
import com.example.androideatit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PrintActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button delbtn;
    private TextView Viewtitle, Viewdate, Viewcontent, ViewGender, ViewPrice, ViewPeriod, ViewLocation;
    private String title, date, content, gender, period, price, location, id;
    private Board board;
    private Long time;
    private String docId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        Viewtitle = findViewById(R.id.Viewtitle);
        Viewdate = findViewById(R.id.Viewdate);
        Viewcontent = findViewById(R.id.Viewcontent);
        ViewGender = findViewById(R.id.Viewgender);
        ViewPeriod = findViewById(R.id.Viewperiod);
        ViewPrice = findViewById(R.id.Viewprice);
        ViewLocation = findViewById(R.id.ViewLocation);

        Intent getIntent = getIntent();
        docId = getIntent.getStringExtra("docId");
        db.collection("Board").document(docId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            title = document.getString("title");
                            time = document.getLong("date");
                            date = TimeString.formatTimeString(time);
                            content = document.getString("content");
                            gender = document.getString("gender");
                            period = document.getString("period");
                            price = document.getString("price");
                            location = document.getString("location");
                            id = document.getString("id");

                            board = new Board(title, content, time, period, price, location, gender, id);
                            if(Common.getMyId().equals(board.getId())) delbtn.setVisibility(View.VISIBLE);
                            Log.d("LOG", Common.getMyId()+ " " +board.getId());
                            Viewtitle.setText(title);
                            Viewdate.setText(date);
                            Viewcontent.setText(content);
                            ViewPeriod.setText(period);
                            ViewPrice.setText(price);
                            ViewGender.setText(gender);
                            ViewLocation.setText(location);
                        }
                    }
                });
        delbtn = findViewById(R.id.delete_btn);
        delbtn.setVisibility(View.INVISIBLE);
        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(PrintActivity.this);
                alertdialog.setTitle("삭제");
                alertdialog.setMessage("이대로 삭제하시겠습니까?");
                alertdialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("Board")
                                .document(docId)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(PrintActivity.this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(PrintActivity.this, ListActivity.class));
                                    }
                                });
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });
                alertdialog.show();
            }
        });

    }
}
