package com.masterandroid.whatsapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.masterandroid.whatsapp.Adapters.UsersAdapter;
import com.masterandroid.whatsapp.R;
import com.masterandroid.whatsapp.UserModel.Users;
import com.masterandroid.whatsapp.databinding.FragmentChatsBinding;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {


    public ChatsFragment() {
        // Required empty public constructor
    }

    FragmentChatsBinding binding;
    ArrayList<Users> list = new ArrayList<Users>();
    FirebaseDatabase database ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        database = FirebaseDatabase.getInstance();
        binding=FragmentChatsBinding.inflate(inflater, container, false);

        UsersAdapter adapter = new UsersAdapter(list,getContext());
        binding.chatRecyclarView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatRecyclarView.setLayoutManager(layoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    //list.clear();
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserNamee(dataSnapshot.getKey());
                    if(!users.getUserNamee().equals(FirebaseAuth.getInstance().getUid()))
                    list.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();

    }
}