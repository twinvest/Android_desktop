package com.example.capstone.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstone.Common.Infomation;
import com.example.capstone.Model.ChatData;
import com.google.firebase.auth.FirebaseUser;
import com.example.capstone.R;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<ChatData> chatList;
    private final String myID = Infomation.getMyId();

    FirebaseUser firebaseUser;

    public MessageAdapter(Context mContext, List<ChatData> mChat) {
        this.chatList = mChat;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, viewGroup, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, viewGroup, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ChatData chat = chatList.get(i);
        viewHolder.show_message.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public CircleImageView profile_image;

        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = (CircleImageView) itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // message sender
        String sender = chatList.get(position).getSender();
        Log.e("sender : ", sender);
        Log.e("my id : ", myID);
        Log.e("sender == id : ", myID.equals(chatList.get(position).getSender()) + "");
        if (sender.equals(myID))
            return MSG_TYPE_RIGHT;
        return MSG_TYPE_LEFT;


    }
}
