package com.example.newtoolsmon.other;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;
import com.example.newtoolsmon.textfeatures.EmojiText;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class ColorPicker extends CommonActivity {

    LinearLayout linear;
    TextView color_value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        setContentView(R.layout.colorpicker);
        linear = (LinearLayout) findViewById(R.id.linear);
        color_value = (TextView) findViewById(R.id.color_value);


        color_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerDialog();
            }
        });

        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!color_value.getText().toString().isEmpty()) {
                    ((ClipboardManager) ColorPicker.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(ColorPicker.this.color_value.getText().toString(), color_value.getText().toString()));
                  //  customToastAtBottom("Color Copied");
                }
            }
        });


    }

    private void colorPickerDialog(){


        ColorPickerDialogBuilder
                .with(ColorPicker.this)
                .setTitle("Choose color")
                .initialColor(R.color.color_yellow)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                      //  Toast.makeText(ColorPicker.this, "onColorSelected: 0x" + Integer.toHexString(selectedColor) , Toast.LENGTH_SHORT).show();

                        Log.d("tag", "mytag" + "onColorSelected: 0x" + Integer.toHexString(selectedColor));

                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {

                      //  Toast.makeText(ColorPicker.this, String.valueOf(Integer.toHexString(selectedColor)) , Toast.LENGTH_SHORT).show();
                        Log.d("tag", "mytag" + selectedColor );
                        linear.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(selectedColor)));
                        color_value.setText("#" + Integer.toHexString(selectedColor));

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
