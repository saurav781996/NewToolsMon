package com.example.newtoolsmon.other;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;
import com.example.newtoolsmon.dashboard.MainActivity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ColorTorch extends CommonActivity {

    SegmentedButtonGroup use_digit;
    boolean settingsCanWrite ;

    Integer curBrightnessValue;

    RelativeLayout colors;

    Timer colorTimer = new Timer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.rainbow_tourch);

        askChangeBrightness();

        settingsCanWrite = Settings.System.canWrite(getApplicationContext());

        use_digit = (SegmentedButtonGroup) findViewById(R.id.rainbow_torch);
        colors = (RelativeLayout) findViewById(R.id.colors);

        use_digit.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {

                if (position == 0) {

                    if (settingsCanWrite){
                        changeScreenBrightness(ColorTorch.this, curBrightnessValue);
                    }

                    colorTimer.cancel();

                } else if (position == 1) {

                    if (settingsCanWrite){
                        changeScreenBrightness(ColorTorch.this, 4000);
                    }


                    colorTimer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    generateRandomColor();
                                }
                            });
                        }
                    }, 0, 1000);

                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            curBrightnessValue = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void changeScreenBrightness(Context context, int screenBrightnessValue) {
        // Change the screen brightness change mode to manual.
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
        // It will also change the screen brightness for the device.
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue);

        Log.d("mytesta", "screenBrightnessValue " + screenBrightnessValue);

    }

    private void generateRandomColor() {

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        colors.setBackgroundColor(color);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (settingsCanWrite){
            changeScreenBrightness(ColorTorch.this, curBrightnessValue);
        }

        if (colorTimer != null){
            colorTimer.cancel();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askChangeBrightness() {

        boolean settingsCanWrite = Settings.System.canWrite(getApplicationContext());

        if (!settingsCanWrite) {
            new AlertDialog.Builder(ColorTorch.this).setTitle("Change brightness permission")
                    .setMessage("Change brightness permission required for better app experience")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                            startActivity(intent);
                        }
                    }).show();
        } else {
            setSharedPreferences("brightness_permission", "true", ColorTorch.this);
        }
    }
}
