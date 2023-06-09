package com.example.birra_2.ui.images

import android.animation.ObjectAnimator
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun loadImage(url: String, imageView: ImageView) {
    Picasso.get()
        .load(url)
        .into(imageView, object : Callback {
            override fun onSuccess() {
                val fadeAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f)
                fadeAnimator.duration = 1000 // Durata dell'animazione in millisecondi
                fadeAnimator.start()
                println("Image loaded successfully")
            }

            override fun onError(e: Exception) {
                println("Image not loaded")
            }
        })
}