package com.example.deeplinkactivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview.setOnClickListener {
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            val uri= Uri.parse("http://169.254.194.107:3000/deeplink.html?_ijt=84bjt1st9ucnuknqvqvpb9u243")
            intent.data = uri
            startActivity(intent)
            //JumpUtils.jumpWeb(this, )
        }
        haha2.setOnClickListener {
            JumpUtils.jumpTest1(this)
        }
    }
}
