package com.example.devwebviewapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.ClientCertRequest
import android.webkit.HttpAuthHandler
import android.webkit.RenderProcessGoneDetail
import android.webkit.SafeBrowsingResponse
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.devwebviewapp.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebviewBinding
    private lateinit var fileChooserLauncher: ActivityResultLauncher<Intent>
    private var filePathCallback: ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebviewBinding.inflate(layoutInflater)

        initWebView()
        initClickListener()

        setContentView(binding.root)
    }

    private fun initWebView() {
        with(binding.webView) {
            clearHistory()
            clearCache(true)
            settings.textZoom = 100
            settings.javaScriptEnabled = true

            webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    this@WebViewActivity.filePathCallback = filePathCallback
                    val intent = fileChooserParams?.createIntent()
                    try {
                        fileChooserLauncher.launch(intent)
                    } catch (e: Exception) {
                        this@WebViewActivity.filePathCallback = null
                        return false
                    }
                    return true
                }
            }

            webViewClient = object: WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    Log.d("DevWebViewApp", "shouldOverrideUrlLoading > url : ${url}" )
                    view!!.loadUrl(url!!)
                    return true
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    Log.d("DevWebViewApp","onPageStarted..." )
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    Log.d("DevWebViewApp","shouldOverrideUrlLoading > request :  ${request}" )
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.tvLoading.visibility = View.GONE
                    Log.d("DevWebViewApp","onPageFinished..." )
                }

                override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                    super.doUpdateVisitedHistory(view, url, isReload)
                    Log.d("DevWebViewApp","doUpdateVisitedHistory...." )
                }

                override fun onFormResubmission(
                    view: WebView?,
                    dontResend: Message?,
                    resend: Message?
                ) {
                    super.onFormResubmission(view, dontResend, resend)
                    Log.d("DevWebViewApp","onFormResubmission > dontResend : ${dontResend} / resend : ${resend}" )
                }

                override fun onLoadResource(view: WebView?, url: String?) {
                    super.onLoadResource(view, url)
                    Log.d("DevWebViewApp","onLoadResource > url : ${url}" )
                }

                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)
                    Log.d("DevWebViewApp","onPageCommitVisible..." )
                }

                override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
                    super.onReceivedClientCertRequest(view, request)
                    Log.d("DevWebViewApp","onReceivedClientCertRequest > request : ${request}" )
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    Log.d("DevWebViewApp","onReceivedError > request : ${request} / error : ${error}" )
                }

                override fun onReceivedHttpAuthRequest(
                    view: WebView?,
                    handler: HttpAuthHandler?,
                    host: String?,
                    realm: String?
                ) {
                    super.onReceivedHttpAuthRequest(view, handler, host, realm)
                    Log.d("DevWebViewApp","onReceivedHttpAuthRequest > host : ${host} / realm : ${realm}" )
                }

                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    super.onReceivedHttpError(view, request, errorResponse)
                    Log.d("DevWebViewApp","onReceivedHttpError > request : ${request} / errorResponse : ${errorResponse}" )
                }

                override fun onReceivedLoginRequest(
                    view: WebView?,
                    realm: String?,
                    account: String?,
                    args: String?
                ) {
                    super.onReceivedLoginRequest(view, realm, account, args)
                    Log.d("DevWebViewApp","onReceivedLoginRequest > realm : ${realm} / account : ${account} / args : ${args}" )
                }

                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
                    super.onReceivedSslError(view, handler, error)
                    handler!!.proceed();
                    Log.d("DevWebViewApp","onReceivedSslError > error : ${error} " )
                }

                override fun onRenderProcessGone(
                    view: WebView?,
                    detail: RenderProcessGoneDetail?
                ): Boolean {
                    return super.onRenderProcessGone(view, detail)
                    Log.d("DevWebViewApp","onRenderProcessGone > detail : ${detail} " )
                }

                override fun onSafeBrowsingHit(
                    view: WebView?,
                    request: WebResourceRequest?,
                    threatType: Int,
                    callback: SafeBrowsingResponse?
                ) {
                    super.onSafeBrowsingHit(view, request, threatType, callback)
                    Log.d("DevWebViewApp","onSafeBrowsingHit > request : ${request} / threatType : ${threatType} / callback : ${callback}" )
                }

                override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
                    super.onScaleChanged(view, oldScale, newScale)
                    Log.d("DevWebViewApp","onScaleChanged > oldScale : ${oldScale} / newScale : ${newScale}" )
                }

                override fun onUnhandledKeyEvent(view: WebView?, event: KeyEvent?) {
                    super.onUnhandledKeyEvent(view, event)
                    Log.d("DevWebViewApp","onUnhandledKeyEvent > event : ${event}" )
                }

                override fun shouldInterceptRequest(
                    view: WebView?,
                    request: WebResourceRequest?
                ): WebResourceResponse? {
                    return super.shouldInterceptRequest(view, request)
                    Log.d("DevWebViewApp","shouldInterceptRequest > request : ${request}" )
                }

                override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                    return super.shouldOverrideKeyEvent(view, event)
                    Log.d("DevWebViewApp","shouldOverrideKeyEvent > event : ${event}" )
                }
            }

            loadUrl(HTML_VALIDATOR_TOOL_URL)
//            addJavascriptInterface(AndroidBridge(), "javascript_object")

            fileChooserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data != null) {
                        val uris = WebChromeClient.FileChooserParams.parseResult(result.resultCode, data)
                        filePathCallback?.onReceiveValue(uris)
                    } else {
                        filePathCallback?.onReceiveValue(null)
                    }
                } else {
                    filePathCallback?.onReceiveValue(null)
                }
                filePathCallback = null
            }
        }
    }

    private fun initClickListener() {
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if(this::binding.isInitialized)
            binding.webView.destroy()
    }
}