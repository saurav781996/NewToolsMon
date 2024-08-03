package com.example.newtoolsmon.memorysenitizer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.an.deviceinfo.device.DeviceInfo;
import com.example.newtoolsmon.R;

import java.util.Timer;
import java.util.TimerTask;

public class EraserMain extends AppCompatActivity {
    public static final String logPrefix = "MyEraser";
    public static int dataOutput = 1;
    public static boolean fillFileTable = true;

    DeviceInfo deviceInfo;
    Handler handler , handler1;
    Timer timer;
    TextView space;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        setContentView(R.layout.eraser_activity);

        handler = new Handler();
        handler1 = new Handler();
        deviceInfo = new DeviceInfo(this);

        space = (TextView) findViewById(R.id.space);
        TextView txtPrimary = (TextView) findViewById(R.id.txtPrimary);
        TextView txtInfoPrimary = (TextView) findViewById(R.id.txtInfoPrimary);
        TextView txtStatusPrimary = (TextView) findViewById(R.id.txtStatusPrimary);
        TextView prgPrimary = (TextView) findViewById(R.id.prgPrimary);
        Button btnPrimary = (Button) findViewById(R.id.btnPrimary);



        new Thread(new Runnable() {
            @Override
            public void run() {

                handler1.post(new Runnable() {
                    @Override
                    public void run() {

                        EraserDrive primary = new EraserDrive(getCacheDir(), false, (TextView) findViewById(R.id.txtInfoPrimary),
                                (TextView) findViewById(R.id.prgPrimary), (Button) findViewById(R.id.btnPrimary), (TextView) findViewById(R.id.txtStatusPrimary));

                    }
                });
            }
        }).start();







        realTimeMemorychecker();

    }

    public void realTimeMemorychecker() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                long percentage = (deviceInfo.getAvailableInternalMemorySize() * 100) / deviceInfo.getTotalInternalMemorySize();

                                space.setText(String.valueOf("Remaining: " + deviceInfo.getAvailableInternalMemorySize() + "\n" + "Total: " + deviceInfo.getTotalInternalMemorySize()) + "\n" + "Percentage: " + percentage + " %");


                            }
                        });
                    }
                }, 0, 500);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null){
            timer.cancel();
        }
    }

    public void memory_seni_info(View view) {
        new AlertDialog.Builder(this).setTitle("Memory Sanitizer Info")
                .setMessage("Memory sanitization is a process in which your deleted data will not be recoverable with any forensic tools or recovery tools.").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
