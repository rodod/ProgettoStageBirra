package com.example.birra_2.ui.activities

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Birra_2.R
import com.example.Birra_2.databinding.LayoutBinding
import com.example.birra_2.data.classes.Beer
import com.example.birra_2.data.midnightRefresh.ChargeBroadcastReceiver
import com.example.birra_2.data.dataManager.readData
import com.example.birra_2.data.dataManager.updateFile
import com.example.birra_2.ui.adapter.Adapter


@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    private lateinit var binding : LayoutBinding
    private val adapter = Adapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


        val header=findViewById<TextView>(R.id.header)
        header.gravity = Gravity.CENTER
        header.setTextColor(Color.BLACK)
        header.setTypeface(null, Typeface.BOLD)

        binding.Recycler.adapter = adapter
        binding.Recycler.layoutManager = LinearLayoutManager(this)

        val beers: List<Beer> = readData(this)
        adapter.submitList(beers)

        updateFile(this ) //aggiorna il json mettendo i dati anche nuovi

        binding.swipeRefreshLayout.setOnRefreshListener { //ogni volta che refresha
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected) {
                updateFile(this)
                binding.swipeRefreshLayout.isRefreshing = false
            } else {
                Toast.makeText(this, "No connection to internet", Toast.LENGTH_SHORT).show()
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        ChargeBroadcastReceiver().onReceive(this,intent )
    }
}

