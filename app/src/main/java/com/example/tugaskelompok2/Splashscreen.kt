package com.example.tugaskelompok2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tugaskelompok2.Slider.SliderActivity
import com.example.tugaskelompok2.register_login.LoginActivity
import kotlinx.android.synthetic.main.splashscreen.*



class Splashscreen  : AppCompatActivity() {
    private var iv: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.mytransition))
        val intent = Intent(this, LoginActivity::class.java)
        val timer1 = object : Thread() {
            override fun run() {
                try {
                    sleep(7000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(intent)
                    finish()
                }
            }
        }
        timer1.start()
    }
}

