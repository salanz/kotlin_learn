package com.csii.android.updateapk.wview

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.Toast
import com.csii.android.myapplication.R
import com.csii.android.myapplication.webview.NetWorkUtil
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.error_pager.*

/**
 * TODO:
 *
 * 使用说明：
 *
 * Created by ChangXubin on 2018/1/16.
 */
class WebViewActivity : AppCompatActivity() {

//    private var url = "https://www.jianshu.com"
    private var url = "file:///android_asset/index.html"
    private var isRefresh : Boolean = false   //是否是加载失败后重新加载
    private var isLoadSuccess : Boolean = true //是否加载成功

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        if (!NetWorkUtil.isNetworkAvailable(applicationContext)) {
            Toast.makeText(this,"网络走神了，请检查您的网络再试！",Toast.LENGTH_SHORT).show()
        }

        initToolBar()
        initWebSetting()
        initWebViewClient()
        initWebChromeClient()
        web_view.loadUrl(url)
    }

    /**
     * 初始化toolBar*/
    private fun initToolBar(){
        toolbar.title = "载入中.."
        toolbar.setTitleTextColor(resources.getColor(R.color.colorWhite))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    /**
     * WebView相关设置*/
    private fun initWebSetting(){
        var settings:WebSettings = web_view.settings

        //支持获取手势焦点
        web_view.requestFocusFromTouch()
        //设置H5调用本地方法的类
        web_view.addJavascriptInterface(JavaScriptInterfaceClass(),"webviewinterface")
        //支持JS
        settings.javaScriptEnabled = true
        //支持插件
        settings.pluginState = WebSettings.PluginState.ON
        //设置适应屏幕
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        //支持缩放
        settings.setSupportZoom(false)
        //隐藏原生的缩放控件
        settings.displayZoomControls = false
        //支持内容重新布局
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.supportMultipleWindows()
        settings.setSupportMultipleWindows(true)

        //设置缓存模式
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setAppCacheEnabled(true)
        settings.setAppCachePath(web_view.context.cacheDir.absolutePath)

        //设置可访问文件
        settings.allowFileAccess = true
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true)
        //支持自动加载图片
        settings.loadsImagesAutomatically = Build.VERSION.SDK_INT >= 19
        settings.setNeedInitialFocus(true)
        //设置编码格式
        settings.defaultTextEncodingName = "UTF-8"
    }

    /**
     * WebViewClient相关配置
     */
    private fun initWebViewClient(){
        web_view.webViewClient = ComWebViewClient()

    }
    inner class ComWebViewClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            isLoadSuccess = true   //加载时设置为成功
            progress.visibility = View.VISIBLE  //显示进度条
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progress.visibility = View.GONE  //加载完成隐藏进度条
            if (isRefresh && isLoadSuccess ) {  //判断是否是重新加载并加载成功
                web_view.visibility = View.VISIBLE
                error_tach.visibility = View.GONE
            }
        }

        //让所有的操作都放在WebView内操作
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }

        //6.0以上执行，加载出错
        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            showErrorPage()
        }

        //6.0一下执行，加载出错
        override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
            super.onReceivedError(view, errorCode, description, failingUrl)
            showErrorPage()
        }

        @TargetApi(Build.VERSION_CODES.M)
        override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
            super.onReceivedHttpError(view, request, errorResponse)
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            handler?.proceed()
            super.onReceivedSslError(view, handler, error)
        }
    }

    /**
     * WebChromClient相关设置
     */
    private fun initWebChromeClient(){
        web_view.webChromeClient = ComWebChromeClient()
    }
    inner class ComWebChromeClient : WebChromeClient(){
        private val mDefaultVideoPoster: Bitmap? = null//默认的视频展示图

        //设置标题
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            setToolbarTitle(title)
        }

        //设置加载进度
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progress.progress = newProgress
        }
    }

    /***
     * 点击返回键逻辑
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        //如果按下的是回退键且历史记录里确实还有页面
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web_view.canGoBack()) {
            web_view.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 设置Toolbar标题
     *
     * @param title
     */
    private fun setToolbarTitle(title: String? ) {
        Log.d("setToolbarTitle", " WebDetailActivity " + title)
        if (toolbar != null) {
            toolbar.post({
                if (TextUtils.isEmpty(title)){
                    toolbar.title = getString(R.string.loading)
                }else{
                    toolbar.title = title
                }
            })
        }
    }

    /***
     * 显示错误界面方法
     */
    private fun showErrorPage(){
        isLoadSuccess = false
        web_view.visibility = View.GONE
        error_tach.visibility = View.VISIBLE
        error_tach.setOnClickListener {
            isRefresh = true
            web_view.reload()
        }
    }

    inner class JavaScriptInterfaceClass{

        @JavascriptInterface
        public fun showToast(){
            Toast.makeText(this@WebViewActivity,"JS调用方法",Toast.LENGTH_SHORT).show()
        }
    }
}