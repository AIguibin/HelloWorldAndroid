package com.aiguibin.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.aiguibin.android.bridge.BridgeHandlerRegistry
import com.aiguibin.android.bridge.DefaultBridgeHandler
import com.aiguibin.android.native_modules.Android2H5BridgeCallback
import com.aiguibin.android.ui.theme.HelloWorldAndroidTheme
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.github.lzyzsd.jsbridge.BridgeWebViewClient


class MainActivity : ComponentActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WebView.setWebContentsDebuggingEnabled(true)
        setContent {
            HelloWorldAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BridgeWebViewScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun BridgeWebViewScreen(modifier: Modifier = Modifier) {
    // 使用 Compose 的 LocalContext 获取当前上下文
    val context = LocalContext.current
    val bridgeHandlerRegistry = remember { BridgeHandlerRegistry(context) }
    AndroidView(
        factory = {
            BridgeWebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.apply {
                    allowFileAccess = true // 允许访问文件系统
                    useWideViewPort = true // 支持viewport标签
                    javaScriptEnabled = true // 允许使用JavaScript
                    domStorageEnabled = true // 启用DOM存储
                    loadWithOverviewMode = true // 缩放至屏幕宽度
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW // 允许混合内容
                }


                webViewClient = object : BridgeWebViewClient(this) {
                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        val errorMessage = "BridgeWebView加载失败: ${error?.description}"
                        Log.e("BridgeWebViewScreen", errorMessage)
                        view?.loadUrl("file:///android_asset/dist/index.html")
                    }
                }
                // 设置默认处理器
                 setDefaultHandler(DefaultBridgeHandler())
                // 使用HandlerManager注册所有处理器到BridgeWebView
                bridgeHandlerRegistry.registerAllHandlers(this)

                loadUrl("file:///android_asset/dist/index.html")
                // 页面加载后就可以拿到H5注册的方法，这里就可以调用，并且可以收到回调信息
                this.callHandler("Android2H5","我是主动从Android调过来的数据",
                    Android2H5BridgeCallback())

            }

        },
        modifier = modifier.fillMaxSize()
    )
}
