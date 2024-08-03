package com.example.newtoolsmon.fancytext;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.newtoolsmon.R;

public class FancyText extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.fancy_text);


        WebView mywebview = (WebView) findViewById(R.id.webView);

        mywebview.getSettings().setJavaScriptEnabled(true);
        //mywebview.loadUrl("https://www.google.com/");

        // put file inside main/assets/myresource.html
        mywebview.loadUrl("file:///android_asset/index.html");


    }
}

