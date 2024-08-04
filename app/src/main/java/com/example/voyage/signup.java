package com.example.voyage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
    EditText sfname, semail, spass, sphone;
    Button signup;
    TextView loginbtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        sfname = findViewById(R.id.sfname);
        semail = findViewById(R.id.semail);
        spass = findViewById(R.id.spass);
        sphone = findViewById(R.id.sphone);
        signup = findViewById(R.id.signup);
        loginbtn = findViewById(R.id.loginbtn);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
       /* if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),home_page.class));
        }*/
        signup.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = semail.getText().toString().trim();
                String password = spass.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    semail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    spass.setError("Password is Required");
                    return;
                }
                if (password.length() < 6) {
                    spass.setError("Password Must be greater then six words");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(signup.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), home_page.class));
                        } else {
                            Toast.makeText(signup.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });
    }
}