package com.google.firebase.app.chatz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;

public class FirstActivity extends AppCompatActivity {
    public static final int RC_SIGN_IN = 1;
    private FirebaseAuth mFirebaseauth;
    private FirebaseAuth.AuthStateListener mAuthstateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mFirebaseauth = FirebaseAuth.getInstance();
        ArrayList<Names> words=new ArrayList<Names>();
        words.add(new Names("chatz","how may i help u",R.drawable.bot));
        words.add(new Names("contact1","hello",R.drawable.contact));
        words.add(new Names("contact2","hello",R.drawable.contact));
        words.add(new Names("contact3","hello",R.drawable.contact));
        NameAdapter wa=new NameAdapter(this,words,R.color.colorTitle);
        ListView listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(wa);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent i = new Intent(FirstActivity.this,Main2Activity.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(FirstActivity.this,MainActivity.class);
                    startActivity(i);
                }
            }
        });
         mAuthstateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();
                if(user != null){
                    onSignInInitialized(user.getDisplayName());
                    Toast.makeText(FirstActivity.this,"signed in", Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivityForResult(
        AuthUI.getInstance()
                .createSignInIntentBuilder().setIsSmartLockEnabled(false)
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build())).build(), RC_SIGN_IN);
                }
            }
        };
    }
     @Override
    protected void onPause(){
        super.onPause();
        mFirebaseauth.removeAuthStateListener(mAuthstateListener);
    }
    @Override
    protected void onResume(){
        super.onResume();
        mFirebaseauth.addAuthStateListener(mAuthstateListener);
    }
    private void onSignInInitialized(String user){
        new MainActivity().setmUsername(user);
    }
}
