package com.masterandroid.whatsapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.masterandroid.whatsapp.UserModel.Users;
import com.masterandroid.whatsapp.databinding.ActivitySettingsBinding;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding ;
    FirebaseStorage storage;
    FirebaseAuth Auth;
    FirebaseDatabase database;
    ActivityResultLauncher<String> takePhoto;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        Auth = FirebaseAuth.getInstance();

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users = snapshot.getValue(Users.class);
                        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.user)
                                .into(binding.profilePic);

                        binding.statusAccountHolder.setText(users.getAbout());
                        binding.accountHolderName.setText(users.getUserName());



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.accountHolderName.getText().toString();
                String status = binding.statusAccountHolder.getText().toString();

                HashMap<String , Object > obj = new HashMap<>();
                obj.put("userName",username);
                obj.put("about",status);


                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                        .updateChildren(obj);
            }
        });



        takePhoto = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                if (result != null) {

                    binding.profilePic.setImageURI(imageUri);
                    imageUri = result;

                }



                StorageReference refrence = storage.getReference().child("profile_pics")
                        .child(FirebaseAuth.getInstance().getUid());

                refrence.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        refrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                        .child("profilepic").setValue(uri.toString());
                                Toast.makeText(SettingsActivity.this, "Profile Pitchure Updated ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });



            }
        });

        binding.backSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(intent);


            }
        });


        binding.plusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takePhoto.launch("image/*");



            }
        });



















    }
}