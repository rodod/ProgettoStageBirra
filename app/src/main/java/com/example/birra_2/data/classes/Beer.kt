package com.example.birra_2.data.classes

data class Beer (
    val id: Int,
    val name: String,
    val image_url: String,
    val tagline : String,
    val food_pairing : List<String>,
    val brewers_tips : String,
    val description: String
)