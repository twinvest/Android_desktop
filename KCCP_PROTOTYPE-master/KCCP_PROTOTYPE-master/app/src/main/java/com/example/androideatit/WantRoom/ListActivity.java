package com.example.androideatit.WantRoom;

import com.example.androideatit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> docIdlist = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListViewAdapter adapter;
    private Button writeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        adapter = new ListViewAdapter();
        listView = findViewById(R.id.listVieww);
        writeButton = findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });
        db.collection("Board")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()){
                                String title = document.getString("title");
                                Long time = document.getLong("date");
                                String date = TimeString.formatTimeString(time);
                                String info = document.getString("gender") + "/";
                                info += document.getString("period") + "/";
                                info += document.getString("price");
                                String location = document.getString("location");
                                adapter.addItem(title, date, info, location);
                                listView.setAdapter(adapter);
                                docIdlist.add(document.getId());
                            }
                        }
                    }
                });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("LOG", docIdlist.get(position));
                Intent intent = new Intent(ListActivity.this, PrintActivity.class);
                intent.putExtra("docId", docIdlist.get(position));
                startActivity(intent);
            }
        });
    }
}
