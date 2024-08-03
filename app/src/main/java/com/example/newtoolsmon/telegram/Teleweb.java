package com.example.newtoolsmon.telegram;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.newtoolsmon.R;
import java.util.Timer;
import java.util.TimerTask;

public class Teleweb extends AppCompatActivity {
    public static Handler handler;
    private static ValueCallback<Uri[]> mUploadMessageArr;
    String TAG = Teleweb.class.getSimpleName();
    boolean doubleBackToExitPressedOnce = false;

    private WebView webViewscanner;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsappweb);




        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // showInterstialAd();
                    }
                });
            }
        }, 2500);








//        getSupportActionBar().setTitle("Whats Web");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        if (!Internetconnection.checkConnection(this)) {
//            Banner banner = findViewById(R.id.startAppBanner);
//            banner.hideBanner();
//        }

        if (Build.VERSION.SDK_INT >= 24) {
            onstart();
            this.webViewscanner = findViewById(R.id.webViewscan);
            this.webViewscanner.clearFormData();
            this.webViewscanner.getSettings().setSaveFormData(true);
            this.webViewscanner.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
            this.webViewscanner.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            this.webViewscanner.setWebChromeClient(new Teleweb.webChromeClients());
            this.webViewscanner.setWebViewClient(new Teleweb.MyBrowser());
            //  this.webViewscanner.getSettings().setAppCacheMaxSize(5242880);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            //   this.webViewscanner.getSettings().setAppCacheEnabled(true);
            this.webViewscanner.getSettings().setJavaScriptEnabled(true);
            this.webViewscanner.getSettings().setDefaultTextEncodingName("UTF-8");
            this.webViewscanner.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            this.webViewscanner.getSettings().setDatabaseEnabled(true);
            this.webViewscanner.getSettings().setBuiltInZoomControls(false);
            this.webViewscanner.getSettings().setSupportZoom(false);
            this.webViewscanner.getSettings().setUseWideViewPort(true);
            this.webViewscanner.getSettings().setDomStorageEnabled(true);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.getSettings().setLoadsImagesAutomatically(true);
            this.webViewscanner.getSettings().setBlockNetworkImage(false);
            //   this.webViewscanner.getSettings().setBlockNetworkLoads(false);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.loadUrl("https://web.telegram.org/k/");
        } else {
            onstart();
            this.webViewscanner = findViewById(R.id.webViewscan);
            this.webViewscanner.clearFormData();
            this.webViewscanner.getSettings().setSaveFormData(true);
            this.webViewscanner.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
            this.webViewscanner.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            this.webViewscanner.setWebChromeClient(new Teleweb.webChromeClients());
            this.webViewscanner.setWebViewClient(new Teleweb.MyBrowser());
            //  this.webViewscanner.getSettings().setAppCacheMaxSize(5242880);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            //  this.webViewscanner.getSettings().setAppCacheEnabled(true);
            this.webViewscanner.getSettings().setJavaScriptEnabled(true);
            this.webViewscanner.getSettings().setDefaultTextEncodingName("UTF-8");
            this.webViewscanner.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            this.webViewscanner.getSettings().setDatabaseEnabled(true);
            this.webViewscanner.getSettings().setBuiltInZoomControls(false);
            this.webViewscanner.getSettings().setSupportZoom(false);
            this.webViewscanner.getSettings().setUseWideViewPort(true);
            this.webViewscanner.getSettings().setDomStorageEnabled(true);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.getSettings().setLoadsImagesAutomatically(true);
            this.webViewscanner.getSettings().setBlockNetworkImage(false);
            this.webViewscanner.getSettings().setBlockNetworkLoads(false);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.loadUrl("https://web.telegram.org/k/");
        }
    }


    @SuppressLint("HandlerLeak")
    private class btnInitHandlerListner extends Handler {
        @SuppressLint({"SetTextI18n"})
        public void handleMessage(Message msg) {
        }
    }

    //Webview Client Methods
    private class webChromeClients extends WebChromeClient {
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.e("CustomClient", consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }
    }


    //Webview Client Methods
    private class MyBrowser extends WebViewClient {
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e(Teleweb.this.TAG, "progressbar");
            super.onPageStarted(view, url, favicon);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String request) {
            view.loadUrl(request);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e(Teleweb.this.TAG, "progressbar GONE");

        }
    }

    //Initialisation Method
    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = 17)


    View.OnClickListener imgButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            //imgButton.setBackgroundResource(R.drawable.block_keyboard);
            Toast.makeText(Teleweb.this, "Keyborad is Blocked", Toast.LENGTH_SHORT).show();


           /* InputMethodManager inputManager = (InputMethodManager) TeleWeb.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(TeleWeb.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);*/



        }
    };

    View.OnClickListener imgButtonHandler1 = new View.OnClickListener() {

        public void onClick(View v) {
            //imgButton.setBackgroundResource(R.drawable.block_keyboard);
            Toast.makeText(Teleweb.this, "Keyborad is Unblocked", Toast.LENGTH_SHORT).show();




        }
    };

    public void onstart() {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE", "android.permission.ACCESS_COARSE_LOCATION"}, 123);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1001 && Build.VERSION.SDK_INT >= 21) {
            mUploadMessageArr.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(i2, intent));
            mUploadMessageArr = null;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean z = true;
        if (keyCode == 4) {
            try {
                if (this.webViewscanner.canGoBack()) {
                    this.webViewscanner.goBack();
                    return z;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
        z = super.onKeyDown(keyCode, event);
        return z;
    }

    protected void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
        this.webViewscanner.clearCache(true);
    }

    public void onDestroy() {
        super.onDestroy();
        this.webViewscanner.clearCache(true);
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        this.webViewscanner.clearCache(true);
        super.onStop();
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @SuppressLint({"HandlerLeak"})
    private void InitHandler() {
        handler = new Teleweb.btnInitHandlerListner();
    }


    //It's Method of More App
    private void more() {
        String appPackageName = getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction("android.intent.action.SEND");
        sendIntent.putExtra("android.intent.extra.TEXT", "Whats Web Scan\n  https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    @Override

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}
