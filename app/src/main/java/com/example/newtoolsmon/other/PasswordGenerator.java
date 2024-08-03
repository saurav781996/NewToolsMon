package com.example.newtoolsmon.other;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;
import com.example.newtoolsmon.textfeatures.StylistText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordGenerator extends CommonActivity {


    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION = "!$%@#";
    private boolean useLower = true;
    private boolean useUpper = true;
    private boolean useDigits = true;
    private boolean usePunctuation = true;

    SegmentedButtonGroup use_lowercase;
    SegmentedButtonGroup use_uppercase;
    SegmentedButtonGroup use_digit;
    SegmentedButtonGroup use_punctuation;
    SegmentedButtonGroup length_of_password;

    int lenghtOfPassword = 4;

    TextView password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        setContentView(R.layout.generate_password);

        Button generate_password = (Button) findViewById(R.id.generate_password);
        password = (TextView) findViewById(R.id.password);

        generate_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PasswordGenerator(useLower,useUpper,useDigits,usePunctuation);
                password.setText(String.valueOf(generate(lenghtOfPassword)));


            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!password.getText().toString().isEmpty()) {
                    ((ClipboardManager) PasswordGenerator.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(PasswordGenerator.this.password.getText().toString(), password.getText().toString()));
                }


            }
        });


        use_lowercase = (SegmentedButtonGroup) findViewById(R.id.use_lowercase);
        use_uppercase = (SegmentedButtonGroup) findViewById(R.id.use_uppercase);
        use_digit = (SegmentedButtonGroup) findViewById(R.id.use_digit);
        use_punctuation = (SegmentedButtonGroup) findViewById(R.id.use_punctuation);
        length_of_password = (SegmentedButtonGroup) findViewById(R.id.length_of_password);


        use_lowercase.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {

                if (position == 0){
                    useLower = true;
                }
                else if (position == 1){
                    useLower = false;
                }

            }
        });
        use_uppercase.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {

                if (position == 0){
                    useUpper = true;
                }
                else if (position == 1){
                    useUpper = false;
                }

            }
        });
        use_digit.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {

                if (position == 0){
                    useDigits = true;
                }
                else if (position == 1){
                    useDigits = false;
                }

            }
        });
        use_punctuation.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {

                if (position == 0){
                    usePunctuation = true;
                }
                else if (position == 1){
                    usePunctuation = false;
                }

            }
        });
        length_of_password.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {

                if (position == 0){
                    lenghtOfPassword = 4;
                }
                else if (position == 1){

                    lenghtOfPassword = 6;
                }
                else if (position == 2){

                    lenghtOfPassword = 10;
                }
                else if (position == 3){

                    lenghtOfPassword = 12 ;
                }
                else if (position == 4){

                    lenghtOfPassword = 14;
                }
                else if (position == 5){

                    lenghtOfPassword = 20;
                }

            }
        });






}



    public void PasswordGenerator(boolean useLower, boolean useUpper, boolean useDigits, boolean usePunctuation){
        this.useLower = useLower;
        this.useUpper = useUpper;
        this.useDigits = useDigits;
        this.usePunctuation = usePunctuation;
    }



    public String generate(int length) {
        if (length <= 0) {
            return "";
        }


        StringBuilder password = new StringBuilder(length);
        Random random = new Random(System.nanoTime());

        // Collect the categories to use.
        List<String> charCategories = new ArrayList<>(4);
        if (!(useLower || useUpper || useDigits || usePunctuation)){
            charCategories.add(" ");
        }
        if (useLower) {
            charCategories.add(LOWER);
        }
        if (useUpper) {
            charCategories.add(UPPER);
        }
        if (useDigits) {
            charCategories.add(DIGITS);
        }
        if (usePunctuation) {
            charCategories.add(PUNCTUATION);
        }

        // Build the password.
        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }
        return new String(password);
    }

}
