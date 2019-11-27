package com.example.recyclerviewstudy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatData> mDataset;
    private String myNickname;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    // 특정한 개수만큼 반복을 한다. Dataset의 크기만큼
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView Textview_nickname;
        public TextView Textview_msg;
        public View rootView;

        public MyViewHolder(View v) {
            super(v);
            // 왜 v에서 찾아가는가? 액티비티는 그 자체가 뷰기 때문에 v가 필요없음
            // 여기선 부모에서 찾아라이기 때문에 xml에서 어떤 요소를 찾을 때 부모에서 찾는다.
            Textview_nickname = v.findViewById(R.id.Textview_nickname);
            Textview_msg = v.findViewById(R.id.Textview_msg);
            rootView = v;
        }
    }

    // 어댑터를 최초 세팅하는 곳
    // Provide a suitable constructor (depends on the kind of dataset)
    // context를 넘기는 건 권장하지 않음. 메모리 누수가 된다. 하지만 난 초짜니 쓴다.
    public ChatAdapter(List<ChatData> myDataset, Context context, String nick) {
        mDataset = myDataset;
        myNickname = nick;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        // 액티비티에 통째로 화면이 넣는게 아님
        // 특정한 부분부분을 바꾸기 때문에 inflate를 사용
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // 여기서 데이터 세팅
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ChatData chat = mDataset.get(position);

        holder.Textview_nickname.setText(chat.getNickname());
        holder.Textview_msg.setText(chat.getMsg());

        if(chat.getNickname().equals(this.myNickname)){
            holder.Textview_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.Textview_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }
        else{
            holder.Textview_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.Textview_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public ChatData getChat(int position){
        return mDataset != null? mDataset.get(position) : null;
    }
    public void addChat(ChatData chat){
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1); // renewal
    }
}

