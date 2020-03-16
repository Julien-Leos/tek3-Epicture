package com.example.epicture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_login.*
import android.webkit.WebView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    fun parseUrl(url : String) : Boolean {
        if (url.contains("access_denied")) {
            return (false)
        }
        val splitUrl = url.split('#')
        val params = splitUrl[1].split('&')
        for (param in params) {
            val splitParam = param.split('=')
            EpictureApplication.settings.put(splitParam[0], splitParam[1])
        }
        return (true)
    }

    fun loginSuccessful() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        Toast.makeText(this, "Successfully connected !", Toast.LENGTH_LONG).show()
    }

    fun loginFailure() {
        val welcomeIntent = Intent(this, WelcomeActivity::class.java)
        startActivity(welcomeIntent)
        Toast.makeText(this, "Failure in the connection process !", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        webview.loadUrl(EpictureApplication.settings["url"])
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = WebViewClient()

        webview.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (parseUrl(url)) {
                    loginSuccessful()
                } else {
                    loginFailure()
                }
                return true
            }
        })
    }

}