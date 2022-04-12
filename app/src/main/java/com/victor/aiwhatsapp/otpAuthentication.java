package com.victor.aiwhatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otpAuthentication extends AppCompatActivity {

    TextView mchangenumber;
    EditText mgetotp;
    android.widget.Button mverify;
    String enteredotp;

    FirebaseAuth firebaseAuth;
    ProgressBar mprogressbarofauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_authentication);

        mchangenumber = findViewById(R.id.changenumber);
        mverify = findViewById(R.id.verify);
        mgetotp = findViewById(R.id.getotp);
        mprogressbarofauth = findViewById(R.id.progressbarofotpauth);

        firebaseAuth = FirebaseAuth.getInstance();

        mchangenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(otpAuthentication.this, MainActivity.class);
                startActivity(intent);
            }
        });
        mverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredotp = mgetotp.getText().toString();
                if (enteredotp.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter your code first", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    mprogressbarofauth.setVisibility(View.VISIBLE);
                    String codereceived=getIntent().getStringExtra("otp");
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codereceived, enteredotp);
                    signInWithPhoneAuthCredential(credential);

                }
                

            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    mprogressbarofauth.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(otpAuthentication.this, setProfile.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                    {
                        mprogressbarofauth.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}