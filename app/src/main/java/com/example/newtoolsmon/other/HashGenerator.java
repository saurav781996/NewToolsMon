package com.example.newtoolsmon.other;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator extends CommonActivity {

    TextView md5_string , sha1_string ,sha256_string ;

    EditText idEdt;

    String md5hash;

    Button generate_hash_key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        setContentView(R.layout.hash_generator);

        md5_string = (TextView) findViewById(R.id.md5_string);
        sha1_string = (TextView) findViewById(R.id.sha1_string);
        sha256_string = (TextView) findViewById(R.id.sha256_string);
        idEdt = (EditText) findViewById(R.id.idEdt);
        generate_hash_key = (Button) findViewById(R.id.generate_hash_key);

        generate_hash_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String editTextData = idEdt.getText().toString();

                performMD5(editTextData);
                md5_string.setText(md5hash);


                try {
                    sha1_string.setText(SHA1(editTextData));
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }




                sha256_string.setText(getSha256Hash(editTextData));


            }
        });

        md5_string.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!md5_string.getText().toString().isEmpty()) {
                    ((ClipboardManager) HashGenerator.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(HashGenerator.this.md5_string.getText().toString(), md5_string.getText().toString()));
                }

            }
        });

        sha1_string.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sha1_string.getText().toString().isEmpty()) {
                    ((ClipboardManager) HashGenerator.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(HashGenerator.this.sha1_string.getText().toString(), sha1_string.getText().toString()));
                }

            }
        });

        sha256_string.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sha256_string.getText().toString().isEmpty()) {
                    ((ClipboardManager) HashGenerator.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(HashGenerator.this.sha256_string.getText().toString(), sha256_string.getText().toString()));
                }


            }
        });

    }

    private void performMD5(String word) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(word.getBytes());

            byte[] data = messageDigest.digest();
            StringBuffer md5Hash = new StringBuffer();
            for (int i = 0; i < data.length; i++) {
                String h = Integer.toHexString(0xFF & data[i]);
                while (h.length() < 2)
                    h = "0" + h;
                md5Hash.append(h);
            }

            md5hash = md5Hash.toString();



        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }



    public String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }


    public String getSha256Hash(String password) {
        try {
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            }
            digest.reset();
            return bin2hex(digest.digest(password.getBytes()));
        } catch (Exception ignored) {
            return null;
        }
    }

    private String bin2hex(byte[] data) {
        StringBuilder hex = new StringBuilder(data.length * 2);
        for (byte b : data)
            hex.append(String.format("%02x", b & 0xFF));
        return hex.toString();
    }





}
