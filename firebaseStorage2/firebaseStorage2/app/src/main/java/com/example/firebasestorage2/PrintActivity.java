package com.example.firebasestorage2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrintActivity extends AppCompatActivity {
    private String fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        Fresco.initialize(this);

        DatabaseReference mDatabaseref = FirebaseDatabase.getInstance().getReference("image");

        mDatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fileName = getIntent().getStringExtra("fileName");
                ImageUpload imageUpload = dataSnapshot.child(fileName).getValue(ImageUpload.class);
                Uri uri = Uri.parse(imageUpload.getUrl());

                SimpleDraweeView draweeView = findViewById(R.id.printImage);
                draweeView.setImageURI(uri);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
