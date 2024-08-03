package com.example.newtoolsmon.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;

public class SplashScreen extends CommonActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        finish();

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {


            }
        }.start();


    }
}
