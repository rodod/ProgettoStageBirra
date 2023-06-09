package com.example.birra_2

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Birra_2.R
import com.example.Birra_2.databinding.LayoutBinding


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
            updateFile(this)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        ChargeBroadcastReceiver().onReceive(this,intent )
    }
}

