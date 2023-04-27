package com.drmontoya.moviesbl

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.drmontoya.moviesbl.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        setContentView(binding.root)
        binding.logoLauncher.startAnimation(rotateAnimation)
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            val intent = Intent(this@LauncherActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}