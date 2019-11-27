package com.example.androideatit.WantRoom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androideatit.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();

    public ListViewAdapter(){}
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView dateTextView = (TextView) convertView.findViewById(R.id.textView2) ;
        TextView genderPeriodPriceTextView = convertView.findViewById(R.id.genderPeriodPrice);
        TextView locationTextView = convertView.findViewById(R.id.location);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem item = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영

        titleTextView.setText(item.getTitle());
        dateTextView.setText(item.getDate());
        genderPeriodPriceTextView.setText(item.getGenderPeriodPrice());
        locationTextView.setText(item.getLocation());

        return convertView;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String title, String date, String genderPeriodPrice, String location) {
        ListViewItem item = new ListViewItem(title, date, genderPeriodPrice, location);

        listViewItemList.add(item);
    }
}
