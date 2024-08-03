package com.example.newtoolsmon.textfeatures;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;
import java.io.IOException;
import java.io.InputStream;

public class EmojiText extends CommonActivity {
    Button clearTxtBtn;
    Button convertButton;
    EditText convertedText;
    Button copyBtn;
    EditText emojeeText;
    EditText inputText;
    Button shareButton;
    LinearLayout backbutton;

    //button convert click event listener
    private class btnConvertListner implements View.OnClickListener {
        public void onClick(View view) {
            if (EmojiText.this.inputText.getText().toString().isEmpty()) {
                Toast.makeText(EmojiText.this.getApplicationContext(), "Enter text", Toast.LENGTH_SHORT).show();
                return;
            }
            char[] charArray = EmojiText.this.inputText.getText().toString().toCharArray();
            EmojiText.this.convertedText.setText(".\n");
            for (char character : charArray) {
                byte[] localObject3;
                InputStream localObject2;
                if (character == '?') {
                    try {
                        InputStream localObject1 = EmojiText.this.getBaseContext().getAssets().open("ques.txt");
                        localObject3 = new byte[localObject1.available()];
                        localObject1.read(localObject3);
                        localObject1.close();
                        EmojiText.this.convertedText.append(new String(localObject3).replaceAll("[*]", EmojiText.this.emojeeText.getText().toString()) + "\n\n");
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                } else if (character == ((char) (character & 95)) || Character.isDigit(character)) {
                    try {
                        localObject2 = EmojiText.this.getBaseContext().getAssets().open(character + ".txt");
                        localObject3 = new byte[localObject2.available()];
                        localObject2.read(localObject3);
                        localObject2.close();
                        EmojiText.this.convertedText.append(new String(localObject3).replaceAll("[*]", EmojiText.this.emojeeText.getText().toString()) + "\n\n");
                    } catch (IOException ioe2) {
                        ioe2.printStackTrace();
                    }
                } else {
                    try {
                        localObject2 = EmojiText.this.getBaseContext().getAssets().open("low" + character + ".txt");
                        localObject3 = new byte[localObject2.available()];
                        localObject2.read(localObject3);
                        localObject2.close();
                        EmojiText.this.convertedText.append(new String(localObject3).replaceAll("[*]", EmojiText.this.emojeeText.getText().toString()) + "\n\n");
                    } catch (IOException ioe22) {
                        ioe22.printStackTrace();
                    }
                }
            }
        }
    }

    //Button - clear Text click listener method
    private class btnClearTextListner implements View.OnClickListener {
        public void onClick(View view) {
            EmojiText.this.convertedText.setText("");
        }
    }

    //Button  - Convert Text click listener method
    private class btnConvertedTextListner implements View.OnClickListener {
        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            if (!EmojiText.this.convertedText.getText().toString().isEmpty()) {
                ((ClipboardManager) EmojiText.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(EmojiText.this.inputText.getText().toString(), EmojiText.this.convertedText.getText().toString()));
                customToastAtBottom("Copied");
            }
        }
    }

    //Copy button click listener method
    private class btnCopyButtonListner implements View.OnClickListener {
        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            if (EmojiText.this.convertedText.getText().toString().isEmpty()) {
                customToastAtBottom("Please Convert text before copy");
                return;
            }
            ((ClipboardManager) EmojiText.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(EmojiText.this.inputText.getText().toString(), EmojiText.this.convertedText.getText().toString()));
            customToastAtBottom("Copied");
        }
    }

    //Share Button click listener method
    private class btnShareListner implements View.OnClickListener {
        public void onClick(View view) {
            if (EmojiText.this.convertedText.getText().toString().isEmpty()) {
                Toast.makeText(EmojiText.this.getApplicationContext(), "Convert text to share", Toast.LENGTH_LONG).show();
                return;
            }
            Intent shareIntent = new Intent();
            shareIntent.setAction("android.intent.action.SEND");
            shareIntent.setPackage("com.whatsapp");
            shareIntent.putExtra("android.intent.extra.TEXT", EmojiText.this.convertedText.getText().toString());
            shareIntent.setType("text/plain");
            EmojiText.this.startActivity(Intent.createChooser(shareIntent, "Please Select an app to share"));
        }
    }

    //initial method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));


        setContentView(R.layout.emojitext);


        this.inputText = findViewById(R.id.inputText);
        this.emojeeText = findViewById(R.id.emojeeTxt);
        this.convertedText = findViewById(R.id.convertedEmojeeTxt);
        this.convertButton = findViewById(R.id.convertEmojeeBtn);
        this.copyBtn = findViewById(R.id.copyTxtBtn);
        this.shareButton = findViewById(R.id.shareTxtBtn);
        this.clearTxtBtn = findViewById(R.id.clearTxtBtn);
        this.convertButton.setOnClickListener(new btnConvertListner());
        this.clearTxtBtn.setOnClickListener(new btnClearTextListner());
        this.convertedText.setOnClickListener(new btnConvertedTextListner());
        this.copyBtn.setOnClickListener(new btnCopyButtonListner());
        this.shareButton.setOnClickListener(new btnShareListner());


    }

    //Menu initialisation
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }





    protected void onDestroy(){

        super.onDestroy();
    }



}
