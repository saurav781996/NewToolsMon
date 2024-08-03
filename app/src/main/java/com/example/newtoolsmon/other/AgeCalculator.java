package com.example.newtoolsmon.other;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;
import com.github.nikartm.button.FitButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AgeCalculator extends CommonActivity {

    TextView textViewNextBirthdayMonths, textViewNextBirthdayDays, textViewFinalYears, textViewFinalMonths, textViewFinalDays, textViewCurrentDay;

    FitButton textViewCalculate, textViewClear;

    ImageView imageViewCalenderFirst, imageViewCalenderSecond;

    EditText editTextBirthDay, editTextBirthMonth, editTextBirthYear, editTextCurrentDay, editTextCurrentMonth, editTextCurrentYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.age_calculator);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        inits();
        setupCurrentDate();

        imageViewCalenderSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AgeCalculator.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        editTextBirthDay.setText(addZero(dayOfMonth));
                        editTextBirthMonth.setText(addZero(monthOfYear + 1));
                        editTextBirthYear.setText(String.valueOf(year));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });

        textViewCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(editTextBirthDay.getText()) && !TextUtils.isEmpty(editTextBirthMonth.getText()) && !TextUtils.isEmpty(editTextBirthYear.getText())) {
                    calculateAge();
                    nextBirthday();
                } else {
                    customToastAtBottom("Please Enter All Fields");
                }


            }
        });

        textViewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextBirthDay.setText("");
                editTextBirthMonth.setText("");
                editTextBirthYear.setText("");
                customToastAtBottom("Cleared");


            }
        });

    }

    private void inits() {

        textViewNextBirthdayMonths = (TextView) findViewById(R.id.textViewNextBirthdayMonths);
        textViewNextBirthdayDays = (TextView) findViewById(R.id.textViewNextBirthdayDays);
        textViewFinalYears = (TextView) findViewById(R.id.textViewFinalYears);
        textViewFinalMonths = (TextView) findViewById(R.id.textViewFinalMonths);
        textViewFinalDays = (TextView) findViewById(R.id.textViewFinalDays);
        textViewCurrentDay = (TextView) findViewById(R.id.textViewCurrentDay);
        textViewCalculate = (FitButton) findViewById(R.id.textViewCalculate);
        textViewClear = (FitButton) findViewById(R.id.textViewClear);


        imageViewCalenderFirst = (ImageView) findViewById(R.id.imageViewCalenderFirst);
        imageViewCalenderSecond = (ImageView) findViewById(R.id.imageViewCalenderSecond);


        editTextBirthDay = (EditText) findViewById(R.id.editTextBirthDay);
        editTextBirthMonth = (EditText) findViewById(R.id.editTextBirthMonth);
        editTextBirthYear = (EditText) findViewById(R.id.editTextBirthYear);
        editTextCurrentDay = (EditText) findViewById(R.id.editTextCurrentDay);
        editTextCurrentMonth = (EditText) findViewById(R.id.editTextCurrentMonth);
        editTextCurrentYear = (EditText) findViewById(R.id.editTextCurrentYear);


    }

    private void nextBirthday() {
        int currentDay = Integer.valueOf(editTextCurrentDay.getText().toString());
        int currentMonth = Integer.valueOf(editTextCurrentMonth.getText().toString());
        int currentYear = Integer.valueOf(editTextCurrentYear.getText().toString());

        Calendar current = Calendar.getInstance();
        current.set(currentYear, currentMonth, currentDay);

        int birthDay = Integer.valueOf(editTextBirthDay.getText().toString());
        int birthMonth = Integer.valueOf(editTextBirthMonth.getText().toString());
        int birthYear = Integer.valueOf(editTextBirthYear.getText().toString());

        Calendar birthday = Calendar.getInstance();
        birthday.set(birthYear, birthMonth, birthDay);

        long difference = birthday.getTimeInMillis() - current.getTimeInMillis();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(difference);

        textViewNextBirthdayMonths.setText(String.valueOf(cal.get(Calendar.MONTH)));
        textViewNextBirthdayDays.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
    }

    private void calculateAge() {
        int currentDay = Integer.valueOf(editTextCurrentDay.getText().toString());
        int currentMonth = Integer.valueOf(editTextCurrentMonth.getText().toString());
        int currentYear = Integer.valueOf(editTextCurrentYear.getText().toString());

        Date now = new Date(currentYear, currentMonth, currentDay);

        int birthDay = Integer.valueOf(editTextBirthDay.getText().toString());
        int birthMonth = Integer.valueOf(editTextBirthMonth.getText().toString());
        int birthYear = Integer.valueOf(editTextBirthYear.getText().toString());

        Date dob = new Date(birthYear, birthMonth, birthDay);

        if (dob.after(now)) {
            customToastAtBottom("Please Enter a Valid Birthday");
            return;
        }
        // days of every month
        int month[] = {31, 28, 31, 30, 31, 30, 31,
                31, 30, 31, 30, 31};

        // if birth date is greater then current birth
        // month then do not count this month and add 30
        // to the date so as to subtract the date and
        // get the remaining days
        if (birthDay > currentDay) {
            currentDay = currentDay + month[birthMonth - 1];
            currentMonth = currentMonth - 1;
        }

        // if birth month exceeds current month, then do
        // not count this year and add 12 to the month so
        // that we can subtract and find out the difference
        if (birthMonth > currentMonth) {
            currentYear = currentYear - 1;
            currentMonth = currentMonth + 12;
        }

        // calculate date, month, year
        int calculated_date = currentDay - birthDay;
        int calculated_month = currentMonth - birthMonth;
        int calculated_year = currentYear - birthYear;

        textViewFinalDays.setText(String.valueOf(calculated_date));
        textViewFinalMonths.setText(String.valueOf(calculated_month));
        textViewFinalYears.setText(String.valueOf(calculated_year));
    }

    private void setupCurrentDate() {
        final Calendar c = Calendar.getInstance();
        editTextCurrentYear.setText(String.valueOf(c.get(Calendar.YEAR)));
        editTextCurrentMonth.setText(addZero(c.get(Calendar.MONTH) + 1));
        editTextCurrentDay.setText(addZero(c.get(Calendar.DAY_OF_MONTH)));

        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date date = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) - 1);
        String dayOfWeek = simpledateformat.format(date);
        textViewCurrentDay.setText(dayOfWeek);
        textViewCurrentDay.setVisibility(View.VISIBLE);
    }

    private String addZero(int number) {
        String n;
        if (number < 10) {
            n = "0" + number;
        } else {
            n = String.valueOf(number);
        }
        return n;
    }


}