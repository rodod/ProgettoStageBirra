package com.example.birra_2
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BeerApiService {
    @GET("beers")
    fun getBeers(): Call<List<Beer>>
}

object ApiClient {
    private const val BASE_URL = "https://api.punkapi.com/v2/"

    fun create(): BeerApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(BeerApiService::class.java)
    }
}