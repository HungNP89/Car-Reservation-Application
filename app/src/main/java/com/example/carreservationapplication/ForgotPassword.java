package com.example.carreservationapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    TextInputLayout resetPassword;
    Button btnReset, btnBackSignIn;
    TextView textView1, textView2;
    ProgressBar progressBar;
    String emailReset;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //View
        resetPassword = findViewById(R.id.textInputResetEmail);
        btnReset = findViewById(R.id.btn_ResetPass);
        btnBackSignIn = findViewById(R.id.toSignIn);
        textView1 = findViewById(R.id.LoginText);
        textView2 = findViewById(R.id.textLogin);
        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(view -> {
            emailReset = resetPassword.getEditText().getText().toString().trim();

            if (emailReset.isEmpty()) {
                Toast.makeText(ForgotPassword.this, "Enter your registered email", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.sendPasswordResetEmail(emailReset).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "Please check your email to reset password", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgotPassword.this, "Failed to reset password", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

        btnBackSignIn.setOnClickListener(view -> {
            Intent backToSignIn = new Intent(ForgotPassword.this, Login.class);
            startActivity(backToSignIn);
            finish();
        });
    }
}