package com.example.newtoolsmon.other;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;
import com.google.android.material.textfield.TextInputLayout;


public class BMICalculator extends CommonActivity {

    SegmentedButtonGroup use_metric;
    Button check_bmi;
    private final static String PREF_IS_METRIC = "system_of_unit";
    TextView txt_result_bmi;
    TextView txt_result_cat;
    AutoCompleteTextView txt_height;
    AutoCompleteTextView txt_weight;
    SharedPreferences sharedPreferences;

    TextView txt_height_outer , txt_weight_outer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        setContentView(R.layout.bmi_calculator);

        use_metric = (SegmentedButtonGroup) findViewById(R.id.use_metric);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        check_bmi = (Button)findViewById(R.id.check_bmi);
        txt_height = findViewById(R.id.txt_height);
        txt_weight = findViewById(R.id.txt_weight);
        initTextField(txt_height);
        initTextField(txt_weight);

        txt_result_bmi = findViewById(R.id.txt_result_bmi);
        txt_result_cat = findViewById(R.id.txt_result_cat);



        txt_result_cat.setText("Result will appear here!");

        setSystemOfUnits();

        check_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculateBmiIfPossible();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initTextField(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              //  calculateBmiIfPossible();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @SuppressLint("StringFormatMatches")
    private void calculateBmiIfPossible() {
        if (isValidInput(txt_height) && isValidInput(txt_weight)) {
            double bmi = calculateBmiAndCastIfNeeded(getTextAsDouble(txt_height), getTextAsDouble(txt_weight));
            txt_result_bmi.setText(getString(R.string.bmi_result, bmi));
            txt_result_cat.setText(getCategory(bmi));
        } else {
            txt_result_bmi.setText("");
            txt_result_cat.setText("");
           // toast("Please enter weight and height");
        }
    }

    private boolean isValidInput(EditText editText) {
        return getTextAsDouble(editText) > 0;
    }

    private double getTextAsDouble(EditText editText) {
        String input = editText.getText().toString().replace(',', '.');
        try {
            return Double.valueOf(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double calculateBmiAndCastIfNeeded(double height, double weight) {
        height = isMetric() ? height : height / 39.37008;
        weight = isMetric() ? weight : weight / 2.204623;
        return calculateBmi(height, weight);
    }

    public static double calculateBmi(double height, double weight) {
        return Math.round(weight / Math.pow(height, 2) * 10d) / 10d;
    }

    private String getCategory(double bmi) {
        if (bmi < 15) {
            return getString(R.string.bmi_cat_1);
        }
        if (bmi < 16) {
            return getString(R.string.bmi_cat_2);
        }
        if (bmi < 18.5) {
            return getString(R.string.bmi_cat_3);
        }
        if (bmi < 25) {
            return getString(R.string.bmi_cat_4);
        }
        if (bmi < 30) {
            return getString(R.string.bmi_cat_5);
        }
        if (bmi < 35) {
            return getString(R.string.bmi_cat_6);
        }
        if (bmi < 40) {
            return getString(R.string.bmi_cat_7);
        }
        if (bmi < 45) {
            return getString(R.string.bmi_cat_8);
        }
        if (bmi < 50) {
            return getString(R.string.bmi_cat_9);
        }
        if (bmi < 60) {
            return getString(R.string.bmi_cat_10);
        }
        return getString(R.string.bmi_cat_11);
    }

    private void setSystemOfUnits() {
        RadioButton btn_metric = findViewById(R.id.btn_metric);
        RadioButton btn_imperial = findViewById(R.id.btn_imperial);
        btn_metric.setChecked(isMetric());


        txt_weight.setHint(isMetric() ? getString(R.string.weight_metric) : getString(R.string.weight_imperial));
        txt_height.setHint(isMetric() ? getString(R.string.height_metric) : getString(R.string.height_imperial));




        use_metric.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {

                if (position == 0){

                    btn_metric.performClick();

                }
                else if (position == 1){

                    btn_imperial.performClick();

                }

            }
        });

    }

    private boolean isMetric() {
        boolean defaultToMetric = getString(R.string.default_unit).equals(getString(R.string.metric));
        return sharedPreferences.getBoolean(PREF_IS_METRIC, defaultToMetric);
    }

    public void setSystemOfUnits(View v) {
        sharedPreferences.edit().putBoolean(PREF_IS_METRIC, v.getId() == R.id.btn_metric).apply();
        setSystemOfUnits();
        calculateBmiIfPossible();
    }


    public void bmiinfo(View view) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Body_mass_index"));
        startActivity(browserIntent);

    }
}


