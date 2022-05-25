package com.masterandroid.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.masterandroid.whatsapp.Adapters.FragmentAdapter;
import com.masterandroid.whatsapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private FirebaseAuth Auth ;
    private GoogleSignInClient mGoogleSignInClient;
    private String [] titles = new String[]{"CHATS","STATUS","CALLS"};

    FragmentAdapter fragmentAdapter ;

    //FragmentAdapter fragmentAdapter = new FragmentAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().setElevation(0);
        Auth= FirebaseAuth.getInstance();
        fragmentAdapter = new FragmentAdapter(this);


        binding.viewpager2.setAdapter(fragmentAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewpager2,(tab, position) -> tab.setText(titles[position])).attach();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.settings:
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.logout:
                //Auth.signOut()
                signOut();

                Intent i = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(i);
                break;

        }
        return true;
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

}

