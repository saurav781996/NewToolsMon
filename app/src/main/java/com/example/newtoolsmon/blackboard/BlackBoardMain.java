package com.example.newtoolsmon.blackboard;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newtoolsmon.R;


public class BlackBoardMain extends AppCompatActivity{

    private CanvasView canvasView;
    private RelativeLayout parentView;
    Button clear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


          getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.blackboard_activity);

        clear = (Button) findViewById(R.id.clear);

        parentView = findViewById(R.id.parentView);
        canvasView = new CanvasView(BlackBoardMain.this);
        parentView.addView(canvasView);


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvasView.clearCanvas();
            }
        });

    }

}
