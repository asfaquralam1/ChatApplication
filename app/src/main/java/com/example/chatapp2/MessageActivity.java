package com.example.chatapp2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp2.Model.Message;
import com.example.chatapp2.adapters.MessageAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    ImageView sendButton;
    EditText messageField;
    Long timeInMilli;
    String uID, receiverID, messageId, strMessage;
    DatabaseReference myRef, rootReference, uReference, mReference;
    Toolbar toolbar;
    RecyclerView recyclerView;
    TextView tvName;
    LinearLayoutManager mLayoutManager;
    ArrayList<Message> list;
    FirebaseAuth mAuth;
    MessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        toolbar = findViewById(R.id.toolbar_message);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvName = findViewById(R.id.name_message);
        sendButton = findViewById(R.id.send_button_message);
        messageField = findViewById(R.id.edit_text_message);


        recyclerView = findViewById(R.id.recyclerView_message);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(false);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);

        list = new ArrayList<>();

        Intent myIntent = getIntent();
        if (myIntent != null) {
            receiverID = myIntent.getStringExtra("receiverID");
        }

        mAuth = FirebaseAuth.getInstance();
        if (mAuth != null) {
            uID = mAuth.getUid();
        } else {
            Intent intent = new Intent(MessageActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            MessageActivity.this.finish();
        }

        rootReference = FirebaseDatabase.getInstance().getReference();
        mReference = FirebaseDatabase.getInstance().getReference("Messages").child(uID).child(receiverID);
        uReference = FirebaseDatabase.getInstance().getReference().child("Users").child(receiverID);

        uReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("username").getValue(String.class);
                tvName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MessageActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        showAllMessage();


        sendButton.setOnClickListener(view -> {
            if (messageField.getText().toString().isEmpty()) {
                messageField.setError("Please enter message here");
                messageField.requestFocus();
            } else {
                timeInMilli = System.currentTimeMillis();

                String senderReference = "Messages/" + uID + "/" + receiverID;
                String receiverReference = "Messages/" + receiverID + "/" + uID;

                myRef = rootReference.child("Messages").child(uID).child(receiverID).push();
                messageId = myRef.getKey();
                strMessage = messageField.getText().toString();

                Message message = new Message(uID, receiverID, strMessage, messageId, "text", "none", false, timeInMilli);
                rootReference.child(senderReference).child(messageId).setValue(message).addOnFailureListener(e -> Snackbar.make(MessageActivity.this.getWindow().getDecorView().getRootView(), "Something went wrong", Snackbar.LENGTH_LONG).show());
                rootReference.child(receiverReference).child(messageId).setValue(message).addOnFailureListener(e -> Snackbar.make(MessageActivity.this.getWindow().getDecorView().getRootView(), "Something went wrong", Snackbar.LENGTH_LONG).show());

                messageField.setText("");
            }
        });
    }

    private void showAllMessage() {

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Message message = dataSnapshot1.getValue(Message.class);
                    list.add(message);

                }
                if (list == null) {
                    Toast.makeText(MessageActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter = new MessageAdapter(MessageActivity.this, list);
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar.make(MessageActivity.this.getWindow().getDecorView().getRootView(), "Something went wrong", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}