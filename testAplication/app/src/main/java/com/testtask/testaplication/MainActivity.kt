package com.testtask.testaplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.example.testaplication.R
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val ONESIGNAL_APP_ID = "74d00b15-e1e3-4048-952d-214c52d8b7a2"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeOneSignal()
        setContentView(R.layout.activity_main)

        val buttonBottleStart = findViewById<AppCompatButton>(R.id.buttonBottleStart)
        val buttonCoinStart = findViewById<AppCompatButton>(R.id.buttonCoinStart)
        val buttonOpenWebView = findViewById<AppCompatButton>(R.id.buttonOpenWebView)

        buttonBottleStart.setOnClickListener {
            startActivity(Intent(this, ActivityBottle::class.java))
        }

        buttonCoinStart.setOnClickListener {
            startActivity(Intent(this, ActivityCoin::class.java))
        }

        buttonOpenWebView.setOnClickListener {
            val intent = Intent(this, ActivityWebView::class.java).apply {
                putExtra("url", "https://fex.net/")
            }
            startActivity(intent)
        }
    }

    // OneSignal Initialization
    private fun initializeOneSignal() {
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)
        }
    }
}