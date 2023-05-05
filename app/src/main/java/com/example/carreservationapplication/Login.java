package com.example.carreservationapplication;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button goSignUp, btnSignIn, btnForgotPass;
    ImageView imageView;
    TextView text1, text2;
    TextInputLayout email, password;
    FirebaseAuth firebaseAuth;

    String Email, PWD;
    String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        try {
            //View
            goSignUp = findViewById(R.id.toSignUp);
            btnSignIn = findViewById(R.id.btn_SignIn);
            imageView = findViewById(R.id.topLogo);
            text1 = findViewById(R.id.LoginText);
            text2 = findViewById(R.id.textLogin);
            email = findViewById(R.id.textInputEmail);
            password = findViewById(R.id.textInputPassword);
            btnForgotPass = findViewById(R.id.btn_forgot);

            firebaseAuth = FirebaseAuth.getInstance();
            //Move to Main Screen
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Email = email.getEditText().getText().toString().trim();
                    PWD = password.getEditText().getText().toString().trim();

                    if (isValid()) {
                        final ProgressDialog dialog = new ProgressDialog(Login.this);
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setMessage("Log In is now processing, please wait ...");
                        dialog.show();

                        firebaseAuth.signInWithEmailAndPassword(Email, PWD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    dialog.dismiss();

                                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                        dialog.dismiss();
                                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent moveToMain = new Intent(Login.this, MainScreen.class);
                                                startActivity(moveToMain);
                                                finish();
                                            }
                                        }, 2000);
                                    } else {
                                        Toast.makeText(Login.this, "Please verify your email first", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(Login.this, "Wrong account", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //Move to Sign Up Screen
        goSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToSignUp = new Intent(Login.this, Register.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(imageView, "app_logo");
                pairs[1] = new Pair<View, String>(text1, "app_text");
                pairs[2] = new Pair<View, String>(text2, "app_text");
                pairs[3] = new Pair<View, String>(email, "email_tran");
                pairs[4] = new Pair<View, String>(password, "password_tran");
                pairs[5] = new Pair<View, String>(goSignUp, "signUp_tran");
                pairs[6] = new Pair<View, String>(btnSignIn, "signIn_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(moveToSignUp, options.toBundle());
            }
        });
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToFPD = new Intent(Login.this, ForgotPassword.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(imageView, "app_logo");
                pairs[1] = new Pair<View, String>(text1, "app_text");
                pairs[2] = new Pair<View, String>(text2, "app_text");
                pairs[3] = new Pair<View, String>(email, "email_tran");
                pairs[4] = new Pair<View, String>(password, "password_tran");
                pairs[5] = new Pair<View, String>(goSignUp, "signUp_tran");
                pairs[6] = new Pair<View, String>(btnSignIn, "signIn_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(moveToFPD, options.toBundle());
            }
        });
    }

    public boolean isValid() {
        email.setErrorEnabled(false);
        email.setError("");
        password.setErrorEnabled(false);
        password.setError("");

        boolean isValid = false, isValidemail = false, isValidpassword = false;
        if (TextUtils.isEmpty(Email)) {
            email.setErrorEnabled(true);
            email.setError("Please enter your email");
        } else {
            if (Email.matches(pattern)) {
                isValidemail = true;
            } else {
                email.setErrorEnabled(true);
                email.setError("Please enter valid email");
            }
        }
        if (TextUtils.isEmpty(PWD)) {
            password.setErrorEnabled(true);
            password.setError("Please enter your password");
        } else {
            isValidpassword = true;
        }

        isValid = isValidemail && isValidpassword;
        return isValid;
    }
}