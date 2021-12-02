package com.example.chatapp2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp2.Model.Message;
import com.example.chatapp2.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final int MESSAGE_LEFT = 0;
    private static final int MESSAGE_RIGHT = 1;
    private Context mContext;
    private String uID;
    private ArrayList<Message> messages;


    public MessageAdapter(Context mContext, ArrayList<Message> messages) {
        this.mContext = mContext;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_RIGHT) {
            return new MessageAdapter.MessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false));
        } else {
            return new MessageAdapter.MessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageAdapter.MessageViewHolder holder, final int position) {
        holder.showMessage.setText(messages.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView showMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            showMessage = itemView.findViewById(R.id.show_message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        uID = FirebaseAuth.getInstance().getUid();
        if (messages.get(position).getSenderId().equals(uID)) {
            return MESSAGE_RIGHT;
        } else {
            return MESSAGE_LEFT;
        }
    }


}
