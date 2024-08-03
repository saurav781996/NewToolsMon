package com.example.newtoolsmon.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;
import com.example.newtoolsmon.blackboard.BlackBoardMain;
import com.example.newtoolsmon.camera.GenerateQRCode;
import com.example.newtoolsmon.camera.Mirror;
import com.example.newtoolsmon.camera.QRCodeScanner;
import com.example.newtoolsmon.clocks.AnalogClock;
import com.example.newtoolsmon.clocks.DigitalClock;
import com.example.newtoolsmon.clocks.StopWatch;
import com.example.newtoolsmon.fancytext.FancyText;
import com.example.newtoolsmon.gps.GpsLocation;
import com.example.newtoolsmon.memorysenitizer.EraserMain;
import com.example.newtoolsmon.other.AgeCalculator;
import com.example.newtoolsmon.other.BMICalculator;
import com.example.newtoolsmon.other.ColorPicker;
import com.example.newtoolsmon.other.HashGenerator;
import com.example.newtoolsmon.other.PasswordGenerator;
import com.example.newtoolsmon.other.KotlinTorch;
import com.example.newtoolsmon.other.ColorTorch;
import com.example.newtoolsmon.other.RandomNumberGenerator;
import com.example.newtoolsmon.telegram.Teleweb;
import com.example.newtoolsmon.textfeatures.EmojiText;
import com.example.newtoolsmon.textscanner.TextScannerMain;
import com.example.newtoolsmon.unitconvert.ConversionsActivity;
import com.example.newtoolsmon.whatsapp.NewWhatsAppDirectChat;
import com.example.newtoolsmon.whatsapp.WhatsAppWeb;
import com.example.newtoolsmon.textencydecy.TextEncypDecry;

import java.util.Objects;


public class DashBoardMainClicks extends CommonActivity {

    TextView textName, textSub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DashBoardMainArrayList memberDashBoardMainArrayList = (DashBoardMainArrayList) getIntent().getExtras().getSerializable("DATA");
        textName = (TextView) findViewById(R.id.title_text);
        textSub = (TextView) findViewById(R.id.sub_text);


        String open = memberDashBoardMainArrayList.getTitle();


        if (open.equals("Analog Clock")) {

            startActivity(new Intent(DashBoardMainClicks.this, AnalogClock.class));
            finish();

        }
        if (open.equals("Digital Clock")) {

            startActivity(new Intent(DashBoardMainClicks.this, DigitalClock.class));
            finish();

        }
        if (open.equals("Torch")) {
                startActivity(new Intent(DashBoardMainClicks.this, KotlinTorch.class));
                finish();
        }


        if (open.equals("Color Torch")) {

            startActivity(new Intent(DashBoardMainClicks.this, ColorTorch.class));
            finish();

        }

        if (open.equals("Mirror")) {

            startActivity(new Intent(DashBoardMainClicks.this, Mirror.class));
            finish();

        }
        if (open.equals("QR Scanner")) {

            startActivity(new Intent(DashBoardMainClicks.this, QRCodeScanner.class));
            finish();

        }
        if (open.equals("Location")) {

            startActivity(new Intent(DashBoardMainClicks.this, GpsLocation.class));
            finish();

        }


        if (open.equals("Stopwatch")) {

            startActivity(new Intent(DashBoardMainClicks.this, StopWatch.class));
            finish();

        }
        if (open.equals("Direct Chat")) {

             startActivity(new Intent(DashBoardMainClicks.this, NewWhatsAppDirectChat.class));
            finish();

        }
        if (open.equals("Whatsapp web")) {

            startActivity(new Intent(DashBoardMainClicks.this, WhatsAppWeb.class));
            finish();

        }


        if (open.equals("Calculators")) {

            Toast.makeText(DashBoardMainClicks.this, "Analog Clock", Toast.LENGTH_SHORT).show();
            finish();

        }

        if (open.equals("Notification log")) {

            Toast.makeText(DashBoardMainClicks.this, "Torch", Toast.LENGTH_SHORT).show();
            finish();

        }


        if (open.equals("Teleweb")) {

            startActivity(new Intent(DashBoardMainClicks.this, Teleweb.class));
            finish();

        }
        if (open.equals("Stylish Text")) {

            startActivity(new Intent(DashBoardMainClicks.this, FancyText.class));
            finish();

        }


        if (open.equals("Generate QR")) {

            startActivity(new Intent(DashBoardMainClicks.this, GenerateQRCode.class));
            finish();

        }


        if (open.equals("Age Calculator")) {

            startActivity(new Intent(DashBoardMainClicks.this, AgeCalculator.class));
            finish();

        }

        if (open.equals("Emoji Text")) {

            startActivity(new Intent(DashBoardMainClicks.this, EmojiText.class));
            finish();

        }



        if (open.equals("Color Picker")) {

            startActivity(new Intent(DashBoardMainClicks.this, ColorPicker.class));
            finish();

        }

        if (open.equals("Password Generator")) {

            startActivity(new Intent(DashBoardMainClicks.this, PasswordGenerator.class));
            finish();

        }


    if (open.equals("Text Scanner")) {

            startActivity(new Intent(DashBoardMainClicks.this, TextScannerMain.class));
            finish();

        }

    if (open.equals("Fancy Text")) {

            startActivity(new Intent(DashBoardMainClicks.this, FancyText.class));
            finish();

        }

    if (open.equals("Random No Generator")) {

            startActivity(new Intent(DashBoardMainClicks.this, RandomNumberGenerator.class));
            finish();

        }

    if (open.equals("Hash Generator")) {

            startActivity(new Intent(DashBoardMainClicks.this, HashGenerator.class));
            finish();

        }


    if (open.equals("BMI calculator")) {

            startActivity(new Intent(DashBoardMainClicks.this, BMICalculator.class));
            finish();

        }

    if (open.equals("Memory Sanitizer")) {

            startActivity(new Intent(DashBoardMainClicks.this, EraserMain.class));
            finish();

        }

    if (open.equals("Black Board")) {

            startActivity(new Intent(DashBoardMainClicks.this, BlackBoardMain.class));
            finish();

        }

    if (open.equals("Text Encrypter")) {

            startActivity(new Intent(DashBoardMainClicks.this, TextEncypDecry.class));
            finish();

        }

    if (open.equals("Converter")) {

            startActivity(new Intent(DashBoardMainClicks.this, ConversionsActivity.class));
            finish();

        }


    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        getFragmentManager().popBackStack();
    }
}
