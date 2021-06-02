package com.example.project

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class HighScore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        val txtHs10sec = findViewById<TextView>(R.id.txtHs10sec)
        val txtHs20sec = findViewById<TextView>(R.id.txtHs20sec)
        val prefs = getSharedPreferences("HighScoreChkn", Context.MODE_PRIVATE)
        val score10sec = prefs.getInt("HighScore10Sec", 0)
        val score20sec = prefs.getInt("HighScore20Sec", 0)

        txtHs10sec.text = score10sec.toString()
        txtHs20sec.text = score20sec.toString()
    }
}