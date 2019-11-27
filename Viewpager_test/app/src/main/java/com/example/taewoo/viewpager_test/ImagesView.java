package com.example.taewoo.viewpager_test;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ImagesView extends AppCompatActivity {
    ProgressDialog progressDialog;
    PagerAdapter pagerAdapter;
    ViewPager pager;
    ArrayList<Uri> priorList = new ArrayList<>();
    ArrayList<String> highList = new ArrayList<>();
    String flag = "";
    int count = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        priorList = getIntent().getParcelableArrayListExtra("list");
        //flag = priorList.get(0).substring(0,7);

        for(int i=0; i<priorList.size(); i++){
            if(flag.equals("content")){
                String path = getRealPathFromURI(priorList.get(i));
                //highList.add(path);
            } else{
                String path = priorList.get(i).toString().replace("file://", "");
                //highList.add(path);
            }
        }
        //pager.setOffscreenPageLimit(priorList.size()-1);
        pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return priorList.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View childView = getLayoutInflater().inflate(R.layout.viewpager_childview, null);
                final ImageView childImageView= (ImageView)childView.findViewById(R.id.img_viewpager_childimage);

                Uri uri = priorList.get(position);
                final String path = uri.getPath();

                //LoadBitmap loadBitmap 이 부분에 미친놈이 뭐 추가해놓음
                container.addView(childView);
                return childView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                //super.destroyItem(container, position, object);
                container.removeView((View)object);
            }

            @Override
            public void finishUpdate(@NonNull ViewGroup container) {

                super.finishUpdate(container);
                if(count == priorList.size()){
                    //progressDialog.dismiss();
                }else{
                    count++;
                }
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                //return false;
                return view == o;
            }
        };
        pager.setAdapter(pagerAdapter);
    }
    public String getRealPathFromURI(Uri contentUri) { String[] proj = { MediaStore.Images.Media.DATA }; Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null); cursor.moveToNext(); String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)); Uri uri = Uri.fromFile(new File(path)); Log.d("uri","getRealPathFromURI(), path : " + uri.toString()); cursor.close(); return path; }
}
