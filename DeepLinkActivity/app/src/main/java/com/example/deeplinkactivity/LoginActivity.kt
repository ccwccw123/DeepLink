package com.example.deeplinkactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * desc:
 * author：ccw
 * date:2019-09-20
 * time:15:36
 */
class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button.setOnClickListener {
            SpUtils.putString(this,"userId",userEdit.text.toString())
            JumpUtils.jumpHome(this)
            finish()
        }
    }
}