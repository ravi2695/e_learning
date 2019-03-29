package com.example.ravi.species;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ravi.species.Common.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUp_page extends AppCompatActivity {

    Button signUpButton;
    EditText emailEditText;
    EditText passwordEditText;
    ProgressBar progressBarLoading;
    FirebaseAuth firebaseAuth;
    TextView moving_to_login_page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        signUpButton=findViewById(R.id.signUp);
        emailEditText=findViewById(R.id.email);
        passwordEditText=findViewById(R.id.password);
        progressBarLoading=findViewById(R.id.progressBar);
        moving_to_login_page=findViewById(R.id.moveToLoginPage);

        firebaseAuth=FirebaseAuth.getInstance();


        moving_to_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signUp_page.this,login_page.class));
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectedToInternet(getBaseContext())) {

                    if (emailEditText.getText().toString().trim().isEmpty()) {
                        emailEditText.setError("this field is required");
                        emailEditText.requestFocus();
                        return;
                    }
                    if (passwordEditText.getText().toString().trim().isEmpty()) {
                        passwordEditText.setError("this field is required");
                        passwordEditText.requestFocus();
                        return;
                    }
                    progressBarLoading.setVisibility(View.VISIBLE);


                    firebaseAuth.createUserWithEmailAndPassword(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    progressBarLoading.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    Toast.makeText(signUp_page.this, "registration successfull\nkindly check your email for verification", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(signUp_page.this, login_page.class));
                                                    emailEditText.setText("");
                                                    passwordEditText.setText("");
                                                } else {
                                                    Toast.makeText(signUp_page.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });


                                    } else {
                                        Toast.makeText(signUp_page.this, "registration failed\n" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(signUp_page.this,"PLEASE CHECK YOUR INTERNET CONNECTION",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });


    }
}
