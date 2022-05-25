package com.masterandroid.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.masterandroid.whatsapp.UserModel.Users;
import com.masterandroid.whatsapp.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {


    ActivitySignUpBinding binding;
    private FirebaseAuth Auth ;
    FirebaseDatabase database ;
    ProgressDialog progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

       binding=ActivitySignUpBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

       getSupportActionBar().hide();

       Auth = FirebaseAuth.getInstance();
       database = FirebaseDatabase.getInstance();
       progress= new ProgressDialog(SignUpActivity.this);
       progress.setTitle("Creating");
       progress.setMessage("creating user profile");


       binding.signInBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               progress.show();
               Auth.createUserWithEmailAndPassword(binding.emailSignIn.getText().toString(),binding.passwordSignIn.getText().toString()).
                       addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       progress.dismiss();
                       if(task.isSuccessful())
                       {
                           Users user = new Users(binding.signInName.getText().toString(),binding.emailSignIn.getText().toString(),binding.passwordSignIn.getText().toString());
                           String id = task.getResult().getUser().getUid();
                           database.getReference().child("Users").child(id).setValue(user) ;
                           Toast.makeText(SignUpActivity.this, "User is Created", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                           startActivity(intent);
                       }
                       else
                       {
                           Toast.makeText(SignUpActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }

                   }
               });

           }
       });


        binding.signIntv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });


    }


}