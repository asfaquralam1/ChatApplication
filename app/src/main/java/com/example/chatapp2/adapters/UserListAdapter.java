package com.example.chatapp2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp2.MessageActivity;
import com.example.chatapp2.Model.User;
import com.example.chatapp2.R;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    ArrayList<User> users;
    Context context;

    public UserListAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final UserListViewHolder holder, final int position) {
        holder.name.setText(users.get(position).getUsername());

        holder.userItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MessageActivity.class).putExtra("receiverID",users.get(position).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserListViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        LinearLayout userItem;

        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_users);
            userItem = itemView.findViewById(R.id.users_item);
        }
    }
}
