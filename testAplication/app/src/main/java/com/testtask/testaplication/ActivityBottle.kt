package com.testtask.testaplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import android.view.animation.RotateAnimation
import com.example.testaplication.R
import kotlin.random.Random

class ActivityBottle : AppCompatActivity() {
    //Картинка бутылки
    private lateinit var bottleImageView: ImageView
    //Последний угол бутылки
    private var lastDir: Int = 0
    //Индикатор анимации
    private var spinning: Boolean = false
    //Время вращения
    private var rotationTime: Long = 2700
    //Максимальный угол вращения
    private var rotationAngle: Int = 2160

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottle)

        val buttonBack = findViewById<AppCompatButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {goToMainActivity()}

        bottleImageView = findViewById(R.id.bottleImageView)
        bottleImageView.setOnClickListener {rotateBottle()}
    }

    //Возвращение в главное меню
    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    //Вращение бутылки
    private fun rotateBottle() {
        if (!spinning) {
            val random = Random
            val newDir = random.nextInt(rotationAngle)
            val pointWidth = bottleImageView.width / 2f
            val pointHeight = bottleImageView.height / 2f
            val rotation = RotateAnimation(lastDir.toFloat(), newDir.toFloat(), pointWidth, pointHeight)

            rotation.duration = rotationTime
            rotation.fillAfter = true

            rotation.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                override fun onAnimationStart(animation: android.view.animation.Animation?) {
                    spinning = true
                }
                override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
                override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                    spinning = false
                }
            })
            lastDir = newDir
            bottleImageView.startAnimation(rotation)
        }
    }
}