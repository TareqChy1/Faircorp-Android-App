package com.faircorp.activities
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.faircorp.R


@SuppressLint("FrontAppScreen")
class FrontAppScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frontapp_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, BuildingActivity::class.java))
            finish()
        }, 1500)

    }
}