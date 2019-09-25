<font color='red' size=3 face="黑体">在讲今天主题之前，不知道大家有没有遇到这种情况，产品经理要做这么一个需求，要求外部链接跳转到app页面？又或者指定页面或者带参数？</font><br/>
<font color=#000000 size=2 face="黑体">这里就涉及到一个深度链接的概念，首先我们从第一步开始</font>
###注册事件监听
这里需要使用到 Android Activity中的 <intent-filter> ，现在可以创建一个解析跳转的 Activity，名字随便取了，然后需要在 Manifest 文件中配置具体的<intent-filter><br/>

 			<intent-filter>
                <action android:name="android.intent.action.VIEW"/>

                <category android:name="android.intent.category.DEFAULT"/>
                <category android:name="android.intent.category.BROWSABLE"/>

                <data
                        android:host="content"
                        android:scheme="myapp"/>
            </intent-filter>
            
如上配置，现在这个 Activity 就具备外部唤醒的能力了，注意下 <data> 中的相关配置，如上配置，外部的链接形式应该就是这样的了：myapp://content。<data> 里面还可以定义其他内容，这里就不展开说了。
然后h5那边的写法是

		<!DOCTYPE html>
			<html lang="en">
				<head>
  				   <meta charset="UTF-8">
    			   <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta http-equiv="X-UA-Compatible" content="ie=edge">
                <title>Document</title>
             </head>
		<body>
    		<div onclick="ok()" style="font-size: 30px">跳转app吧12222</div>
		</body>		
		<script>
 		   function ok(){
   		     window.location.href = "myapp://content?json='12312'&userId='3'";
  				  }
		</script>
	</html>
###页面跳转
<font color=#000000 size=2 face="黑体">这里要注意下了，比如说你有一个启动页 A，登录页是B，主页是C，现在要跳到指定的D页面。那么外部唤起 App 的时候，其实有几种情况，这都是需要我们去考虑的。</font><br/>
第一种情况，就是当前手机中并没有启动过目标 App。简单说就是浏览器要直接跳到D页面，然后回退的时候，是显示 A 页面，然后判断是否进入B页面，最后进入到C页面。这里就是需要我们自己去创建一个堆栈，把 A、B、C、D按顺序都放进去，所以D回退到A，A然后判断是否进入B再C。知识点就是[TaskStackBuilder](https://developer.android.com/reference/android/app/TaskStackBuilder.html?hl=zh-cn)，配合它的就是在Manifest中可以指定Parent的属性。<br/>
TaskStackBuilder:官方介绍说提供了一种跨任务导航的约定方法，我的感觉是类似与pengdingIntent，但是这个是通过back的时候触发这个方法。具体自己点下上面的链接，看下官方文档。
		
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
就像上面这样写，这里我做了判断如果C页面已经启动了的话，我就直接打开指定页面D，如果app没有启动则调用TaskStackBuilder.create方法。直接开启的时候记得要加上Intent.FLAG_ACTIVITY_NEW_TASK的Flag，不然就在浏览器所在的堆栈里面了。这里也解决了第二个问题，目标App已经启动，在后台运行着，并且指定的D页面并没有打开就直接打开。<br/>
第三种情况，目标App已经启动，在后台运行这，指定的D页面打开着的。<br/>
这个其实就是启动模式的问题，D已经打开，又一次打开，如果是正经的启动模式，这里肯定重复出现多个D页面的，所以呢，设置一个SingleTop就是可以解决问题的。当然，如果设置了该模式，你需要去处理 onNewIntent() 的方法了。但是我后期把demo改进后发现一个问题，因为我写了个中转类

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
所以启动模式出现了点问题，解决方式是我把目标页和中转页launchMode都设置为singleTask，问题解决，这里建议中转类用Theme.Translucent.NoTitleBar.Fullscreen样式风格
###参数解析

	  Uri data = Uri.parse(url);
            String scheme = data.getScheme();
            String host = data.getHost();
            String path = data.getPath();
            Set<String> queryParameterNames = data.getQueryParameterNames();
            HashMap<String, String> map = null;
            if (!queryParameterNames.isEmpty()) {
                map = new HashMap<>();
                for (String name : queryParameterNames) {
                    map.put(name, data.getQueryParameter(name));
                }
            }
            Log.i(TAG, "host: " + host);
            Log.i(TAG, "path: " + path);
            Log.i(TAG, "scheme: " + scheme);
            if (map != null) {
                Log.i(TAG, "query: " + map.toString());
            }
这样搞定，取出所有传参的值，业务中可能涉及到Notification的方式，但是深度链接的跳转也是一样的，这里推荐一个路由框架Atouter，实现深度链接或许更简单。

##Github地址:
