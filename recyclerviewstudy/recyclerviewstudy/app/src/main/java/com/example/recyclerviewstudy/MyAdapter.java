package com.example.recyclerviewstudy;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NewsData> newsData;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    // 특정한 개수만큼 반복을 한다. Dataset의 크기만큼
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView_title;
        public TextView textView_description;
        public SimpleDraweeView imageView_title;

        public MyViewHolder(View v) {
            super(v);
            // 왜 v에서 찾아가는가? 액티비티는 그 자체가 뷰기 때문에 v가 필요없음
            // 여기선 부모에서 찾아라이기 때문에 xml에서 어떤 요소를 찾을 때 부모에서 찾는다.
            textView_title = v.findViewById(R.id.textView_title);
            textView_description = v.findViewById(R.id.textView_description);
            imageView_title = v.findViewById(R.id.imageView_title);
        }
    }

    // 어댑터를 최초 세팅하는 곳
    // Provide a suitable constructor (depends on the kind of dataset)
    // context를 넘기는 건 권장하지 않음. 메모리 누수가 된다. 하지만 난 초짜니 쓴다.
    public MyAdapter(List<NewsData> newsData, Context context) {
        this.newsData = newsData;
        //Adapter는 액티비티가 없어서 this만 쓸 수는 없음
        Fresco.initialize(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        // 액티비티에 통째로 화면이 넣는게 아님
        // 특정한 부분부분을 바꾸기 때문에 inflate를 사용
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // 여기서 데이터 세팅
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        NewsData news = newsData.get(position);
        holder.textView_title.setText(news.getTitle());
        String description = news.getDescription();
        if(description != null && description.length() > 0)
            holder.textView_description.setText(description);
        else
            holder.textView_description.setText("-");

        Uri uri = Uri.parse(news.getUrlToImage());
        holder.imageView_title.setImageURI(uri);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return newsData == null ? 0 : newsData.size();
    }
}

