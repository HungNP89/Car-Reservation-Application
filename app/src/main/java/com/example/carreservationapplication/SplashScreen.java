package com.example.carreservationapplication;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 5000;
    //Animation Variables
    Animation topAnimation, botAnimation;
    ImageView imageView;
    TextView ssText1, ssText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        //Animation
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        botAnimation = AnimationUtils.loadAnimation(this, R.anim.bot_animation);

        //View
        imageView = findViewById(R.id.AppLogo);
        ssText1 = findViewById(R.id.SSText1);
        ssText2 = findViewById(R.id.SSText2);

        //set animation for logo image
        imageView.setAnimation(topAnimation);
        //set animation for text
        ssText1.setAnimation(botAnimation);
        ssText2.setAnimation(botAnimation);


        //Modify duration display each screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent moveToLogin = new Intent(SplashScreen.this, Login.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(imageView, "app_logo");
                pairs[1] = new Pair<View, String>(ssText1, "app_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
                startActivity(moveToLogin, options.toBundle());
            }
        }, SPLASH_SCREEN);
    }
}