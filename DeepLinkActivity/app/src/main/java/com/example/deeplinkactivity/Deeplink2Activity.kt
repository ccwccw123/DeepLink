package com.example.deeplinkactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.TaskStackBuilder

/**
 * desc:深度链接跳转中转类
 * author：ccw
 * date:2019-09-19
 * time:10:04
 */
class Deeplink2Activity : AppCompatActivity() {
    var bundle: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.data
        try {
            if (data != null) {
                Log.i("==>", "url: " + data.toString())
                val resultIntent = JumpUtils.parseIntent(this, data.toString(), null)
                if (resultIntent == null) {
                    finish()
                    return
                }
                resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                if (ViewUtils.isLaunchedActivity(this, MainActivity::class.java)) {
                    startActivity(resultIntent)
                } else {
                    val stackBuilder = TaskStackBuilder.create(this)
                            .addParentStack(resultIntent.component)
                            .addNextIntent(resultIntent)
                    stackBuilder.startActivities()
                }
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            finish()
        }
    }

    /**
     * 通过中转的页面，跳转到指定的页面
     * */
//    fun startActivity(activity: Activity) {
//        // 首先判断是否是从深度链接跳转过来的
//        val uri = activity.intent.data ?: return
//        val page = uri.getQueryParameter("page")
//        // 启动app
//        if (Utils.isEmpty(page)) {
//            DeepLinkSoNavigator.launchApp(activity)
//            return
//        }
//
//        // 创建跳转请求
//        val request = createDeepLinkSoRequest(uri, page)
//        // 检查配置是否正确
//        if (!checkOptionAndParams(activity, request)) {
//            deepLinkFailed(activity)
//            return
//        }
//
//        // 判断是否被拦截器拦截
//        if (needIntercept(activity, request)) {
//            deepLinkFailed(activity)
//            return
//        }
//
//        // 跳转页面
//        if (request.option!! is DeepLinkSoActivityOption) {
//            startActivity(activity, request)
//        }
//        // 调用指定的EventHandler处理此次跳转请求
//        else if (request.option is DeepLinkSoEventOption) {
//            callEventHandler(activity, request)
//        }
//
//        activity.finish()
//    }

}