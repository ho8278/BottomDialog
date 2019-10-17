package com.example.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hyoungwoong.bottomdialog.BottomDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BottomDialog.BottomDialogBuilder()
            .setLayout(R.layout.test)
            .setCancelable(false)
            .setNegativeTextView{
                Toast.makeText(baseContext,"야!",Toast.LENGTH_SHORT).show()
            }.build(supportFragmentManager)
    }
}
