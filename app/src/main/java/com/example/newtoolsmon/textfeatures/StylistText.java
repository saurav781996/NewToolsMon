package com.example.newtoolsmon.textfeatures;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;
import com.example.newtoolsmon.other.ColorPicker;
import com.github.nikartm.button.FitButton;

public class StylistText extends CommonActivity {

    EditText input;
    TextView output;
    FitButton convert;
    FitButton copy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));



        setContentView(R.layout.stylish_text);
        inits();



        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldText = input.getText().toString();
                String newText = convertFancyString(oldText);
                output.setText(newText);
            }
        });


        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (!output.getText().toString().isEmpty()) {
                    ((ClipboardManager) StylistText.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(StylistText.this.output.getText().toString(), output.getText().toString()));
                    //  customToastAtBottom("Color Copied");
                }



            }
        });

    }

    private void inits() {
        input = (EditText) findViewById(R.id.input);
        output = (TextView) findViewById(R.id.output);
        convert = (FitButton) findViewById(R.id.convert);
        copy = (FitButton) findViewById(R.id.copy);


    }

    private String convertFancyString(String input) {

        String normal = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM123456789";
        String split = "ⓠⓦⓔⓡⓣⓨⓤⓘⓞⓟⓐⓢⓓⓕⓖⓗⓙⓚⓛⓩⓧⓒⓥⓑⓝⓜⓆⓌⒺⓇⓉⓎⓊⒾⓄⓅⒶⓈⒹⒻⒼⒽⒿⓀⓁⓏⓍⒸⓋⒷⓃⓂ①②③④⑤⑥⑦⑧⑨";
        String output = "";
        char letter;

        for (int i = 0; i < input.length(); i++) {
            letter = input.charAt(i);

            int a = normal.indexOf(letter);
            output += (a != -1) ? split.charAt(a) : letter;

        }

        return new StringBuilder(output).toString();

    }

    private String convertBlackSquare(String input) {

        String normal = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String split = "ᎯᏰᏨᎠᎬᎰᎶᎻᎨᏠᏦᏝᎷᏁᎾᏢᏅᏒᏕᎿᏬᏉᏯᎲᎽᏃ";
        String output = "";
        char letter;

        for (int i = 0; i < input.length(); i++) {
            letter = input.charAt(i);

            int a = normal.indexOf(letter);
            output += (a != -1) ? split.charAt(a) : letter;

        }

        return new StringBuilder(output).toString();

    }

}
