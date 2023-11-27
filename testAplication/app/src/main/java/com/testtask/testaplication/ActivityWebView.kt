package com.testtask.testaplication

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.testaplication.R

class ActivityWebView : AppCompatActivity() {
    // Переменная для обработки результатов выбора файла
    private var filePathCallback: ValueCallback<Array<Uri>>? = null
    // Лаунчер для получения результата выбора файла
    private lateinit var fileChooserLauncher: ActivityResultLauncher<Intent>

    // Статическое поле класса для запроса разрешения на использование камеры
    companion object {
        private const val CAMERA_PERMISSION_REQUEST = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val webView: WebView = findViewById(R.id.webView)
        val url = intent.getStringExtra("url")

        // Настройка WebView
        configureWebView(webView)
        // Загрузка веб-страницы
        loadWebView(url, webView)

        // Запрос разрешения на использование камеры
        requestCameraPermission()

        // Инициализация лаунчера для получения результата выбора файла
        fileChooserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            filePathCallback?.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(result.resultCode, data))
            filePathCallback = null
        }
    }

    // Настройка параметров WebView
    private fun configureWebView(webView: WebView) {
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                // Сохранение колбэка для обработки выбора файла
                this@ActivityWebView.filePathCallback = filePathCallback
                // Запуск лаунчера для выбора файла
                fileChooserLauncher.launch(createGetContentIntent())
                return true
            }
        }
    }

    // Загрузка веб-страницы по указанному URL
    private fun loadWebView(url: String?, webView: WebView) {
        if (!url.isNullOrBlank()) {
            webView.loadUrl(url)
        }
    }

    // Создание Intent для получения контента (выбора файла)
    private fun createGetContentIntent(): Intent {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/* video/*"
        return Intent.createChooser(intent, "Choose File")
    }

    // Запрос разрешения на использование камеры
    private fun requestCameraPermission() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST)
        }
    }
}

