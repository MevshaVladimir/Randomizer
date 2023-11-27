package com.testtask.testaplication
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.testaplication.R

class ActivityCoin : AppCompatActivity() {
    //Изображение монеты
    private lateinit var coinImage: ImageView
    //Индикатор анимации вращения
    private var spinning: Boolean = false
    //Время анимации вращения
    private var rotationTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin)

        val buttonBack = findViewById<AppCompatButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            goToMainActivity()
        }

        coinImage = findViewById(R.id.coinImageView)
        coinImage.setOnClickListener {
            if(!spinning) {
                flipCoin()
            }
        }
    }

    //Возвращение в главное меню
    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    //Вращение монеты
    private fun flipCoin() {
        // Установка изображения ребра монеты перед анимацией
        coinImage.setImageResource(R.drawable.coin_edge_image_view)
        // Вращение монеты
        val rotateAnimation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
            duration = rotationTime
            // Устанавливаем слушатель завершения анимации
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    spinning = true
                }
                override fun onAnimationEnd(animation: Animation?) {
                    spinning = false
                    setRandomCoinImage()
                }
                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
        coinImage.startAnimation(rotateAnimation)
    }

    //Определение стороны моенты по средствам рандома
    private fun setRandomCoinImage() {
        val coinImages = arrayOf(R.drawable.coin_back_image_view, R.drawable.coin_front_image_view)
        coinImage.setImageResource(coinImages.random())
    }
}