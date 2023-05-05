package com.example.carreservationapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    TextInputLayout Fullname, Username, Email, Phone, Password;
    Button goSignIn, moveSignUp;
    String fullname, username, email, phone, password;
    FirebaseDatabase firebaseDB;
    DatabaseReference reference;
    FirebaseAuth auth;
    String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //View
        goSignIn = findViewById(R.id.toSignIn);
        Fullname = findViewById(R.id.textInputFullname);
        Username = findViewById(R.id.textInputUsername1);
        Email = findViewById(R.id.textInputEmail1);
        Phone = findViewById(R.id.textInputPhoneNo);
        Password = findViewById(R.id.textInputPassword1);
        moveSignUp = findViewById(R.id.moveSignUp);


        //Move to Sign In Screen
        goSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToSignIn = new Intent(Register.this, Login.class);
                startActivity(moveToSignIn);
                finish();
            }
        });

        firebaseDB = FirebaseDatabase.getInstance();
        reference = firebaseDB.getReference("Users");
        auth = FirebaseAuth.getInstance();

        //Save user data to Firebase
        moveSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname = Fullname.getEditText().getText().toString().trim();
                username = Username.getEditText().getText().toString().trim();
                email = Email.getEditText().getText().toString().trim();
                phone = Phone.getEditText().getText().toString().trim();
                password = Password.getEditText().getText().toString().trim();

                if (isValid()) {
                    final ProgressDialog dialog = new ProgressDialog(Register.this);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setMessage("Sign Up is now processing , please wait ...");
                    dialog.show();
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("Fullname", fullname);
                                hashMap.put("Username", username);
                                hashMap.put("Email", email);
                                hashMap.put("Phone", phone);
                                hashMap.put("Password", password);
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                dialog.dismiss();
                                                final FirebaseUser user = auth.getCurrentUser();
                                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                                            builder.setMessage("Registered! Please verify your email before Sign In");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                            AlertDialog alertDialog = builder.create();
                                                            alertDialog.show();
                                                        } else {
                                                            dialog.dismiss();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                            }
                        }
                    });
                }
            }
        });
    }

    public boolean isValid() {
        Fullname.setErrorEnabled(false);
        Fullname.setError("");
        Username.setErrorEnabled(false);
        Username.setError("");
        Email.setErrorEnabled(false);
        Email.setError("");
        Phone.setErrorEnabled(false);
        Phone.setError("");
        Password.setErrorEnabled(false);
        Password.setError("");

        boolean isValid = false, isValidFname = false, isValidusername = false, isValidEmail = false, isValidPhone = false, isValidPassword = false;

        if (TextUtils.isEmpty(fullname)) {
            Fullname.setErrorEnabled(true);
            Fullname.setError("Please enter your fullname");
        } else {
            isValidFname = true;
        }
        if (TextUtils.isEmpty(username)) {
            Username.setErrorEnabled(true);
            Username.setError("Please enter your username");
        } else {
            isValidusername = true;
        }
        if (TextUtils.isEmpty(email)) {
            Email.setErrorEnabled(true);
            Email.setError("Please enter your email");
        } else {
            if (email.matches(pattern)) {
                isValidEmail = true;
            } else {
                Email.setErrorEnabled(true);
                Email.setError("Please enter valid email");
            }
        }
        if (TextUtils.isEmpty(phone)) {
            Phone.setErrorEnabled(true);
            Phone.setError("Please enter your phone number");
        } else {
            isValidPhone = true;
        }
        if (TextUtils.isEmpty(password)) {
            Password.setErrorEnabled(true);
            Password.setError("Please enter your password");
        } else {
            if (password.length() < 10) {
                Password.setErrorEnabled(true);
                Password.setError("Password requires 10 characters or more");
            } else {
                isValidPassword = true;
            }
        }

        isValid = isValidFname && isValidusername && isValidEmail && isValidPhone && isValidPassword;
        return isValid;
    }

}