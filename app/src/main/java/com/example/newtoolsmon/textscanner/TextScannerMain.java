package com.example.newtoolsmon.textscanner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;
import com.example.newtoolsmon.other.PasswordGenerator;


public class TextScannerMain extends CommonActivity {

    TextView mytext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));


        setContentView(R.layout.text_scanner);

        mytext = (TextView)findViewById(R.id.mytext);


        mytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mytext.getText().toString().isEmpty()) {
                    ((ClipboardManager) TextScannerMain.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(TextScannerMain.this.mytext.getText().toString(), mytext.getText().toString()));
                }

            }
        });


        ScannerView scanner = findViewById(R.id.scanner);
        scanner.setOnDetectedListener(this, new ScannerListener() {
            @Override
            public void onDetected(String detections) {
                mytext.setText(detections);
            }

            @Override
            public void onStateChanged(String state, int i) {
                Log.d("state", state);
            }
        });



    }
}
