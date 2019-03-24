package com.example.ravi.species;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sameActivity extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAuth samefirebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser samefirebaseUser=samefirebaseAuth.getCurrentUser();

        if(samefirebaseUser!=null && samefirebaseUser.isEmailVerified())
        {
            Intent intent=new Intent(sameActivity.this,Timeline.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }
}
