package com.example.project

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlin.random.Random


class Play : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val prefs = getSharedPreferences("HighScoreChkn", Context.MODE_PRIVATE)
        val btn10sec = findViewById<Button>(R.id.btn10sec)
        val btn20sec = findViewById<Button>(R.id.btn20sec)
        val txtScore = findViewById<TextView>(R.id.txtScore)
        val txtTime = findViewById<TextView>(R.id.txtTimer)
        val height = this.resources.displayMetrics.heightPixels
        val width = this.resources.displayMetrics.widthPixels
        val txtHeader = findViewById<TextView>(R.id.textView5)
        val myBm = BitmapFactory.decodeResource(resources, R.drawable.chkn)
        var gameMode = ""

        fun randomHeight(bm: Bitmap):Int{
            return Random.nextInt(bm.height, height - (bm.height * 2))
        }
        fun randomWidth(bm: Bitmap):Int{
            return Random.nextInt(0, width - bm.width)
        }

        val imgBtnChkn = findViewById<ImageButton>(R.id.imgBtnChkn)
        imgBtnChkn.setImageBitmap(myBm)
        imgBtnChkn.isEnabled = false

        fun randomChicken(){
            val newPos = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            newPos.setMargins(randomWidth(myBm), randomHeight(myBm), 0, 0)
            imgBtnChkn.layoutParams = newPos

        }
        fun updateHs() {
            val score10sec = prefs.getInt("HighScore10Sec", 0)
            val score20sec = prefs.getInt("HighScore20Sec", 0)
            val intScore = Integer.parseInt(txtScore.text.toString())
            if (gameMode == "10sec"){
                if (intScore > score10sec) {
                    val editor = prefs.edit()
                    editor.putInt("HighScore10Sec", intScore)
                    editor.commit()
                }
            }
            else if (gameMode == "20sec") {
                if (intScore > score20sec) {
                    val editor = prefs.edit()
                    editor.putInt("HighScore20Sec", intScore)
                    editor.commit()
                }
            }
        }
        fun countDownTimer(seconds: Long) {
            object : CountDownTimer(seconds, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    txtTime.text = (millisUntilFinished/1000).toString()
                }
                override fun onFinish() {
                    btn10sec.isVisible = true
                    btn20sec.isVisible = true
                    txtHeader.isVisible = true
                    imgBtnChkn.isEnabled = false
                    updateHs()
                }

            }.start()
        }
        fun startRound() {
            btn10sec.isVisible = false
            btn20sec.isVisible = false
            txtScore.text = "0"
            txtHeader.isVisible = false
            imgBtnChkn.isEnabled = true
            randomChicken()
        }

        fun playSound() {
            val sound = intent.extras
            if (sound?.get("sound") == true) {
                var mPlayer = MediaPlayer.create(this, R.raw.shooting)
            mPlayer.isLooping = false
            mPlayer.setOnPreparedListener(MediaPlayer.OnPreparedListener {
                mPlayer.start()
            })
            }
        }

        imgBtnChkn.setOnClickListener {
            randomChicken()
            val newScore = Integer.parseInt(txtScore.text.toString()) + 1
            txtScore.text = newScore.toString()
            playSound()
        }

        btn10sec.setOnClickListener {
            countDownTimer(10000)
            startRound()
            gameMode = "10sec"
        }
        btn20sec.setOnClickListener {
            countDownTimer(20000)
            startRound()
            gameMode = "20sec"
        }



    }
}