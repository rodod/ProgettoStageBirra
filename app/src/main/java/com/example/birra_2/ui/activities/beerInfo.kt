package com.example.birra_2.ui.activities

import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.view.ViewCompat
import com.example.Birra_2.R
import com.example.birra_2.data.classes.Beer
import com.example.birra_2.data.dataManager.readData
import com.example.birra_2.ui.images.loadImage


class BeerInfo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.layout2)
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.layout2_horizontal)
        }


        val id = intent.getIntExtra("item_id", 0)
        val img = intent.getStringExtra("item_image")!!
        val sharedElementId = intent.getStringExtra("item_transition_name")
        val detailImageView = findViewById<ImageView>(R.id.imgBeer)
        ViewCompat.setTransitionName(detailImageView, sharedElementId)


        val beers = readData(this)

        //prendo la birra che clicco per poi prendere le informazioni di essa
        val beerReal = returnBeerById(beers, id)

        var foods = ""
        for (food in beerReal!!.food_pairing) {
            foods += "\n-> " + food
        }
        val test: String =
            beerReal.description + "\nBest foods:" + foods + "\nTips: " + beerReal.brewers_tips
        val text = findViewById<TextView>(R.id.textViewinfoBirra)
        val backButton: Button = findViewById(R.id.ReturnHome)
        val beerTitle = findViewById<TextView>(R.id.beerHeader)


        loadImage(img, detailImageView)

        backButton.setOnClickListener {
            finish()
        }

        beerTitle.gravity = Gravity.CENTER
        beerTitle.text = beerReal.name
        beerTitle.setTextColor(android.graphics.Color.BLACK)
        beerTitle.setTypeface(null, Typeface.BOLD)

        text.text = test
        text.setTextColor(android.graphics.Color.BLACK)
        text.textSize=15f
    }
    private fun returnBeerById(beers: List<Beer>, id: Int): Beer? {
        val theBeer : Beer
        for (beer in beers) {
            if (beer.id == id) {
                 theBeer= beer
                return theBeer

            }
        }
        return null
    }
}


