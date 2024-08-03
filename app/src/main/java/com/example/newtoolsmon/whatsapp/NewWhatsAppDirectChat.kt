package com.example.newtoolsmon.whatsapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.newtoolsmon.R
import com.github.nikartm.button.FitButton
import com.github.nikartm.button.util.getDensity
import com.hbb20.CountryCodePicker

class NewWhatsAppDirectChat : AppCompatActivity() {

    var editTextMessage: EditText? = null
    var editPhno: EditText? = null
    var countryCodePicker: CountryCodePicker? = null
    var send: FitButton? = null
    var cancel: FitButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        setContentView(R.layout.new_whatsapp_directchat)

        wahtsAppDirectChatAlertDialog()

    }

    // Custom Alert Dialog
    fun wahtsAppDirectChatAlertDialog() {
        val dialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(this@NewWhatsAppDirectChat, R.style.CustomAlertDialog)
        val inflater = this@NewWhatsAppDirectChat?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView: View = inflater.inflate(R.layout.direct_chat_alert, null)
        dialogBuilder.setView(dialogView)
        val alertDialog: AlertDialog = dialogBuilder.create()




        editTextMessage = dialogView.findViewById<View>(R.id.editTextMessage) as EditText
        editPhno = dialogView.findViewById<View>(R.id.editPhno) as EditText
        countryCodePicker = dialogView.findViewById<View>(R.id.countryCodePicker) as CountryCodePicker
        send = dialogView.findViewById<View>(R.id.send) as FitButton
        cancel = dialogView.findViewById<View>(R.id.cancel) as FitButton


        send!!.setOnClickListener(View.OnClickListener {
            val installed: Boolean = isAppInstalled("com.whatsapp")
            if (installed) {
                sendOnWhatsApps()
                finish()
            } else {
                Toast.makeText(
                    this@NewWhatsAppDirectChat,
                    "App is not currently installed on your phone ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        cancel!!.setOnClickListener({

            finish()

        })





        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun sendOnWhatsApps() {
        val messageText: String = editTextMessage?.getText().toString()
        val phoneno: String = editPhno?.getText().toString()
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "https://api.whatsapp.com/send?phone=" + (countryCodePicker?.getSelectedCountryCode()
                        ?: getDensity()) + phoneno + "&text=" + messageText
                )
            )
        )
        countryCodePicker?.getSelectedCountryCode()
        Log.d("test", "countryCodePicker " + (countryCodePicker?.getSelectedCountryCode() ?: getDensity() ))
    }

    private fun isAppInstalled(packageName: String): Boolean {
        val pm: PackageManager? = null
        val app_installed: Boolean
        app_installed = try {
            if (pm != null) {
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            }
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return app_installed
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}