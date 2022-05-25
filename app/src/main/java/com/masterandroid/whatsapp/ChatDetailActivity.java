package com.masterandroid.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.masterandroid.whatsapp.Adapters.ChatAdapter;
import com.masterandroid.whatsapp.UserModel.Messages;
import com.masterandroid.whatsapp.databinding.ActivityChatDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding ;
    FirebaseDatabase database;
    FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();

        Auth = FirebaseAuth.getInstance();
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String senderId = Auth.getUid();
        String recieverId = getIntent().getStringExtra("userId");
        String username = getIntent().getStringExtra("userName");
        String profilepic = getIntent().getStringExtra("userprofilePic");

        binding.userName.setText(username);
        Picasso.get().load(profilepic).placeholder(R.drawable.user).into(binding.userProfilePic);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<Messages> messages = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messages,this);
        binding.chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom = senderId+recieverId;
        final String recieverRoom = recieverId+senderId;

        database.getReference().child("Chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messages.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    Messages model = snapshot1.getValue(Messages.class);
                    messages.add(model);
                }
                chatAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String message =  binding.typeMessage.toString();
               final Messages model = new Messages(senderId,message);
               model.setTimeStramp(new Date().getTime());
               binding.typeMessage.setText("");

               database.getReference().child("Chats").child(senderRoom)
                       .push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {

                       database.getReference().child("Chats").child(recieverRoom)
                               .push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {

                                   }
                               });
                   }
               });

            }
        });

    }
}