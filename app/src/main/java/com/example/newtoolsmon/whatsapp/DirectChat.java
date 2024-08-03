package com.example.newtoolsmon.whatsapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.newtoolsmon.R;
import com.github.nikartm.button.FitButton;
import com.hbb20.CountryCodePicker;

public class DirectChat extends AppCompatActivity {

    EditText editTextMessage;
    EditText editPhno;
    CountryCodePicker countryCodePicker;
    FitButton send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));


        setContentView(R.layout.direct_chat);

        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        editPhno = (EditText) findViewById(R.id.editPhno);
        countryCodePicker = (CountryCodePicker) findViewById(R.id.countryCodePicker);
        send = (FitButton)findViewById(R.id.send);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean installed = isAppInstalled("com.whatsapp");
                if(installed) {

                    sendOnWhatsApps();

                } else {

                    Toast.makeText(DirectChat.this, "App is not currently installed on your phone " , Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void sendOnWhatsApps (){
        String messageText = editTextMessage.getText().toString();
        String phoneno = editPhno.getText().toString();

        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(
                        "https://api.whatsapp.com/send?phone=" + countryCodePicker.getSelectedCountryCode() + phoneno + "&text=" + messageText
                )));


        countryCodePicker.getSelectedCountryCode();
        Log.d("test", "countryCodePicker " +countryCodePicker.getSelectedCountryCode());

    }

    private boolean isAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }



}
