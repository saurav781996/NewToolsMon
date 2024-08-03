package com.example.newtoolsmon.other

import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.Log
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.newtoolsmon.R
import com.github.nikartm.button.FitButton
import java.util.Timer
import java.util.TimerTask

class KotlinTorch : AppCompatActivity() {

    var cameraManager: CameraManager? = null
    var torch: FitButton? = null
    var display_torch: FitButton? = null
    var display: RelativeLayout? = null
    var torchStatus = 0
    var curBrightnessValue: Int? = null
    var settingsCanWrite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        setContentView(R.layout.torch)
        inits()

        torch?.setOnClickListener({

            if (this.torchStatus == 0) {
                touchLightOn()
                this.torchStatus = 1
            } else if (this.torchStatus == 1) {
                touchLightOff()
                this.torchStatus = 0
            }

            Log.d("Tag", "torchStatus" + torchStatus)

        })

        display_torch?.setOnClickListener({
            if (this.torchStatus == 0) {

                if (settingsCanWrite){
                    display?.setBackgroundColor(getResources().getColor(R.color.white))
                    changeScreenBrightness(this@KotlinTorch, 4000)
                    this.torchStatus = 1
                }
                else{
                    askChangeBrightness()
                }


            } else if (this.torchStatus == 1) {

                if (settingsCanWrite){
                    display?.setBackgroundColor(getResources().getColor(R.color.black))
                    changeScreenBrightness(this@KotlinTorch, curBrightnessValue!!)
                    this.torchStatus = 0
                }
                else{
                    askChangeBrightness()
                }


            }


        })

    }

    override fun onResume() {
        super.onResume()

        try {
            curBrightnessValue = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
            Log.d("mytesta", "curBrightnessValue $curBrightnessValue")
        } catch (e: SettingNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }


    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun touchLightOn() {

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraManager!!.setTorchMode("0", true)

        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun touchLightOff() {

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraManager!!.setTorchMode("0", false)

        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }


    }

    fun inits() {

        torch = findViewById<FitButton>(R.id.torch) as FitButton
        display_torch = findViewById<FitButton>(R.id.display_torch) as FitButton
        display = findViewById<RelativeLayout>(R.id.display) as RelativeLayout
        settingsCanWrite = Settings.System.canWrite(applicationContext)
    }

    fun blinkLight(){

        if (this.torchStatus == 1) {

            object : CountDownTimer(250, 250) {
                override fun onTick(millisUntilFinished: Long) {



                    try {
                        cameraManager!!.setTorchMode("0", true)
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }

                }

                override fun onFinish() {


                    try {
                        cameraManager!!.setTorchMode("0", false)
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }

                    blinkDelay()

                }
            }.start()

        }

    }

    fun blinkDelay(){

        Timer().schedule(object : TimerTask() {
            override fun run() {
                this@KotlinTorch?.runOnUiThread(Runnable {

                    blinkLight()

                })
            }
        }, 250)

    }

    private fun changeScreenBrightness(context: Context, screenBrightnessValue: Int) {
        // Change the screen brightness change mode to manual.
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
        // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
        // It will also change the screen brightness for the device.
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            screenBrightnessValue
        )
        Log.d("mytesta", "screenBrightnessValue $screenBrightnessValue")
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun askChangeBrightness() {
        val settingsCanWrite = Settings.System.canWrite(
            applicationContext
        )
        if (!settingsCanWrite) {
            AlertDialog.Builder(this@KotlinTorch).setTitle("Change brightness permission")
                .setMessage("Change brightness permission required for better app experience")
                .setPositiveButton("Yes") { dialog, which ->
                    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                    startActivity(intent)
                }.show()
        }
    }

}