package com.example.newtoolsmon.other;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;

public class PrivacyPolicy extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);

        WebView webView = findViewById(R.id.web);


        webView.loadUrl("https://toolsmon-utility-app.web.app");

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
    }
}
