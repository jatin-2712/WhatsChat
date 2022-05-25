package com.masterandroid.whatsapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
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
import com.masterandroid.whatsapp.databinding.ActivitySignInBinding;
import com.masterandroid.whatsapp.databinding.ActivitySignUpBinding;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    FirebaseAuth Auth ;
    ProgressDialog progress;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseDatabase database ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();


        Auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progress= new ProgressDialog(SignInActivity.this);
        progress.setTitle("Loading");
        progress.setMessage("Logging into your account");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        binding.googleLoginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();

                startActivityForResult.launch(signInIntent);
            }
        });



        binding.signInBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(binding.emailSignIn.getText().toString().isEmpty())
                {
                    binding.emailSignIn.setError("Enter Your Email");
                    return;
                }
                if(binding.passwordSignIn.getText().toString().isEmpty())
                {
                    binding.passwordSignIn.setError("Enter Your Password");
                    return;
                }


                progress.show();
                Auth.signInWithEmailAndPassword(binding.emailSignIn.getText().toString(),binding.passwordSignIn.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progress.dismiss();
                        if(task.isSuccessful())
                        { Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(SignInActivity.this, "Invalid User Id and Password Please Create", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        binding.signuptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });


        if(Auth.getCurrentUser() != null)
        {Intent intent = new Intent(SignInActivity.this,MainActivity.class);
            startActivity(intent);
        }


    }
   ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(
           new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
               @Override
               public void onActivityResult(ActivityResult result) {

                   if(result.getResultCode()==Activity.RESULT_OK)
                   {
                       Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                       handleSignInResult(task);
                   }

               }
           }
   );


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.

            Log.w("Google Sign in", "Email = "+acct.getEmail()+" Name "+ acct.getDisplayName());

            Users users = new Users();

            users.setUserNamee(acct.getId());
            users.setUserName(acct.getDisplayName());
            users.setProfilepic(acct.getPhotoUrl().toString());

            database.getReference().child("Users").child(acct.getId()).setValue(users);
            startActivity(new Intent(SignInActivity.this,MainActivity.class));
            Toast.makeText(SignInActivity.this, "Signed in with Google "+acct.getEmail(), Toast.LENGTH_SHORT).show();
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //updateUI(null);
        }
    }


}