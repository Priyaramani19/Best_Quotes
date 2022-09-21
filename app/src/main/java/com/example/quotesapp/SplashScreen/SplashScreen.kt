package com.example.quotesapp.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.quotesapp.R
import com.example.quotesapp.Screen.CategoryScreen.Screen.MainActivity
import com.example.quotesapp.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    lateinit var splashBinding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)


        Handler().postDelayed({
            splashBinding.splashAnim.playAnimation()
            val intent = Intent(this, MainActivity::class.java)
            splashBinding.splashAnim.pauseAnimation()
            startActivity(intent)
            finish()
        }, 2500)

    }
}