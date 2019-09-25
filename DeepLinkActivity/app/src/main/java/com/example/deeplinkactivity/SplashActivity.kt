package com.example.deeplinkactivity

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isTaskRoot) {
            //判断栈中的任务数
            finish()
            return
        }
        setContentView(R.layout.activity_splash)
        logo.alpha = 0F
        val d3 = (Math.random() * 10F).toInt()
        if (d3 % 2 == 0) {
            val d = (Math.random() * 10F).toInt()
            logo.translationY = if (d % 2 == 0) 100F else -100F
        } else {
            val d1 = (Math.random() * 10F).toInt()
            logo.translationX = if (d1 % 2 == 1) 100F else -100F
        }
        logo.scaleX = .6F
        logo.scaleY = .6F
        logo.animate()
            .alpha(1F)
            .translationY(0F)
            .translationX(0F)
            .scaleX(1F)
            .scaleY(1F)
            .setInterpolator(FastOutSlowInInterpolator())
            .setDuration(300).startDelay = 300
        logo.postDelayed({
            if (TextUtils.isEmpty(SpUtils.getString(this, "userId", ""))) JumpUtils.jumpLogin(this)
            else JumpUtils.jumpHome(this)
            finish()
        }, 1000)
    }
}
