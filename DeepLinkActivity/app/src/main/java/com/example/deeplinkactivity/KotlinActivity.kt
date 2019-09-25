package com.example.deeplinkactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_kotlin.*
import org.jetbrains.anko.toast


/**
 * desc:
 * author：ccw
 * date:17/5/23
 * time:10:44
 */
class KotlinActivity : AppCompatActivity() {
    //定义只读局部变量使用关键字 val 定义。只能为其赋值一次。
    val code: Int = 0;
    var c: Int = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        textview.text = "hollow world"
        hahaha.text = "ddd"
        intent?.apply {
            val json = intent.getStringExtra("json")
            val userId = intent.getStringExtra("userId")
            userId?.let {
                textview.text = json
                hahaha.text = userId.toString()
            }

        }

        printSum(3, 9)
        sum(4, 3)
        textview.setOnClickListener {
            toast("典了一下")
            val sp = getSharedPreferences(SpUtils.PREFERENCE_NAME, Context.MODE_PRIVATE)
            sp.edit().clear().commit()
        }
        // when 会将它的参数和每个条件比较，直到找到一个合适的分支，否则会走默认分支（else）
        when (3 + 5) {
            7 -> {
                toast("等于7")
            }
            8 -> {
                toast("==8")
            }
        }
        var c1: Int = 6
        var v1 = c1.toLong()

        c.toInt()
        // str=66;
    }

    //    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
//        return Toast.makeText(context, this.toString(), duration).apply { show() }
//    }
    override fun onStart() {
        super.onStart()
//        textview.text = "hollow world"
//        textview.text = sum(4, 9);
        //  Int b=ToolSize.dp2Px(this, 30F);
        // Toast.makeText(this,"哈哈哈哈哈"+b,Toast.LENGTH_SHORT).show()

    }

    fun sum(a: Int, b: Int): String {
        return (a + b).toString()
    }

    fun printSum(a: Int, b: Int) {
        Toast.makeText(this, "哈哈哈哈哈", Toast.LENGTH_SHORT).show()
    }

    fun printProduc(arg1: String, arg2: String) {
        val x = parseInt(arg1)
        val y = parseInt(arg2)
        var code = x * y;

    }

    fun parseInt(str: String): Int {
        return Integer.parseInt(str)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        //这里判断 当没有登录的时候 跳转登录页面
        if (TextUtils.isEmpty(SpUtils.getString(this, "userId", ""))) {
            JumpUtils.jumpLogin(this)
            finish()
            return
        }
        intent?.apply {
            val json = intent.getStringExtra("json")
            val userId = intent.getStringExtra("userId")
            userId?.let {
                textview.text = json
                hahaha.text = userId.toString()
            }

        }
    }
}
