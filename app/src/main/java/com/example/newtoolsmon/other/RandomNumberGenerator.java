package com.example.newtoolsmon.other;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.common_activity.CommonActivity;
import com.example.newtoolsmon.R;

public class RandomNumberGenerator extends CommonActivity {

    EditText max_value ,range;
    TextView output;
    Button generate_number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_number_generator);

        max_value = (EditText) findViewById(R.id.max_value);
        range = (EditText) findViewById(R.id.range);
        output = (TextView) findViewById(R.id.output);
        generate_number = (Button) findViewById(R.id.generate_number);



        generate_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getRandomData(1, Integer.parseInt(range.getText().toString()));

            }
        });



    }


    public void getRandomData(int count , int range){

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * (range + 1)) + 3; // formula for random number

            int con = Math.round(val); // converting float value to integer

            Log.d("MyMainActivity", "val: " + con + "\n");

            output.setText(String.valueOf(con));

        }

    }

}
