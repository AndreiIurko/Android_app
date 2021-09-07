package com.andreyyurko.firstapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock.sleep
import android.widget.Button

class LoadingScreen : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        sleep(5000)
        val button: Button = findViewById(R.id.butt1)
        button.setOnClickListener {
            val intent = Intent(this@LoadingScreen, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
