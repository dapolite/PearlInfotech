package com.example.pearlinfotech;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {
    ProgressBar mprogressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ActionBar bar = getSupportActionBar();
        if(bar!=null){
            bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg1));
        }


        Animation anim1 = AnimationUtils.loadAnimation(this,R.anim.anim_down);
            ImageView img =(ImageView)findViewById(R.id.imageView);
            img.setAnimation(anim1);

            mprogressBar = (ProgressBar) findViewById(R.id.progressBar);
            ObjectAnimator anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0, 100);
            anim.setDuration(4000);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.start();

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();

                }
            },3000);
    }
}
