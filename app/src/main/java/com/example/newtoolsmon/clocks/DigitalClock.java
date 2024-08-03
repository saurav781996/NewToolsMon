package com.example.newtoolsmon.clocks;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtoolsmon.R;
import com.example.newtoolsmon.other.ColorPicker;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DigitalClock extends AppCompatActivity {

    TextView time;
    RelativeLayout background_clock;
    Handler handler = new Handler();

    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     //hide Status Bar
        setContentView(R.layout.activity_digital_clock);

        time = (TextView) findViewById(R.id.current_time);
        background_clock = (RelativeLayout) findViewById(R.id.background_clock);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                            time.setText(LocalDateTime.now().getHour() + " : " + LocalDateTime.now().getMinute() + " : " + LocalDateTime.now().getSecond());

                        }


                    }
                });
            }
        }, 0, 1000);




        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timecolorPickerDialog();
            }
        });


        background_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgrondcolorPickerDialog();
            }
        });





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void timecolorPickerDialog(){


        ColorPickerDialogBuilder
                .with(DigitalClock.this)
                .setTitle("Choose color")
                .initialColor(R.color.color_yellow)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {

                        Log.d("tag", "mytag" + "onColorSelected: 0x" + Integer.toHexString(selectedColor));

                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {

                        Log.d("tag", "mytag" + selectedColor );
                      //  time.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(selectedColor)));
                        time.setTextColor(Color.parseColor("#" + Integer.toHexString(selectedColor)));

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();


    }

    private void backgrondcolorPickerDialog(){


        ColorPickerDialogBuilder
                .with(DigitalClock.this)
                .setTitle("Choose color")
                .initialColor(R.color.color_yellow)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .showColorPreview(true)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {

                        Log.d("tag", "mytag" + "onColorSelected: 0x" + Integer.toHexString(selectedColor));

                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {

                        Log.d("tag", "mytag" + selectedColor );
                        background_clock.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(selectedColor)));

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();


    }

}