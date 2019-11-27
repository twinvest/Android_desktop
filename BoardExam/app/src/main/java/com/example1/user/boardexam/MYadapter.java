package com.example1.user.boardexam;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MYadapter extends BaseAdapter {
    Context context;
    List<Board> data = new ArrayList<Board>();
    MYadapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {  //파이어베이스가 고정이 아니니 당연히 이것도 고정값이 되어선 안된다. 태우야 잊지마셈.
        return data.size();
    }

    @Override
    public Object getItem(int position) { //아이템의 위치에 해당하는 데이터의 객체를 반환
        return data.get(position);
    }

    @Override
    public long getItemId(int position) { //아이템의 위치에 해당하는 id를 반환하는 메소드
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {  //실제 아이템의 레이아웃을 구현하고 리스트뷰에 아이템을 배치하는 역할을 한다. 제일 복잡. 그리고 레이아웃은 내가 구현해줘야 함.
        // 한 아이템당 한 번 호출 됨. 만약 data에 10개 들어있다면 10번 호출.
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
