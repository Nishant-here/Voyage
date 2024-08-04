package com.example.voyage;

import android.annotation.SuppressLint;
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

public class login extends AppCompatActivity {
    EditText lemail, lpass;
    Button mlogin;
    TextView create;
    ProgressBar progress;
    FirebaseAuth fAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        lemail = findViewById(R.id.lemail);
        lpass = findViewById(R.id.lpass);
        progress = findViewById(R.id.progress);
        mlogin = findViewById(R.id.mlogin);
        create = findViewById(R.id.create);
        fAuth=FirebaseAuth.getInstance();
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = lemail.getText().toString().trim();
                String password = lpass.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    lemail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    lpass.setError("Password is Required");
                    return;
                }
                if (password.length() < 6) {
                    lpass.setError("Password Must be greater then six words");
                    return;
                }
                progress.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), home_page.class));
                        } else {
                            Toast.makeText(login.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),signup.class));
            }
        });
    }
}