package com.example.androideatit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.androideatit.WantRoom.ListActivity;
import com.example.androideatit.WantRoom.ListViewAdapter;
import com.example.androideatit.WantRoom.PrintActivity;
import com.example.androideatit.WantRoom.TimeString;
import com.example.androideatit.WantRoom.WriteActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WantRoomFragment extends Fragment {
    private ListView listView;
    private List<String> docIdlist = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListViewAdapter adapter;
    private FloatingActionButton writeButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_want_room, container, false);

        adapter = new ListViewAdapter();
        listView = view.findViewById(R.id.listVieww);
        writeButton = view.findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteActivity.class);
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
                Intent intent = new Intent(getActivity(), PrintActivity.class);
                intent.putExtra("docId", docIdlist.get(position));
                startActivity(intent);
            }
        });
        return view;
    }
}
