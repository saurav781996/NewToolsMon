package com.example.newtoolsmon.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newtoolsmon.R;

public class B extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        startActivity(new Intent(B.this, MainActivity.class));

    }
}