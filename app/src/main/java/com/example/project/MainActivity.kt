package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.media.MediaPlayer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkbox = findViewById<CheckBox>(R.id.checkBox)
        val btnAbout = findViewById<Button>(R.id.btnAbout)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnHighScore = findViewById<Button>(R.id.btnHighScore)


        var sound = true

        var mPlayer = MediaPlayer.create(this, R.raw.blazer_rail)
        mPlayer.isLooping = true
        mPlayer.setOnPreparedListener(MediaPlayer.OnPreparedListener {
            mPlayer.start()
        })
        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                sound = true
                mPlayer.start()
            }else {
                mPlayer.pause()
                sound = false
            }
        }

        btnAbout.setOnClickListener {
            val intent = Intent(this, About::class.java)
            startActivity(intent)
        }
        btnPlay.setOnClickListener {
            val intent = Intent(this, Play::class.java)
            intent.putExtra("sound", sound)
            startActivity(intent)
        }
        btnHighScore.setOnClickListener {
            val intent = Intent(this, HighScore::class.java)
            startActivity(intent)
        }
    }
}