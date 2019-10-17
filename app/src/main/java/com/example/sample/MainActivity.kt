package com.example.sample

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.hyoungwoong.bottomdialog.BottomDialog
import com.hyoungwoong.bottomdialog.BottomListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BottomDialog.Builder(applicationContext)
            .setLayout(R.layout.test)
            .setCancelable(false)
            .setPositiveTextViewColor(R.color.colorAccent)
            .setPositiveTextView(R.string.app_name, object:BottomListener.OnClickListener{
                override fun OnClick(listener: BottomListener) {
                    listener.dismiss()
                }
            })
            .setMessage("올로로로롤")
            .build(supportFragmentManager)
    }
}
