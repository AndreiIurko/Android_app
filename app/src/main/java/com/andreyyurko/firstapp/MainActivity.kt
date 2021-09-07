package com.andreyyurko.firstapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val button1: Button = findViewById(R.id.butt1)
        val button2: Button = findViewById(R.id.butt2)
        // альтернативный вариант
        // val imageButton = findViewById<ImageButton>(R.id.imageButton)
        // или val imageButton = findViewById(R.id.imageButton) as ImageButton

        button1.setOnClickListener {
            button1.text = "50/50, final offer!"
        }
        button2.setOnClickListener {
            button2.text = "No, i'll pass"
        }
    }
}