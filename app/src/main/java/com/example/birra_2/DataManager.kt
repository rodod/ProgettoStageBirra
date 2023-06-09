package com.example.birra_2

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response


//funzione che legge tutti i file da una shared preference
fun readData(context: Context): MutableList<Beer>{
    val beers : MutableList<Beer> = mutableListOf()
    val sharedPreferences = context.getSharedPreferences("FILE_BEERS", Context.MODE_PRIVATE)
    val allEntries: Map<String, *> = sharedPreferences.all
    val iterator: Iterator<*> = allEntries.entries.iterator()
    val gson= Gson()
    while (iterator.hasNext()) {
        val entry = iterator.next() as Map.Entry<*, *>
        val value = entry.value.toString()
        val beer: Beer = gson.fromJson(value, Beer::class.java)
        beers.add(beer)
    }
    return beers
}


//funzione che aggiunge tutte le birre ad una shared preference
private fun addData(context: Context, beers: List<Beer>) {
    val pref = context.getSharedPreferences("FILE_BEERS", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = pref.edit()
    val gson = Gson()
    for (beer in beers) {
        // Converte l'oggetto birra in json per poi aggiungerlo in SharedPreferences
        val beerJson = gson.toJson(beer)
        val beerKey = "beerN_${beer.id}" // Chiave univoca basata sull'ID della birra
        editor.putString(beerKey, beerJson)
    }
    editor.apply()
    println("Data added successfully")
}

//aggiorna il file json refreshando
fun updateFile(activity: Activity){
    val beerApiService: BeerApiService = ApiClient.create()
    val beers = beerApiService.getBeers()

    beers.enqueue(object :retrofit2.Callback<List<Beer>> {
        override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
            if (response.isSuccessful && response.body()!=null) {
                println(response.isSuccessful)
                addData(activity, response.body()!!)
            }
        }

        override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
        }

    })
}