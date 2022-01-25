package kr.jiho.kotlinmvvm.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kr.jiho.kotlinmvvm.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setOnExitAnimationListener {
            Intent(applicationContext, LoginActivity::class.java).apply {
                startActivity(this)
                finishAffinity()
            }
        }

        setContentView(R.layout.activity_splash)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}