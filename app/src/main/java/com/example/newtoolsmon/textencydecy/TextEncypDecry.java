package com.example.newtoolsmon.textencydecy;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;
import com.kazakago.cryptore.CipherAlgorithm;
import com.kazakago.cryptore.Cryptore;
import com.kazakago.cryptore.DecryptResult;
import com.kazakago.cryptore.EncryptResult;

public class TextEncypDecry extends CommonActivity {

    SegmentedButtonGroup encyptAndDecrypt;
    EditText et_your_text, et_your_key;
    TextView tv_converted_text;
    Button btn_convert ,clear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textencrydecry_activity);
        init();

        encyptAndDecrypt = (SegmentedButtonGroup) findViewById(R.id.encyptAndDecrypt);


        // initial conversion
        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tv_converted_text.setText(encrypt(et_your_text.getText().toString()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });


        encyptAndDecrypt.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {

                if (position == 0){

                    clear.performClick();
                    btn_convert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                tv_converted_text.setText(encrypt(et_your_text.getText().toString()));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
                else if (position == 1){
                    clear.performClick();
                    btn_convert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {
                                tv_converted_text.setText(decrypt(et_your_text.getText().toString()));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }


                        }
                    });
                }

            }
        });

        tv_converted_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!tv_converted_text.getText().toString().isEmpty()) {
                    ((ClipboardManager) TextEncypDecry.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(TextEncypDecry.this.tv_converted_text.getText().toString(), tv_converted_text.getText().toString()));
                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_your_text.setText("");
                et_your_key.setText("");
            }
        });

    }


    private void init(){

        et_your_text = (EditText) findViewById(R.id.et_your_text);
        et_your_key = (EditText) findViewById(R.id.et_your_key);
        tv_converted_text = (TextView) findViewById(R.id.tv_converted_text);
        btn_convert = (Button) findViewById(R.id.btn_convert);
        clear = (Button) findViewById(R.id.clear);

    }



    private String encrypt(String plainStr) throws Exception {
            byte[] plainByte = plainStr.getBytes();
            EncryptResult result = getCryptore(TextEncypDecry.this,et_your_key.getText().toString()).encrypt(plainByte);
            return Base64.encodeToString(result.getBytes(), Base64.DEFAULT);
        }


        private String decrypt(String encryptedStr) throws Exception {
            byte[] encryptedByte = Base64.decode(encryptedStr, Base64.DEFAULT);
            DecryptResult result = getCryptore(TextEncypDecry.this,et_your_key.getText().toString()).decrypt(encryptedByte, null);
            return new String(result.getBytes());
        }


    Cryptore getCryptore(Context context, String alias) throws Exception {
        Cryptore.Builder builder = new Cryptore.Builder(alias, CipherAlgorithm.RSA);
        builder.setContext(context);
        return builder.build();
    }


}
