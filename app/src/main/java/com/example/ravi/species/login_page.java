package com.example.ravi.species;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_page extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPassword;
    TextView registeringtextView;
    Button loginButton;
    ProgressBar loginProgressbar;

    FirebaseAuth loginFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        loginEmail=findViewById(R.id.loginemail);
        loginPassword=findViewById(R.id.loginpassword);
        loginButton=findViewById(R.id.loginbutton);
        registeringtextView=findViewById(R.id.registering);
        loginProgressbar=findViewById(R.id.loginprogressbar);

        loginFirebaseAuth=FirebaseAuth.getInstance();

            registeringtextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(login_page.this,signUp_page.class));
                }
            });

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(loginEmail.getText().toString().trim().isEmpty())
                    {
                        loginEmail.setError("this field is required");
                        loginEmail.requestFocus();
                        return;
                    }
                    if(loginPassword.getText().toString().trim().isEmpty())
                    {
                        loginPassword.setError("this field is required");
                        loginPassword.requestFocus();
                        return;
                    }

                    loginProgressbar.setVisibility(View.VISIBLE);

                    loginFirebaseAuth.signInWithEmailAndPassword(loginEmail.getText().toString().trim(),loginPassword.getText()
                            .toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            loginProgressbar.setVisibility(View.GONE);
                            if(task.isSuccessful())
                            {
                                startActivity(new Intent(login_page.this,Timeline.class));
                            }
                            else
                            {
                                Toast.makeText(login_page.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });

    }
}
