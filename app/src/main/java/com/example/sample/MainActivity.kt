package com.example.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyoungwoong.bottomdialog.BottomDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BottomDialog().show(supportFragmentManager,"Test")
    }
}
